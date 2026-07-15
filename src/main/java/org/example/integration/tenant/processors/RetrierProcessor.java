package org.example.integration.tenant.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.integration.tenant.constants.TenantConstants;
import org.example.integration.tenant.data.DynamoData;
import org.example.integration.tenant.managers.AsseticManager;
import org.example.integration.tenant.managers.AuthorityManager;
import org.example.integration.tenant.managers.DynamoManager;
import org.example.integration.tenant.managers.EventManager;
import org.example.integration.tenant.models.*;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RetrierProcessor implements Runnable{
    private final String authorityToken;
    private final String trackId;
    private static final Logger log = LogManager.getLogger(RetrierProcessor.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(100);
    List<Future<?>> futures = new ArrayList<>();

    public RetrierProcessor(String trackId, String authorityToken) {
        this.trackId = trackId;
        this.authorityToken = authorityToken;
    }

    @Override
    public void run() {
        log.info("{} starting RetrierProcessor",trackId);
        authorityAttachmentRetry();
        failedEntriesRetry();
        EventManager.waitForCompletion(futures);

    }

    public void authorityAttachmentRetry() {

        log.info("{} starting authorityAttachmentRetry process",trackId);
        QueryResponse response = DynamoManager.getRecentSuccessEntriesFromDynamo(trackId);

        if (response != null) {
            for (Map<String, AttributeValue> result: response.items()){
                log.info("{} results for the attachment retry from dynamo: {}",trackId,result);
                DynamoData data = new DynamoData();
                data.setSourceId(result.get("source_id").s());
                data.setTargetId(result.get("target_id").s());
                data.setRetryCount(result.get("retry_count").n());
                if (isAuthorityTask(data.getSourceId()) && data.getTargetId() != null &&
                        data.getTargetId().startsWith("wr")) {
                    uploadAttachments(data.getTargetId().substring("wr".length()), getFormattedAccount(data.getSourceId()));
                }

            }
        }

    }

    public void failedEntriesRetry() {

        log.info("{} starting failedEntriesRetry process",trackId);
        QueryResponse response = DynamoManager.getFailedEntryFromDynamo(trackId);

        if (response != null) {
            for (Map<String, AttributeValue> result: response.items()){
                log.info("{} results from dynamo: {}",trackId,result);
                DynamoData data = new DynamoData();
                data.setSourceId(result.get("source_id").s());
                data.setTargetId(result.get("target_id").s());
                data.setStatus(result.get("current_status").s());
                data.setUpdatedAt(result.get("updated_at").s());

                if (!isWithinLast3Minutes(data.getUpdatedAt())) {
                    if (data.getStatus().startsWith("failed") || "received".equals(data.getStatus())) {
                        if (isAuthorityTask(data.getSourceId())) {
                            failedAuthorityTaskEntriesRetry(data); //Failed Authority Task retry
                        } else if (data.getSourceId().startsWith(TenantConstants.DYNAMO_WR_INSPECT_PREFIX)) {
                            failedAsseticWRInspectRetry(data); //wrInspect Retries
                        } else if (data.getSourceId().startsWith(TenantConstants.DYNAMO_WR_REJECT_PREFIX)) {
                            failedAsseticWRRejectRetry(data); //wrReject Retries
                        } else if (data.getSourceId().startsWith(TenantConstants.DYNAMO_WR_SUPPORT_INFO_PREFIX)) {
                            failedAsseticWRSupportInfoRetry(data); //wrSupportInfo Retries
                        } else if (data.getSourceId().startsWith(TenantConstants.DYNAMO_WO_NEW_PREFIX)) {
                            failedAsseticWONewRetry(data); //woNew Retries
                        } else if (data.getSourceId().startsWith(TenantConstants.DYNAMO_WO_INPRG_PREFIX)) {
                            failedAsseticWOINPRGRetry(data); //woNew Retries
                        } else if (data.getSourceId().startsWith(TenantConstants.DYNAMO_WO_TCOMP_PREFIX)) {
                            failedAsseticWOTCOMPRetry(data); //woNew Retries
                        } else if (data.getSourceId().startsWith(TenantConstants.DYNAMO_WO_SUPPORT_INFO_PREFIX)) {
                            failedAsseticWOSupportInfoRetry(data); //woNew Retries
                        } else {
                            log.info("{} un-known sourceId: {}",trackId,data.getSourceId());
                        }
                    } else {
                        log.info("{} un-known status: {}",trackId,data.getStatus());
                    }
                } else {
                    log.info("{} failed entry is not older than 3 mins. Hence this will consider next time",trackId);
                }
            }
        } else {
            log.info("{} No failed entries",trackId);
        }

    }

    public void failedAuthorityTaskEntriesRetry(DynamoData data) {
        log.info("{} starting failedAuthorityTaskEntriesRetry for: {}",trackId,data.getSourceId());
        data.setRetriable(true);
        AuthorityTaskResponse.Result task = AuthorityManager.getCrmTaskById(trackId, authorityToken, getTaskId(data.getSourceId()),TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC,data.getSourceId());
        AuthorityCrmResponse.ResultItem crm = AuthorityManager.getCrmByFormattedAccount(trackId, getFormattedAccount(data.getSourceId()), authorityToken, TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC);
        if (TenantConstants.DYNAMO_STATUS_RECEIVED.equals(data.getStatus()) ||
                data.getStatus().startsWith(TenantConstants.DYNAMO_STATUS_FAILED)) {
            if (task != null && crm != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("task", task);
                response.put("crm", crm);
                response.put("isConfigured", true);
                String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0, 10);
                AuthorityProcessableTaskProcessor thread = new AuthorityProcessableTaskProcessor(data,executionId,response,authorityToken);
                futures.add(executorService.submit(thread));

            } else {
                log.info("{} task or crmTask is null",trackId);
            }
        } else {
            log.info("{} task is not retriable: {}",trackId,data.getStatus());
        }
    }

    public static boolean isAuthorityTask(String input) {
        if (input == null || input.isEmpty()) return false;
        String[] parts = input.split("/");
        return parts.length == 3;
    }

    public static boolean isWithinLast3Minutes(String utcTimeString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime inputTime = LocalDateTime.parse(utcTimeString, formatter);

            LocalDateTime nowUtc = LocalDateTime.now(ZoneOffset.UTC);

            Duration duration = Duration.between(inputTime, nowUtc);
            return !duration.isNegative() && duration.toMinutes() <= 3;
        } catch (Exception e) {
            log.error("Error parsing time: {}", e.getMessage());
            return false;
        }
    }

    public static String getTaskId(String input) {
        String[] parts = input.split("/");
        return parts[0];
    }

    public static String getFormattedAccount(String input) {
        String[] parts = input.split("/");
        return parts[1];
    }

    public void failedAsseticWRInspectRetry(DynamoData data) {
        log.info("{} starting failedAsseticWRInspectRetry for: {}",trackId,data.getSourceId());
        data.setRetriable(true);
        String formattedAccount = data.getSourceId().substring(TenantConstants.DYNAMO_WR_INSPECT_PREFIX.length());
        AsseticWorkRequestByExternalIdResponse.Resource wr = AsseticManager.getWorkRequestByFilter(trackId,formattedAccount,TenantConstants.ASSETIC_EXTERNAL_IDENTIFIER_FILTER,TenantConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA," ");
        if (wr != null) {
            String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            AsseticWRInspectionProcessor thread = new AsseticWRInspectionProcessor(executionId,authorityToken,wr);
            futures.add(executorService.submit(thread));
        } else {
            log.info("{} wr is null. hence failed W/R inspect not retriable",trackId);
            DynamoManager.updateEntryOnDynamo(data.getSourceId(),TenantConstants.DYNAMO_STATUS_FAILED," ",
                    " ",trackId);
        }
    }

    public void failedAsseticWRRejectRetry(DynamoData data) {
        log.info("{} starting failedAsseticWRRejectRetry for: {}",trackId,data.getSourceId());
        data.setRetriable(true);
        String formattedAccount = data.getSourceId().substring(TenantConstants.DYNAMO_WR_REJECT_PREFIX.length());
        AsseticWorkRequestByExternalIdResponse.Resource wr = AsseticManager.getWorkRequestByFilter(trackId,formattedAccount,TenantConstants.ASSETIC_EXTERNAL_IDENTIFIER_FILTER,TenantConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA," ");
        if (wr != null) {
            String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            AsseticWRRejectProcessor thread = new AsseticWRRejectProcessor(executionId,authorityToken,wr);
            futures.add(executorService.submit(thread));
        } else {
            log.info("{} wr is null. hence failed W/R reject not retriable",trackId);
            DynamoManager.updateEntryOnDynamo(data.getSourceId(),TenantConstants.DYNAMO_STATUS_FAILED," ",
                    " ",trackId);
        }
    }

    public void failedAsseticWRSupportInfoRetry(DynamoData data) {
        log.info("{} starting failedAsseticWRSupportInfoRetry for: {}",trackId,data.getSourceId());
        data.setRetriable(true);
        String sourceId = data.getSourceId().substring(TenantConstants.DYNAMO_WR_SUPPORT_INFO_PREFIX.length());
        String formattedAccount = sourceId.split("/")[0];
        String infoId = sourceId.split("/")[1];
        AsseticWorkRequestByExternalIdResponse.Resource wr = AsseticManager.getWorkRequestByFilter(trackId,formattedAccount,TenantConstants.ASSETIC_EXTERNAL_IDENTIFIER_FILTER,TenantConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA," ");
        if (wr != null && infoId != null) {
            for (AsseticWorkRequestByExternalIdResponse.SupportingInformation item : wr.getSupportingInformationHistory()) {
                if (infoId.equals(item.getId())) {
                    String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").
                            substring(0, 10);
                    AsseticWRSupportInfoProcessor thread = new AsseticWRSupportInfoProcessor(executionId,authorityToken,
                            wr,infoId,item.getDescription(), item.getCreatedByDisplayName(),item.getCreatedDateTime());
                    futures.add(executorService.submit(thread));
                }
            }
        } else {
            log.info("{} wr is null. hence failed W/R support info not retriable",trackId);
            DynamoManager.updateEntryOnDynamo(data.getSourceId(),TenantConstants.DYNAMO_STATUS_FAILED," ",
                    " ",trackId);
        }
    }

    public void failedAsseticWONewRetry(DynamoData data) {
        log.info("{} starting failedAsseticWONewRetry for: {}",trackId,data.getSourceId());
        data.setRetriable(true);
        String formattedAccount = data.getSourceId().substring(TenantConstants.DYNAMO_WO_NEW_PREFIX.length());
        AsseticGetWorkOrderByTimeResponse.WorkOrder wo = AsseticManager.getWorkOrderByExternalId(trackId,formattedAccount,TenantConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA," ");
        if (wo != null) {
            String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            AsseticWONewProcessor thread = new AsseticWONewProcessor(executionId,authorityToken,wo);
            futures.add(executorService.submit(thread));
        } else {
            log.info("{} wo is null. hence failed W/O new not retriable",trackId);
            DynamoManager.updateEntryOnDynamo(data.getSourceId(),TenantConstants.DYNAMO_STATUS_FAILED," ",
                    " ",trackId);
        }
    }

    public void failedAsseticWOINPRGRetry(DynamoData data) {
        log.info("{} starting failedAsseticWOINPRGRetry for: {}",trackId,data.getSourceId());
        data.setRetriable(true);
        String formattedAccount = data.getSourceId().substring(TenantConstants.DYNAMO_WO_INPRG_PREFIX.length());
        AsseticGetWorkOrderByTimeResponse.WorkOrder wo = AsseticManager.getWorkOrderByExternalId(trackId,formattedAccount,TenantConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA, "");
        if (wo != null) {
            String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            AsseticWOINPRGProcessor thread = new AsseticWOINPRGProcessor(executionId,authorityToken,wo);
            futures.add(executorService.submit(thread));
        } else {
            log.info("{} wo is null. hence failed W/O inprg not retriable",trackId);
            DynamoManager.updateEntryOnDynamo(data.getSourceId(),TenantConstants.DYNAMO_STATUS_FAILED," ",
                    " ",trackId);
        }
    }

    public void failedAsseticWOTCOMPRetry(DynamoData data) {
        log.info("{} starting failedAsseticWOTCOMPRetry for: {}",trackId,data.getSourceId());
        data.setRetriable(true);
        String formattedAccount = data.getSourceId().substring(TenantConstants.DYNAMO_WO_TCOMP_PREFIX.length());
        AsseticGetWorkOrderByTimeResponse.WorkOrder wo = AsseticManager.getWorkOrderByExternalId(trackId,formattedAccount,TenantConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA," ");
        if (wo != null) {
            String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            AsseticWOTCOMPProcessor thread = new AsseticWOTCOMPProcessor(executionId,authorityToken,wo);
            futures.add(executorService.submit(thread));
        } else {
            log.info("{} wo is null. hence failed W/O tcomp not retriable",trackId);
            DynamoManager.updateEntryOnDynamo(data.getSourceId(),TenantConstants.DYNAMO_STATUS_FAILED," ",
                    " ",trackId);
        }
    }

    public void failedAsseticWOSupportInfoRetry(DynamoData data) {
        log.info("{} starting failedAsseticWOSupportInfoRetry for: {}",trackId,data.getSourceId());
        data.setRetriable(true);
        String sourceId = data.getSourceId().substring(TenantConstants.DYNAMO_WO_SUPPORT_INFO_PREFIX.length());
        String formattedAccount = sourceId.split("/")[0];
        String infoId = sourceId.split("/")[1];
        AsseticGetWorkOrderByTimeResponse.WorkOrder wo = AsseticManager.getWorkOrderByExternalId(trackId,formattedAccount,TenantConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA," ");
        if (wo != null && infoId != null) {
            for (AsseticGetWorkOrderByTimeResponse.SupportingInformation item : wo.getSupportingInformation()) {
                if (infoId.equals(item.getId())) {
                    String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").
                            substring(0, 10);
                    AsseticWOSupportInfoProcessor thread = new AsseticWOSupportInfoProcessor(executionId,authorityToken,
                            wo,infoId,item.getDescription(),item.getCreatedByDisplayName(),item.getCreatedDateTime());
                    futures.add(executorService.submit(thread));
                }
            }
        } else {
            log.info("{} wo is null. hence failed W/O support info not retriable",trackId);
            DynamoManager.updateEntryOnDynamo(data.getSourceId(),TenantConstants.DYNAMO_STATUS_FAILED," ",
                    " ",trackId);
        }
    }

    public void uploadAttachments(String wrId, String formattedAccount) {
        log.info("{} Uploading attachments for task: {}",trackId,formattedAccount);
        Thread thread = new Thread(new AuthorityAttachmentsProcessor(trackId, authorityToken,formattedAccount,wrId));
        thread.start();
        EventManager.wait(thread,trackId);
    }
}
