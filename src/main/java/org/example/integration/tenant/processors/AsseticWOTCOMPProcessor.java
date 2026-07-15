package org.example.integration.tenant.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.integration.tenant.constants.TenantConstants;
import org.example.integration.tenant.data.AuthorityTaskManagingData;
import org.example.integration.tenant.managers.AuthorityManager;
import org.example.integration.tenant.managers.AuthorityTaskManager;
import org.example.integration.tenant.managers.DynamoManager;
import org.example.integration.tenant.models.AsseticGetWorkOrderByTimeResponse;
import org.example.integration.tenant.models.AuthorityTaskResponse;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.util.List;

public class AsseticWOTCOMPProcessor implements Runnable{
    private final String trackId;
    private final AuthorityTaskManagingData taskData;
    private static final Logger log = LogManager.getLogger(AsseticWOTCOMPProcessor.class);
    public AsseticWOTCOMPProcessor(String trackId, String authorityToken, AsseticGetWorkOrderByTimeResponse.WorkOrder wo) {
        this.trackId = trackId;
        this.taskData = new AuthorityTaskManagingData();
        taskData.setFormattedAccount(wo.getExternalId());
        taskData.setACTN(true);
        taskData.setCode(TenantConstants.AUTHORITY_INVESTIGATE_TASK);
        taskData.setTaskNote("Works completed");
        taskData.setAuthorityToken(authorityToken);
        taskData.setPrefix(TenantConstants.DYNAMO_WO_TCOMP_PREFIX);
        taskData.setFriendlyIdStr(wo.getFriendlyIdStr());
        taskData.setTrackId(trackId);

    }

    @Override
    public void run() {
        log.info("{} starting AsseticTCOMPProcessor for {}",trackId,taskData.getFriendlyIdStr());
        QueryResponse response = DynamoManager.getEntryFromDynamo(taskData.getPrefix()+
                taskData.getFormattedAccount(), trackId);
        if (!response.items().isEmpty()) {
            if (Integer.parseInt(response.items().get(0).get("retry_count").n()) < 4) {
                log.info("{} dynamo entry found. hence a retry",trackId);
                taskData.setRetry(true);
            } else {
                log.info("{} retry count is larger than 3. Hence stop the processing",trackId);
                return;
            }

        }
        List<AuthorityTaskResponse.Result> tasks = AuthorityManager.getCrmTaskByFormattedAccount(trackId,
                taskData.getAuthorityToken(), taskData.getFormattedAccount(),TenantConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA,taskData.getFriendlyIdStr());
        if (tasks != null && !tasks.isEmpty()) {
            boolean taskFound = false;
            AuthorityTaskResponse.Result investigateTask = null;
            for (AuthorityTaskResponse.Result task : tasks) {
                if (TenantConstants.AUTHORITY_INVESTIGATE_TASK.equals(task.getWorkflowActionCode())) {
                    log.info("{} task is present in the authority.",trackId);
                    taskFound = true;
                    taskData.setTaskId(String.valueOf(task.getId()));
                    investigateTask = task;
                    break;
                }
            }
            if (!taskFound) {
                log.info("{} task {} is not present in the authority. Hence integration will not " +
                        "proceed further: {}",trackId,TenantConstants.AUTHORITY_INVESTIGATE_TASK,taskData.getFormattedAccount());
            } else {
                log.info("{} task {} is present in the authority. Hence integration will " +
                        "proceed further: {}",trackId,TenantConstants.AUTHORITY_INVESTIGATE_TASK,taskData.getFormattedAccount());
                String note = AuthorityManager.getTaskNotes(trackId,taskData.getTaskId(),taskData.getAuthorityToken(),TenantConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA, taskData.getFriendlyIdStr(),taskData.getFormattedAccount());
                if (note == null || note.isEmpty()) {
                    log.info("{} issue in getting the task note. {}",trackId,taskData.getFormattedAccount());
                } else if (!note.endsWith("Works completed")) {
                    actnTcompCommentNotFound();
                } else if (investigateTask.getDeterminationCode() == null) {
                    actnTcompDeterminationCodeNotFound();
                } else {
                    log.info("{} No need to process this W/O TCOMP instance {}",trackId,taskData.getFriendlyIdStr());
                    if (taskData.isRetry()) {
                        DynamoManager.updateEntryOnDynamo(taskData.getPrefix()+taskData.getFormattedAccount(),TenantConstants.DYNAMO_STATUS_SUCCESS," ",
                                "Already processed",trackId);
                    }
                }
            }
        } else {
            log.info("{} No tasks found for the external identifier: {}",trackId,taskData.getFormattedAccount());
            //this should be a failure case since there should be at-least one task under the crm
        }

    }

    public void actnTcompCommentNotFound() {
        log.info("{} task comment is not present in the authority. Hence update the task note. {}",trackId,taskData.getFormattedAccount());
        //wait3Mins(); //this to wait for 3 mins if there are multiple operations on the same
        // crm before determining the crm
        boolean noteAdded = AuthorityTaskManager.addTaskNotes(trackId,taskData);
        if (noteAdded) {
            taskData.setEnd(true);
            AuthorityTaskManager.taskClose(trackId,taskData);
        }
    }

    public void actnTcompDeterminationCodeNotFound() {
        log.info("{} task determination code is note present. Hence closing the task",trackId);
        //wait3Mins(); //this to wait for 3 mins if there are multiple operations on the same
        // crm before determining the crm
        taskData.setEnd(true);
        AuthorityTaskManager.taskClose(trackId,taskData);
    }

}
