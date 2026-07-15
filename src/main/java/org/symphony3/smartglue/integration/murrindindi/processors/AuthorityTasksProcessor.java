package org.symphony3.smartglue.integration.murrindindi.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.symphony3.smartglue.integration.murrindindi.constants.MurrindindiConstants;
import org.symphony3.smartglue.integration.murrindindi.data.DynamoData;
import org.symphony3.smartglue.integration.murrindindi.managers.AuthorityManager;
import org.symphony3.smartglue.integration.murrindindi.managers.DynamoManager;
import org.symphony3.smartglue.integration.murrindindi.managers.EventManager;
import org.symphony3.smartglue.integration.murrindindi.models.AuthorityCrmResponse;
import org.symphony3.smartglue.integration.murrindindi.models.AuthorityTaskConfiguration;
import org.symphony3.smartglue.integration.murrindindi.models.AuthorityTaskResponse;
import org.symphony3.smartglue.integration.murrindindi.operators.S3Operator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.time.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class AuthorityTasksProcessor implements Runnable {
    private static final Logger log = LogManager.getLogger(AuthorityTasksProcessor.class);
    private final String trackId;
    private final String authorityToken;
    private String nextRunTime;
    private String lastRunTime;
    private final ExecutorService executorService = Executors.newFixedThreadPool(100);

    public AuthorityTasksProcessor(String trackId, String authorityToken) {
        this.trackId = trackId;
        this.authorityToken = authorityToken;

    }

    @Override
    public void run() {
        log.info("{} Starting Authority Task Processor",trackId);
        try {
            lastRunTime = S3Operator.getLastRuntime(trackId, MurrindindiConstants.AUTHORITY_LAST_RUNTIME);
            if (lastRunTime == null) {
                log.error("{} Failed to retrieve last_run_time",trackId);
                return;
            }

            getTaskList(trackId);
            S3Operator.updateLastRuntime(trackId,nextRunTime,MurrindindiConstants.AUTHORITY_LAST_RUNTIME);


        } catch (Exception e) {
            log.error("{} Error in Authority Task Processor: {}",trackId, e.getMessage());
        }

    }

    public void getTaskList(String trackId) throws Exception {
        int previouseResultCount =100;
        int skip =0;
        nextRunTime = String.valueOf(Instant.now());
        List<AuthorityTaskConfiguration.AuthorityTaskConfig> configList = S3Operator.loadConfigFile();
        List<Future<?>> futures = new ArrayList<>();
        while (previouseResultCount == 100) {

            AuthorityTaskResponse taskResponse = AuthorityManager.getAuthorityTask(trackId, authorityToken, lastRunTime, skip, MurrindindiConstants.TENANT_INTEGRATION_ID_MURRINDINDI);
            if (taskResponse != null) {
                List<AuthorityTaskResponse.Result> results = taskResponse.getResult();
                previouseResultCount = taskResponse.getTotalCount();
                if (previouseResultCount==100){
                    skip +=previouseResultCount;
                }

                for (AuthorityTaskResponse.Result result : results) {
                    if (isInsideWithinThisTrigger(result.getCreatedDateTime())) {
                        newTasksWithinTrigger(trackId,result,configList,futures);
                    } else {
                        log.info("{} Task ID: {} is not applicable for this trigger",trackId,result.getId());
                    }
                }
            } else {
                log.error("{} failure when retrieving tasks list from Authority",trackId);
                return;
            }
        }

        // Wait for all tasks to complete
        EventManager.waitForCompletion(futures);
    }

    public boolean isInsideWithinThisTrigger(String createdDateTime) {

        log.info("{} Comparing the time created time: {}, lastRunTime: {}, nextRunTime: {}",trackId, createdDateTime, lastRunTime, nextRunTime);
        Instant inputTime = OffsetDateTime.parse(createdDateTime).toInstant();
        Instant startTime = Instant.parse(lastRunTime).minus(Duration.ofMinutes(5));
        Instant endTime = Instant.parse(nextRunTime);

        // Check if inputTime is within the range
        return !inputTime.isBefore(startTime) && !inputTime.isAfter(endTime);
    }

    public Map<String, Object> checkCategory(AuthorityTaskResponse.Result task,
                                 List<AuthorityTaskConfiguration.AuthorityTaskConfig> configList) {

        Map<String, Object> response = new HashMap<>();
        AuthorityCrmResponse.ResultItem crm = AuthorityManager.getCrmByFormattedAccount( trackId,task.getFormattedAccount(), authorityToken, MurrindindiConstants.TENANT_INTEGRATION_ID_MURRINDINDI);

        if (crm != null) {
            for (AuthorityTaskConfiguration.AuthorityTaskConfig config : configList) {
                if (String.valueOf(crm.getCategoryId()).equalsIgnoreCase(config.getCategoryId()) &&
                        config.getActionCode().contains(task.getWorkflowActionCode())) {
                    log.info("{} Task ID: {} matches category: {} and action code: {}" ,trackId,task.getId(),crm.getCategoryId(),task.getWorkflowActionCode());
                    response.put("task", task);
                    response.put("crm", crm);
                    response.put("isConfigured", true);
                    return response;
                }
            }
        }

        return response;
    }

    public DynamoData isProcessable(String sourceId, String trackId) {

        QueryResponse response = DynamoManager.getEntryFromDynamo(sourceId, trackId);
        if (response == null) {
            log.info("{} No response from Dynamo",trackId);
            return null;
        }
        DynamoData data = new DynamoData();
        for (Map<String, AttributeValue> result: response.items()){
            log.info("{} results from dynamo: {}",trackId,result);
        }
        if (response.items().size() == 1) {
            // Extract the single result
            Map<String, AttributeValue> result = response.items().get(0);

            // Extract the status and retry_count
            data.setStatus(result.get("current_status").s());
            data.setRetryCount(result.get("retry_count").n());
            data.setTargetId(result.get("target_id").s());
            if (data.getStatus().equalsIgnoreCase(MurrindindiConstants.DYNAMO_STATUS_SUCCESS)) {
                // Item is already in progress
                log.info("{} Item is a success entry: {}",trackId,sourceId);
                return data;
            } else if (data.getStatus().equalsIgnoreCase(MurrindindiConstants.DYNAMO_STATUS_RECEIVED) ||
                    data.getStatus().startsWith(MurrindindiConstants.DYNAMO_STATUS_FAILED)) {
                if (Integer.parseInt(data.getRetryCount()) < 4) {
                    log.info("{} Item is either received or has failed in one stage and retry count less than 4: {}",trackId,sourceId);
                    data.setRetriable(true);
                } else {
                    log.info("{} Item is either received or has failed in one stage and retry count greater than ot equal 4: {}",trackId,sourceId);
                }
                return data;
            } else  {
                // Item has succeeded
                log.info("{} status is unknown: {}",trackId,sourceId);
                return null;
            }

        } else if (response.items().isEmpty()) {
            // No items found
            log.info("{} No items found from dynamo: {}",trackId,sourceId);
            data.setNew(true);
            return data;
        } else {
            // More than one item found
            log.info("{} More than one item found from dynamo: {}",trackId,sourceId);
            return null;
        }
    }

    public void newTasksWithinTrigger(String trackId, AuthorityTaskResponse.Result result,
                                      List<AuthorityTaskConfiguration.AuthorityTaskConfig> configList,
                                                 List<Future<?>> futures) {
        log.info("{} Task ID: {} is applicable for this trigger",trackId,result.getId());
        Map<String, Object> checkResult = checkCategory(result, configList);
        if (Boolean.TRUE.equals(checkResult.get("isConfigured"))) {
            log.info("{} Task ID: {} is applicable for this trigger and category",trackId,result.getId());
            AuthorityCrmResponse.ResultItem crm = (AuthorityCrmResponse.ResultItem) checkResult.get("crm");
            String source = String.valueOf(result.getId())+"/"+result.getFormattedAccount()+"/"+crm.getId();
            DynamoData data = isProcessable(source, trackId);
            if (data != null) {
                if (data.isNew() || data.isRetriable()) {
                    log.info("{} Task ID: {} is new and will be processed",trackId,result.getId());
                    String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0, 10);
                    AuthorityProcessableTaskProcessor task = new AuthorityProcessableTaskProcessor(data,executionId,checkResult,authorityToken);
                    futures.add(executorService.submit(task));
                }
            } else {
                log.info("{} Task ID: {} is not processable",trackId,result.getId());
            }
        } else {
            log.info("{} Task ID: {} is not applicable for this trigger and category",trackId,result.getId());
        }
    }

}
