package org.example.integration.tenant.processors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.integration.tenant.constants.TenantConstants;
import org.example.integration.tenant.data.AuthorityTaskManagingData;
import org.example.integration.tenant.data.DynamoData;
import org.example.integration.tenant.data.HttpRsponseData;
import org.example.integration.tenant.data.IntegrationCompletedEventData;
import org.example.integration.tenant.managers.AsseticManager;
import org.example.integration.tenant.managers.AuthorityManager;
import org.example.integration.tenant.managers.DynamoManager;
import org.example.integration.tenant.models.*;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.io.ByteArrayInputStream;
import java.net.URLConnection;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import static org.example.integration.tenant.managers.EventManager.handleSGCompleteEvent;

public class AuthorityAttachmentsProcessor implements Runnable {
    private final String trackId;
    private final String authorityToken;
    private final String formattedAccount;
    private final String wrId;
    private boolean isRetry;
    private String externalId;
    private static final Logger log = LogManager.getLogger(AuthorityAttachmentsProcessor.class);

    public AuthorityAttachmentsProcessor(String trackId, String authorityToken, String formattedAccount, String wrId) {
        this.trackId = trackId;
        this.authorityToken = authorityToken;
        this.formattedAccount = formattedAccount;
        this.wrId = wrId;
    }

    @Override
    public void run() {
        try {
            log.info("{} Authority Attachments Processor started for account: {}",trackId, formattedAccount);
            List<AuthorityAttachmentMetadataResponse.AttachmentMetadata> attachmentsMetadata = getAttachmentsMetadata();
            if (attachmentsMetadata != null) {
                for (AuthorityAttachmentMetadataResponse.AttachmentMetadata attachmentMetadata : attachmentsMetadata) {
                    externalId = attachmentMetadata.getId();
                    log.info("{} Attachment Metadata: {}",trackId, attachmentMetadata);
                    if (checkSourceId(attachmentMetadata.getId()) && isProcessableAttachment(attachmentMetadata)) {
                        processingAttachment(attachmentMetadata.getDownloadId(), attachmentMetadata.getId(),
                                attachmentMetadata.getFileName());
                    } else {
                        log.info("{} already processed attachment: {}",trackId, attachmentMetadata.getId());
                    }
                }
            }
        } catch (Exception e) {
            log.error("{} Error in Authority Attachments Processor: {}", trackId, e.getMessage());
            dynamoStatusUpdate(trackId,true,externalId,isRetry);
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    formattedAccount+"-Attachments", " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC));

            handleSGCompleteEvent(items,trackId);
        }

    }

    public List<AuthorityAttachmentMetadataResponse.AttachmentMetadata> getAttachmentsMetadata() throws Exception {
        String payload = attachmentMetadatPayload(formattedAccount);
        if (payload != null) {
            log.info("{} Payload for Authority Attachments Metadata: {}",trackId, payload);
            return AuthorityManager.
                    getAttachmentsByFormattedAccount(trackId, authorityToken, payload,TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC,formattedAccount,wrId);
        } else {
            log.error("{} Error in creating payload for Authority Attachments Metadata",trackId);
            return null;
        }
    }

