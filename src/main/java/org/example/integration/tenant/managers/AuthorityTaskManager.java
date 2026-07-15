package org.example.integration.tenant.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.integration.tenant.constants.TenantConstants;
import org.example.integration.tenant.data.AuthorityTaskManagingData;
import org.example.integration.tenant.data.DynamoData;
import org.example.integration.tenant.data.HttpRsponseData;
import org.example.integration.tenant.data.IntegrationCompletedEventData;
import org.example.integration.tenant.models.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.example.integration.tenant.managers.EventManager.handleSGCompleteEvent;

public class AuthorityTaskManager {
    private static final Logger log = LogManager.getLogger(AuthorityTaskManager.class);
    private AuthorityTaskManager() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String authorityTaskOpen(String trackId, AuthorityTaskManagingData taskData) {
        ObjectMapper mapper = new ObjectMapper();
        String taskId = null;
        boolean isFailed = false;

        try {
            String payload = mapper.writeValueAsString(getTaskOpenPayload(taskData.getFormattedAccount(),taskData.getCode()));
            log.info("{} authority task open request: {}",trackId,payload);
            HttpRsponseData res = AuthorityManager.openTask(trackId,payload,taskData.getAuthorityToken(),TenantConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA,taskData);
            if (res.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AuthorityTaskOpenResonse responseObj = mapper.readValue(res.getBody(), AuthorityTaskOpenResonse.class);

                if (responseObj.getResult().getOpenTaskResponseData() != null && !responseObj.getResult().
                        getOpenTaskResponseData().isEmpty()) {
                    taskId = String.valueOf(responseObj.getResult().getOpenTaskResponseData().get(0).getId());
                    log.info("{} Task opened successfully: {}",trackId,taskData.getFormattedAccount());
                } else {
                    log.info("{} task opening failed: {}",trackId,taskData.getFormattedAccount());
                    isFailed = true;
                    taskData.setMessage(res.getBody());
                }
            } else {
                log.error("{} Error in opening task: {}",trackId, res.getBody());
                isFailed = true;
                taskData.setMessage(res.getBody());
            }
        } catch (Exception e) {
            isFailed = true;
            taskData.setMessage(e.toString());
            log.error("{} Error in parsing the document id: {}",trackId,e.toString());
        }
        dynamoStatusUpdate(trackId,isFailed,taskData);
        return taskId;
    }


    public static boolean addTaskNotes(String trackId, AuthorityTaskManagingData taskData) {
        boolean isFailed = false;
        String note = null;
        String statusKey = "] Status: ";
        if (taskData.getInfoId() != null) {
            note = "["+taskData.getInfoId()+"]["+taskData.getSupportInfoDate()+"] ["+taskData.getFriendlyIdStr()+"] ["+taskData.getSupportInfoName()+statusKey+taskData.getTaskNote();
        } else if (TenantConstants.DYNAMO_WO_TCOMP_PREFIX.equals(taskData.getPrefix())) {
            note = "----["+getCurrentLocalTime()+"] ["+taskData.getFriendlyIdStr()+statusKey+taskData.getTaskNote();
        } else {
            note = "["+getCurrentLocalTime()+"] ["+taskData.getFriendlyIdStr()+statusKey+taskData.getTaskNote();
        }

        String encodedNote = URLEncoder.encode(note, StandardCharsets.UTF_8);
        HttpRsponseData res = AuthorityManager.updateTaskNotes(trackId,taskData.getTaskId(),taskData.getAuthorityToken(),encodedNote,TenantConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA,taskData.getFriendlyIdStr(),taskData.getFormattedAccount());
        if (res.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
            log.info("{} Task notes updated successfully: {}",trackId,taskData.getFormattedAccount());
            return true;
        } else {
            log.error("{} Error in updating task notes: {}",trackId, res.getBody());
            isFailed = true;
            taskData.setMessage(res.getBody());
        }
        dynamoStatusUpdate(trackId,isFailed,taskData);

        return false;
    }

