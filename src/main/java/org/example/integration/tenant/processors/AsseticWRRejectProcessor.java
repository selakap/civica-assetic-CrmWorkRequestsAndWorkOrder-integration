package org.example.integration.tenant.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.integration.tenant.constants.TenantConstants;
import org.example.integration.tenant.data.AuthorityTaskManagingData;
import org.example.integration.tenant.managers.AuthorityManager;
import org.example.integration.tenant.managers.AuthorityTaskManager;
import org.example.integration.tenant.managers.DynamoManager;
import org.example.integration.tenant.models.AsseticWorkRequestByExternalIdResponse;
import org.example.integration.tenant.models.AuthorityTaskResponse;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.util.List;

public class AsseticWRRejectProcessor implements Runnable{
    private final String trackId;
    private final String authorityToken;
    private final AuthorityTaskManagingData taskData;
    private static final Logger log = LogManager.getLogger(AsseticWRRejectProcessor.class);

    public AsseticWRRejectProcessor(String trackId, String authorityToken, AsseticWorkRequestByExternalIdResponse.Resource wr) {
        this.trackId = trackId;
        this.authorityToken = authorityToken;
        this.taskData = new AuthorityTaskManagingData();
        taskData.setFormattedAccount(wr.getExternalIdentifier());
        taskData.setACTN(false);
        taskData.setCode(TenantConstants.AUTHORITY_REJECTED_TASK);
        taskData.setTaskNote("Operations & Maintenance rejected/cancelled request");
        taskData.setAuthorityToken(authorityToken);
        taskData.setPrefix(TenantConstants.DYNAMO_WR_REJECT_PREFIX);
        taskData.setFriendlyIdStr(wr.getFriendlyIdStr());
        taskData.setTrackId(trackId);
    }

    @Override
    public void run() {
        log.info("{} starting AsseticWRRejectProcessor for: {}",trackId,taskData.getFormattedAccount());
        QueryResponse response = DynamoManager.getEntryFromDynamo(TenantConstants.DYNAMO_WR_REJECT_PREFIX+taskData.getFormattedAccount(), trackId);
        if (!response.items().isEmpty()) {
            if (Integer.parseInt(response.items().get(0).get("retry_count").n()) < 4) {
                log.info("{} dynamo entry found. hence a retry",trackId);
                taskData.setRetry(true);
            } else {
                log.info("{} retry count is larger than 3. Hence stop the processing",trackId);
                return;
            }

        }
        List<AuthorityTaskResponse.Result> tasks = AuthorityManager.getCrmTaskByFormattedAccount(trackId, authorityToken, taskData.getFormattedAccount(),TenantConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA,taskData.getFriendlyIdStr());
        if (tasks != null && !tasks.isEmpty()) {
            boolean taskFound = false;
            AuthorityTaskResponse.Result ass1Task = null;
            for (AuthorityTaskResponse.Result task : tasks) {
                if (TenantConstants.AUTHORITY_REJECTED_TASK.equals(task.getWorkflowActionCode())) {
                    log.info("{} task is present in the authority.",trackId);
                    taskFound = true;
                    taskData.setTaskId(String.valueOf(task.getId()));
                    ass1Task = task;
                    break;
                }
            }
            if (!taskFound) {
                ass1TaskNotFound();
            } else if (ass1Task.getComment() == null || ass1Task.getComment().isEmpty()) {
                ass1commentNotFound();
            } else {
                log.info("{} No need to process this Reject instance {}",trackId,taskData.getFriendlyIdStr());
                if (taskData.isRetry()) {
                    DynamoManager.updateEntryOnDynamo(taskData.getPrefix()+taskData.getFormattedAccount(),TenantConstants.DYNAMO_STATUS_SUCCESS," ",
                            "Already processed",trackId);
                }
            }
        } else {
            log.info("{} No tasks found for the external identifier: {}",trackId,taskData.getFormattedAccount());
        }

    }

    public void ass1TaskNotFound() {
        log.info("{} ASS1 task is not present in the authority. Hence create a new task: {}",trackId,taskData.getFormattedAccount());
        String taskId = AuthorityTaskManager.authorityTaskOpen(trackId,taskData);
        if (taskId != null) {
            log.info("{} task is created successfully",trackId);
            taskData.setTaskId(taskId);
            taskData.setEnd(true);
            boolean noteAdded = AuthorityTaskManager.addTaskNotes(trackId,taskData);
        }
    }

    public void ass1commentNotFound() {
        log.info("{} task comment is not present in the authority. Hence update the task note",trackId);
        taskData.setEnd(true);
        boolean noteAdded = AuthorityTaskManager.addTaskNotes(trackId,taskData);
    }


}
