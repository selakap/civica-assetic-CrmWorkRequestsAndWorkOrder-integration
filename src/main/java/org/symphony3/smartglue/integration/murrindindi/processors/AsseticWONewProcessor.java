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

public class AsseticWONewProcessor implements Runnable{

    private final String trackId;
    private final AuthorityTaskManagingData taskData;
    private static final Logger log = LogManager.getLogger(AsseticWONewProcessor.class);
    public AsseticWONewProcessor(String trackId, String authorityToken, AsseticGetWorkOrderByTimeResponse.WorkOrder wo) {

        this.trackId = trackId;
        this.taskData = new AuthorityTaskManagingData();
        taskData.setFormattedAccount(wo.getExternalId());
        taskData.setACTN(false);
        taskData.setCode(MurrindindiConstants.AUTHORITY_WO_CREATED_TASK);
        taskData.setTaskNote("Works have been approved");
        taskData.setAuthorityToken(authorityToken);
        taskData.setPrefix(MurrindindiConstants.DYNAMO_WO_NEW_PREFIX);
        taskData.setFriendlyIdStr(wo.getFriendlyIdStr());
        taskData.setTrackId(trackId);
    }

    @Override
    public void run() {
        log.info("{} starting AsseticNewWOProcessor for: {}",trackId,taskData.getFriendlyIdStr());
        QueryResponse response = DynamoManager.getEntryFromDynamo(taskData.getPrefix()+taskData.getFormattedAccount(), trackId);
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
                taskData.getAuthorityToken(), taskData.getFormattedAccount(),MurrindindiConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA_MURRINDINDI,taskData.getFriendlyIdStr());
        if (tasks != null && !tasks.isEmpty()) {
            boolean taskFound = false;
            AuthorityTaskResponse.Result ass3Task = null;
            for (AuthorityTaskResponse.Result task : tasks) {
                if (MurrindindiConstants.AUTHORITY_WO_CREATED_TASK.equals(task.getWorkflowActionCode())) {
                    log.info("{} task is present in the authority.",trackId);
                    taskFound = true;
                    taskData.setTaskId(String.valueOf(task.getId()));
                    ass3Task = task;
                    break;
                }
            }
            if (!taskFound) {
                ass3TaskNotFound();
            } else if (ass3Task.getComment() == null || ass3Task.getComment().isEmpty()) {
                ass3commentNotFound();
            } else if (ass3Task.getDeterminationCode() == null) {
                ass3DeterminationCodeNotFound();
            } else {
                log.info("{} No need to process this new W/O instance {}",trackId,taskData.getFriendlyIdStr());
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

    public void ass3TaskNotFound() {
        log.info("{} task is not present in the authority. Hence create a new task. {}",trackId,taskData.getFormattedAccount());
        String taskId = AuthorityTaskManager.authorityTaskOpen(trackId,taskData);
        if (taskId != null) {
            taskData.setTaskId(taskId);
            log.info("{} task is created successfully. task id {} in {} ",trackId,taskId,taskData.getFormattedAccount());
            boolean noteAdded = AuthorityTaskManager.addTaskNotes(trackId,taskData);
            if (noteAdded) {
                taskData.setEnd(true);
                AuthorityTaskManager.taskClose(trackId,taskData);
            }
        }
    }

    public void ass3commentNotFound() {
        log.info("{} task comment is not present in the authority. Hence update the task note. {}",trackId,taskData.getFormattedAccount());
        boolean noteAdded = AuthorityTaskManager.addTaskNotes(trackId,taskData);
        if (noteAdded) {
            taskData.setEnd(true);
            AuthorityTaskManager.taskClose(trackId,taskData);
        }
    }

    public void ass3DeterminationCodeNotFound() {
        log.info("{} task determination code is note present. Hence closing the task",trackId);
        taskData.setEnd(true);
        AuthorityTaskManager.taskClose(trackId,taskData);
    }

}