    public static void taskClose(String trackId, AuthorityTaskManagingData taskData) {
        ObjectMapper mapper = new ObjectMapper();
        boolean isFailed = false;
        try {
            String payload = mapper.writeValueAsString(getTaskClosePayload(taskData.isACTN()));
            log.info("{} authority task close request: {}",trackId,payload);
            HttpRsponseData res = AuthorityManager.closeTask(trackId,taskData.getTaskId(),
                    payload,taskData.getAuthorityToken(),TenantConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA,taskData);
            if (res.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                log.info("{} Task close successfully: {}",trackId,taskData.getFormattedAccount());
                taskData.setMessage(res.getBody());
            } else {
                log.error("{} Error in closing task: {}",trackId, res.getBody());
                isFailed = true;
                taskData.setMessage(res.getBody());
            }
        } catch (Exception e) {
            log.error("{} Error in parsing the document id: {}",trackId,e.toString());
            isFailed = true;
            taskData.setMessage(e.toString());
        }
        dynamoStatusUpdate(trackId,isFailed,taskData);

    }

    public static AuthorityTaskOpenRequest getTaskOpenPayload(String externalId, String taskCode) {
        AuthorityTaskOpenRequest req = new AuthorityTaskOpenRequest();
        AuthorityTaskOpenRequest.TaskDetail det = new AuthorityTaskOpenRequest.TaskDetail();
        det.setTaskReasonCode(taskCode);
        det.setComment("Test comment");
        req.setTaskDetails(List.of(det));
        String[] parts = externalId.split("\\.");
        req.setDocumentType(Integer.parseInt(parts[0]));
        req.setDocumentYear(Integer.parseInt(parts[1]));
        req.setDocumentNumber(Integer.parseInt(parts[2]));
        req.setDocumentPart(Integer.parseInt(parts[3]));
        return req;
    }

    public static AuthorityTaskCloseRequest getTaskClosePayload(boolean isACTN) {
        AuthorityTaskCloseRequest req = new AuthorityTaskCloseRequest();
        req.setCompletionDateTime(getCurrentUtcTime());
        if (isACTN) {
            req.setDeterminationCode(TenantConstants.AUTHORITY_TASK_CLOSE_CODE_ACTN);
        } else {
            req.setDeterminationCode(TenantConstants.AUTHORITY_TASK_CLOSE_CODE);
        }

        return req;
    }

    public static void dynamoStatusUpdate(String trackId, boolean isFailed, AuthorityTaskManagingData taskData) {
        String sourceId = null;
        String infoId = taskData.getInfoId();
        String formattedAccount = taskData.getFormattedAccount();
        String prefix = taskData.getPrefix();
        boolean isEnd = taskData.isEnd();
        boolean isRetry = taskData.isRetry();
        String message = taskData.getMessage();
        if (taskData.getInfoId() != null) {
            sourceId = prefix+formattedAccount+"/"+infoId;
        } else {
            sourceId = prefix+formattedAccount;
        }

        if (isFailed) {
            if (isRetry) {
                DynamoManager.updateEntryOnDynamo(sourceId,TenantConstants.DYNAMO_STATUS_FAILED," ",
                        message,trackId);
            } else {
                createDynamoEntry(trackId,sourceId, TenantConstants.DYNAMO_STATUS_FAILED,
                        formattedAccount, message);
            }
        } else if (isEnd) {
            if (!isRetry) {
                createDynamoEntry(trackId,sourceId, TenantConstants.DYNAMO_STATUS_SUCCESS,
                        formattedAccount, message);
            } else {
                DynamoManager.updateEntryOnDynamo(sourceId,TenantConstants.DYNAMO_STATUS_SUCCESS," ",
                        message,trackId);
            }
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    sourceId, taskData.getTaskId()+"/"+taskData.getFormattedAccount(), TenantConstants.COMPLETED_EVENT_SUCCESS,
                    1, TenantConstants.STATUS_CODE_200, message,Integer.parseInt(TenantConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA));

            handleSGCompleteEvent(items,trackId);
        }
    }

    public static void createDynamoEntry(String trackId, String sourceId, String status, String targetId, String payload) {
        DynamoData data = new DynamoData();
        data.setTenantIntegrationsId(TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC);
        data.setSourceId(sourceId);
        data.setTargetId(targetId);
        data.setStatus(status);
        data.setRetryCount("0");
        data.setUpdatedAt(getCurrentUtcTime());
        data.setJson(payload);
        DynamoManager.createEntryOnWithData(data, trackId);
    }

    public static String getCurrentUtcTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime utcNow = ZonedDateTime.now(ZoneOffset.UTC);
        return utcNow.format(formatter);
    }
    public static String getCurrentLocalTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssxxx");
        ZonedDateTime melbourneNow = ZonedDateTime.now(ZoneId.of("Australia/Melbourne"));
        return melbourneNow.format(formatter);
    }


}
