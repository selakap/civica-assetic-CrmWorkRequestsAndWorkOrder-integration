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

public class AsseticWOSupportInfoProcessor implements Runnable{

    private final String trackId;
    private final AuthorityTaskManagingData taskData;
    private static final Logger log = LogManager.getLogger(AsseticWOSupportInfoProcessor.class);
    public AsseticWOSupportInfoProcessor(String trackId, String authorityToken, AsseticGetWorkOrderByTimeResponse.WorkOrder wo,
                                         String infoId, String info, String name, String dateTime) {
        this.trackId = trackId;
        this.taskData = new AuthorityTaskManagingData();
        taskData.setFormattedAccount(wo.getExternalId());
        taskData.setACTN(false);
        taskData.setCode(TenantConstants.AUTHORITY_SUPPORT_INFO_TASK);
        taskData.setTaskNote("Operations & Maintenance additional information -"+info);
        taskData.setAuthorityToken(authorityToken);
        taskData.setPrefix(TenantConstants.DYNAMO_WO_SUPPORT_INFO_PREFIX);
        taskData.setFriendlyIdStr(wo.getFriendlyIdStr());
        taskData.setTrackId(trackId);
        taskData.setInfoId(infoId);
        taskData.setSupportInfoName(name);
        taskData.setSupportInfoDate(dateTime.replace("T", " "));
    }

    @Override
    public void run() {
        log.info("{} starting AsseticWOSupportInfoProcessor for {}",trackId,taskData.getFormattedAccount());
        QueryResponse response = DynamoManager.getEntryFromDynamo(taskData.getPrefix()+
                taskData.getFormattedAccount()+"/"+taskData.getInfoId(), trackId);
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
            String emptyTaskId = null;
            AuthorityTaskResponse.Result ass5Task = null;
            for (AuthorityTaskResponse.Result task : tasks) {
                if (TenantConstants.AUTHORITY_SUPPORT_INFO_TASK.equals(task.getWorkflowActionCode())) {
                    if (task.getComment() == null) {
                        emptyTaskId = String.valueOf(task.getId());
                    } else if (task.getComment().contains(taskData.getInfoId())) {
                        taskFound = true;
                        taskData.setTaskId(String.valueOf(task.getId()));
                        ass5Task = task;
                        break;
                    }
                }
            }
            if (!taskFound) {
                if (emptyTaskId != null) {
                    taskData.setTaskId(emptyTaskId);
                    ass5commentNotFound();
                } else {
                    ass5TaskNotFound();
                }
            } else if (ass5Task.getComment() == null || ass5Task.getComment().isEmpty()) {
                ass5commentNotFound();
            } else if (ass5Task.getDeterminationCode() == null) {
                ass5DeterminationCodeNotFound();
            } else {
                log.info("{} No need to process this support info instance {}",trackId,taskData.getFriendlyIdStr());
                if (taskData.isRetry()) {
                    DynamoManager.updateEntryOnDynamo(taskData.getPrefix()+taskData.getFormattedAccount()+"/"+taskData.getInfoId(),TenantConstants.DYNAMO_STATUS_SUCCESS," ",
                            "Already processed",trackId);
                }
            }
        } else {
            log.info("{} No tasks found for the external identifier: {}",trackId,taskData.getFormattedAccount());
            //this should be a failure case since there should be at-least one task under the crm
        }

    }

    public void ass5TaskNotFound() {
        log.info("{} ASS5 task with {} is not present in the authority. " +
                "Hence create a new task: {}",trackId,taskData.getInfoId(),taskData.getFormattedAccount());
        String taskId = AuthorityTaskManager.authorityTaskOpen(trackId,taskData);
        if (taskId != null) {
            log.info("{} task is created successfully",trackId);
            taskData.setTaskId(taskId);
            boolean noteAdded = AuthorityTaskManager.addTaskNotes(trackId,taskData);
            if (noteAdded) {
                taskData.setEnd(true);
                AuthorityTaskManager.taskClose(trackId,taskData);
            }
        }
    }
    public void ass5commentNotFound() {
        log.info("{} task comment is not present in the authority task {}. " +
                "Hence update the task note on {}",trackId,taskData.getTaskId(),taskData.getFormattedAccount());
        boolean noteAdded = AuthorityTaskManager.addTaskNotes(trackId,taskData);
        if (noteAdded) {
            taskData.setEnd(true);
            AuthorityTaskManager.taskClose(trackId,taskData);
        }
    }

    public void ass5DeterminationCodeNotFound() {
        log.info("{} task determination code is note present. Hence closing the task {} of {}",trackId, taskData.getTaskId(),taskData.getFormattedAccount());
        taskData.setEnd(true);
        AuthorityTaskManager.taskClose(trackId,taskData);
    }
}
