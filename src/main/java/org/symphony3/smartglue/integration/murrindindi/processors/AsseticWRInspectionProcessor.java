package org.symphony3.smartglue.integration.murrindindi.processors;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.symphony3.smartglue.integration.murrindindi.constants.MurrindindiConstants;
import org.symphony3.smartglue.integration.murrindindi.data.AuthorityTaskManagingData;
import org.symphony3.smartglue.integration.murrindindi.managers.AuthorityManager;
import org.symphony3.smartglue.integration.murrindindi.managers.AuthorityTaskManager;
import org.symphony3.smartglue.integration.murrindindi.managers.DynamoManager;
import org.symphony3.smartglue.integration.murrindindi.models.*;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;
import java.util.List;


public class AsseticWRInspectionProcessor implements Runnable{
    private final String authorityToken;
    private final String trackId;
    private final AuthorityTaskManagingData taskData;
    private static final Logger log = LogManager.getLogger(AsseticWRInspectionProcessor.class);

    public AsseticWRInspectionProcessor(String trackId, String authorityToken, AsseticWorkRequestByExternalIdResponse.Resource wr) {
        this.trackId = trackId;
        this.authorityToken = authorityToken;
        this.taskData = new AuthorityTaskManagingData();
        taskData.setFormattedAccount(wr.getExternalIdentifier());
        taskData.setACTN(false);
        taskData.setCode(MurrindindiConstants.AUTHORITY_REACTIVE_INSPECTION_TASK);
        taskData.setTaskNote("Assigned to relevant inspector");
        taskData.setAuthorityToken(authorityToken);
        taskData.setPrefix(MurrindindiConstants.DYNAMO_WR_INSPECT_PREFIX);
        taskData.setFriendlyIdStr(wr.getFriendlyIdStr());
        taskData.setTrackId(trackId);
    }

    @Override
    public void run() {
        log.info("{} starting AsseticWRInspectionProcessor for WR: {}",trackId, taskData.getFormattedAccount());
        QueryResponse response = DynamoManager.getEntryFromDynamo(MurrindindiConstants.DYNAMO_WR_INSPECT_PREFIX+taskData.getFormattedAccount(), trackId);
        if (!response.items().isEmpty()) {
            if (Integer.parseInt(response.items().get(0).get("retry_count").n()) < 4) {
                log.info("{} dynamo entry found. hence a retry",trackId);
                taskData.setRetry(true);
            } else {
                log.info("{} retry count is larger than 3. Hence stop the processing",trackId);
                return;
            }

        }
        List<AuthorityTaskResponse.Result> tasks = AuthorityManager.getCrmTaskByFormattedAccount(trackId, authorityToken, taskData.getFormattedAccount(),MurrindindiConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA_MURRINDINDI,taskData.getFriendlyIdStr());
        if (tasks != null && !tasks.isEmpty()) {
            boolean taskFound = false;
            AuthorityTaskResponse.Result ass2Task = null;
            for (AuthorityTaskResponse.Result task : tasks) {
                if (MurrindindiConstants.AUTHORITY_REACTIVE_INSPECTION_TASK.equals(task.getWorkflowActionCode())) {
                    log.info("{} task is present in the authority.",trackId);
                    taskFound = true;
                    taskData.setTaskId(String.valueOf(task.getId()));
                    ass2Task = task;
                    break;
                }
            }
            if (!taskFound) {
                ass2TaskNotFound();
            } else if (ass2Task.getComment() == null || ass2Task.getComment().isEmpty()) {
                ass2commentNotFound();
            } else if (ass2Task.getDeterminationCode() == null) {
                ass2DeterminationCodeNotFound();
            } else {
                log.info("{} No need to process this inspect instance {}",trackId,taskData.getFriendlyIdStr());
                if (taskData.isRetry()) {
                    DynamoManager.updateEntryOnDynamo(taskData.getPrefix()+taskData.getFormattedAccount(),MurrindindiConstants.DYNAMO_STATUS_SUCCESS," ",
                            "Already processed",trackId);
                }
            }
        } else {
            log.info("{} No tasks found for the external identifier: {}",trackId,taskData.getFormattedAccount());
            //this should be a failure case since there should be at-least one task under the crm
        }
    }
    
    public void ass2TaskNotFound() {
        log.info("{} task is not present in the authority. Hence create a new task",trackId);
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

    public void ass2commentNotFound() {
        log.info("{} task comment is not present in the authority. Hence update the task note",trackId);
        boolean noteAdded = AuthorityTaskManager.addTaskNotes(trackId,taskData);
        if (noteAdded) {
            taskData.setEnd(true);
            AuthorityTaskManager.taskClose(trackId,taskData);
        }
    }

    public void ass2DeterminationCodeNotFound() {
        log.info("{} task determination code is note present. Hence closing the task",trackId);
        taskData.setEnd(true);
        AuthorityTaskManager.taskClose(trackId,taskData);
    }

}