    public String attachmentMetadatPayload(String formattedAccount) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AuthorityAttachmentsMetadataRequest req = new AuthorityAttachmentsMetadataRequest();
        List<String> attachments = new ArrayList<>();
        attachments.add(formattedAccount);
        req.setFormattedAccounts(attachments);
        req.setModuleReference("DD");
        req.setIncludeInTransit(true);
        req.setIncludeOnHold(true);
        return mapper.writeValueAsString(req);
    }

    public boolean isProcessableAttachment(AuthorityAttachmentMetadataResponse.AttachmentMetadata data) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        HttpRsponseData response = AsseticManager.getDocumentById(trackId, data.getId(),TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC,formattedAccount,wrId);
        if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
            AsseticGetDocumentResponse responseObj = mapper.readValue(response.getBody(),
                    AsseticGetDocumentResponse.class);

            if (responseObj != null && responseObj.getResourceList().isEmpty()) {
                log.info("{} Attachment is processable: {}",trackId, data.getId());
                return true;
            }
        }

        return false;
    }

    public void processingAttachment(String downloadId, String externalId, String name) throws Exception {
        HttpRsponseData res = AuthorityManager.getAttachmentsByDownloadId(trackId, authorityToken, downloadId, TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC,formattedAccount,wrId);
        if (res == null || res.getStatusCode() != Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
            log.error("{} Error retrieving attachment with downloadId: {}",trackId, downloadId);
            dynamoStatusUpdate(trackId,true,externalId,isRetry);
            return;
        }
        String base64EncodedAttachment = res.getBody();
        String mimeType = getMimeType(res.getHeaders());
        if (base64EncodedAttachment != null) {
            String payload = getAsseticDocumentPayload(base64EncodedAttachment,externalId, name, mimeType, wrId);
            if (AsseticManager.uploadDocuments(trackId, payload,TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC,formattedAccount,wrId)) {
                dynamoStatusUpdate(trackId,false,externalId,isRetry);
            } else {
                dynamoStatusUpdate(trackId,true,externalId,isRetry);
            }
        }
    }

    public String getAsseticDocumentPayload(String base64Data, String externalId, String name, String mimeType, String wrId) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        AsseticDocumentRequest req = new AsseticDocumentRequest();
        AsseticDocumentRequest.DocumentWorkRequest documentWorkRequest = new AsseticDocumentRequest.DocumentWorkRequest();
        AsseticDocumentRequest.FileProperty fileProperty = new AsseticDocumentRequest.FileProperty();
        List<AsseticDocumentRequest.FileProperty> fileProperties = new ArrayList<>();
        documentWorkRequest.setWorkRequestId(wrId);
        fileProperty.setFilecontent(base64Data);
        fileProperty.setMimetype(mimeType);
        fileProperty.setName(name);
        req.setExternalId(externalId);
        req.setDocumentWorkRequest(documentWorkRequest);
        fileProperties.add(fileProperty);
        req.setFileProperty(fileProperties);
        return mapper.writeValueAsString(req);
    }

    /*public String detectMimeType(String base64Data) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes)) {
            String mimeType = URLConnection.guessContentTypeFromStream(bais);
            if (mimeType != null && mimeType.contains("/")) {
                String[] parts = mimeType.split("/");
                if (parts.length == 2) {
                    return parts[1];
                }
            }
        } catch (Exception e) {
            log.error("{} Error detecting MIME type: {}",trackId, e.getMessage());
        }
        return "jpeg";
    }*/

    public String getMimeType(Map<String, List<String>> headers) {
        String contentType = null;
        if (headers != null) {
            List<String> contentTypeList = headers.get("Content-Type");
            if (contentTypeList == null) {
                // Sometimes header keys might be lowercase depending on the server
                contentTypeList = headers.get("content-type");
            }
            if (contentTypeList != null && !contentTypeList.isEmpty()) {
                contentType = contentTypeList.get(0);
                return contentType.split("/")[1];
            }
        }
        return "jpg";
    }

    public static void dynamoStatusUpdate(String trackId, boolean isFailed, String sourceId, boolean isRetry) {

        if (sourceId.isEmpty() || sourceId == null) {
            log.error("{} Source ID is empty or null. Cannot update Dynamo status.", trackId);
            return;
        }
        if (isFailed) {
            if (isRetry) {
                DynamoManager.updateEntryOnDynamo(sourceId,TenantConstants.DYNAMO_STATUS_FAILED," ",
                        " ",trackId);
            } else {
                createDynamoEntry(trackId,sourceId, TenantConstants.DYNAMO_STATUS_FAILED,
                        " ", " ");
            }
        } else {
            if (!isRetry) {
                createDynamoEntry(trackId,sourceId, TenantConstants.DYNAMO_STATUS_SUCCESS,
                        " ", " ");
            } else {
                DynamoManager.updateEntryOnDynamo(sourceId,TenantConstants.DYNAMO_STATUS_SUCCESS," ",
                        " ",trackId);
            }
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

    private boolean checkSourceId(String sourceId) {
        QueryResponse response = DynamoManager.getEntryFromDynamo(sourceId, trackId);
        if (!response.items().isEmpty()) {
            if (Integer.parseInt(response.items().get(0).get("retry_count").n()) < 4) {
                log.info("{} dynamo entry found. hence a retry",trackId);
                isRetry = true;
                if ("success".equals(response.items().get(0).get("current_status").s())) {
                    log.info("{} already processed attachment: {}",trackId, sourceId);
                    return false;
                }
                return true;
            } else {
                log.info("{} retry count is larger than 3. Hence stop the processing",trackId);
                return false;
            }
        }
        isRetry = false;
        return true;
    }

}
