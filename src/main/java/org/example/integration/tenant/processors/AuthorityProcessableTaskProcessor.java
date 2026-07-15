package org.example.integration.tenant.processors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.integration.tenant.constants.TenantConstants;
import org.example.integration.tenant.data.DynamoData;
import org.example.integration.tenant.data.HttpRsponseData;
import org.example.integration.tenant.data.IntegrationCompletedEventData;
import org.example.integration.tenant.managers.AsseticManager;
import org.example.integration.tenant.managers.AuthorityManager;
import org.example.integration.tenant.managers.DynamoManager;
import org.example.integration.tenant.managers.EventManager;
import org.example.integration.tenant.models.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.integration.tenant.managers.EventManager.handleSGCompleteEvent;


public class AuthorityProcessableTaskProcessor implements Runnable{
    private final DynamoData data;
    private static final Logger log = LogManager.getLogger(AuthorityProcessableTaskProcessor.class);
    private final String trackId;
    private final Map<String, Object> input;
    private final String authorityToken;
    private final AuthorityTaskResponse.Result task;
    private final AuthorityCrmResponse.ResultItem crm;

    public AuthorityProcessableTaskProcessor(DynamoData data, String trackId, Map<String, Object> input,
                                             String authorityToken) {
        this.data = data;
        this.trackId = trackId;
        this.input = input;
        this.authorityToken = authorityToken;
        this.task = (AuthorityTaskResponse.Result) input.get("task");
        this.crm = (AuthorityCrmResponse.ResultItem) input.get("crm");
    }

    @Override
    public void run() {
        log.info("{} Processing task ",trackId);
        if (data.isNew()) {
            newTask();
        } else if (data.isRetriable()) {
            previouslyProcessedTask();
        } else {
            log.info("{} Unknown task state: {}",trackId, data.getSourceId());
        }
    }
    
    public void newTask() {
        if (TenantConstants.AUTHORITY_INVESTIGATE_TASK.equals(task.getWorkflowActionCode())) {
            newInvestigateTask();
        } else if (task.getWorkflowActionCode().startsWith(TenantConstants.AUTHORITY_FURTHER_INFO_TASK)) {
            newFurtherInfoTask();
        }

    }

    public void newInvestigateTask() {
        log.info("{} New investigate task: {}",trackId, task.getId());
        if (data.isNew()){
            String source = String.valueOf(task.getId())+"/"+task.getFormattedAccount()+"/"+crm.getId();
            DynamoManager.createEntryOnDynamo(source, trackId);
        }
        log.info("{} Created new entry in DynamoDB for task: {}",trackId, task.getId());
        AsseticWorkRequestByExternalIdResponse.Resource workRequest = AsseticManager.getWorkRequestByFilter(trackId,task.getFormattedAccount(),TenantConstants.ASSETIC_EXTERNAL_IDENTIFIER_FILTER,TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC,task.getId()+"/"+task.getFormattedAccount());
        if (workRequest == null) {
            handleNewInvstTask();
        } else {
            log.info("{} W/R already exist in the assetic",trackId);
            DynamoManager.updateEntryOnDynamo(String.valueOf(task.getId())+"/"+task.getFormattedAccount()+"/"+crm.getId(),TenantConstants.DYNAMO_STATUS_FAILED," ",
                    "W/R already exist in the assetic",trackId);
        }


    }

    public void newFurtherInfoTask() {
        try {
            Thread.sleep(5000); //this is to handle for cases where new investigation task
                                        // and support info task of same crm received same time
            log.info("{} New further info task: {}",trackId, task.getId());
            if (data.isNew()){
                String source = String.valueOf(task.getId())+"/"+task.getFormattedAccount()+"/"+crm.getId();
                DynamoManager.createEntryOnDynamo(source, trackId);
            }
            String note = AuthorityManager.getTaskNotes(trackId,String.valueOf(task.getId()),authorityToken,TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC,task.getFormattedAccount()," ");
            if (note != null) {
                log.info("{} task notes found for task: {}",trackId, task.getId());
                AsseticWorkRequestByExternalIdResponse.Resource workRequest = AsseticManager.getWorkRequestByFilter(trackId,task.getFormattedAccount(),TenantConstants.ASSETIC_EXTERNAL_IDENTIFIER_FILTER,TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC,task.getId()+"/"+task.getFormattedAccount());
                if (workRequest != null) {
                    String wrId = workRequest.getId();
                    String friendlyId = workRequest.getFriendlyIdStr();
                    addTaskNotesToAssetic(note,wrId,friendlyId);

                } else {
                    log.info("{} No W/O or W/R found for externalId: {}",trackId,task.getFormattedAccount());
                    DynamoManager.updateEntryOnDynamo(String.valueOf(task.getId())+"/"+task.getFormattedAccount()+"/"+crm.getId(),TenantConstants.DYNAMO_STATUS_FAILED,"","null",trackId);
                }
            } else {
                log.info("{} No task notes found for task: {}" ,trackId, task.getId());
                DynamoManager.updateEntryOnDynamo(String.valueOf(task.getId())+"/"+task.getFormattedAccount()+"/"+crm.getId(),TenantConstants.DYNAMO_STATUS_FAILED,"","null",trackId);

            }
        } catch (Exception e) {
            log.error("{} Error in creating further info task: {}",trackId,e.toString());
            Thread.currentThread().interrupt();
        }

    }

    public void handleNewInvstTask() {
        AuthorityNameResponse.Result name = null;
        AuthorityPropertyResponse.ParcelAddressResult address = null;
        AuthorityGPSLocationResponse.Coordinate gps = null;
        String sourceId = task.getId()+"/"+task.getFormattedAccount();
        int nameLink = AuthorityManager.getNameLink(task.getFormattedAccount(),trackId,authorityToken, TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC);
        if (nameLink != 0) {
            name = AuthorityManager.getNameData(nameLink,trackId,authorityToken, TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC,sourceId);
        }
        int propertyLink = AuthorityManager.getPropertyLink(task.getFormattedAccount(),trackId,authorityToken);
        if (propertyLink != 0) {
            address = AuthorityManager.getPropertyData(propertyLink,trackId,authorityToken,TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC,sourceId);
        } else {
            sourceId = task.getId()+"/"+task.getFormattedAccount();
            gps = AuthorityManager.getGPSLocationByCrm(trackId,authorityToken,String.valueOf(crm.getId()),sourceId,TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC);
        }

        if (name != null && (address != null || gps != null)) {
            String payload = getAsseticWorkRequestPayload(name, address,gps);
            HttpRsponseData response = AsseticManager.createWorkRequest(trackId, payload,TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC,sourceId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_201)) {
                log.info("{} Assetic work request created successfully: {}",trackId,task.getId());
                uploadAttachments(response.getBody().replace("\"", ""));
                updateInvestigateTaskNotes(task, response.getBody().replace("\"", ""));

            } else {
                log.error("{} Error in creating Assetic work request: {}",trackId, response.getBody().replace("\"", ""));
                DynamoManager.updateEntryOnDynamo(String.valueOf(task.getId())+"/"+task.getFormattedAccount()+"/"+crm.getId(),TenantConstants.DYNAMO_STATUS_FAILED," ",
                        response.getBody().replace("\"", ""),trackId);
            }
        } else {
            log.error("{} Error in getting name or address data: {}",trackId,task.getId());
            DynamoManager.updateEntryOnDynamo(String.valueOf(task.getId())+"/"+task.getFormattedAccount()+"/"+crm.getId(),TenantConstants.DYNAMO_STATUS_FAILED," ",
                    "missing NAR or property in authority",trackId);
        }
    }

    public void previouslyProcessedTask() {
        log.info("{} Previously processed task: {}",trackId,task.getId());
        String status = data.getStatus();
        if (TenantConstants.DYNAMO_STATUS_RECEIVED.equals(status) ||
                TenantConstants.DYNAMO_STATUS_FAILED.equals(status)) {
            //Fully failure
            if (TenantConstants.AUTHORITY_INVESTIGATE_TASK.equals(task.getWorkflowActionCode())) {
                newInvestigateTask();
            } else if (task.getWorkflowActionCode().startsWith(TenantConstants.AUTHORITY_FURTHER_INFO_TASK)) {
                newFurtherInfoTask();
            }

        } else if (TenantConstants.DYNAMO_STATUS_FAILED_ACTN_TASK_NOTES.equals(status)) {
            log.info("{} Task notes update failed: {}",trackId,task.getId());
            updateInvestigateTaskNotes(task, data.getTargetId());
        }
    }


    public String getAsseticWorkRequestPayload(AuthorityNameResponse.Result name,
                                               AuthorityPropertyResponse.ParcelAddressResult address,
                                               AuthorityGPSLocationResponse.Coordinate gps){
        ObjectMapper mapper = new ObjectMapper();
        String payloadStr = null;
        AsseticWorkRequestPayload payload = new AsseticWorkRequestPayload();
        AsseticWorkRequestPayload.Requestor requestor = new AsseticWorkRequestPayload.Requestor();
        AsseticWorkRequestPayload.WorkRequestPhysicalLocation location = new AsseticWorkRequestPayload.WorkRequestPhysicalLocation();
        AsseticWorkRequestPayload.Address addr = new AsseticWorkRequestPayload.Address();

        try {
            payload.setWorkRequestSourceId(3);
            String description = getDescription(name);
            if (description.length() > 249) { //Assetic description field has a 249 character limit
                String subDesc = description.substring(0,248);
                payload.setDescription(subDesc);
            } else {
                payload.setDescription(description);
            }
            payload.setSupportingInformation(description);
            payload.setExternalIdentifier(task.getFormattedAccount());
            payload.setReferenceNumber(getDocumentId(task.getFormattedAccount()));
            requestor.setSurName(name.getFamilyName() != null ? name.getFamilyName() : "NoFirstName");
            requestor.setFirstName(name.getGivenName1() != null ? name.getGivenName1() : "NoLastName");
            if (address != null) {
                payload.setLocation(getPropertyAddress(address));
                addr.setStreetNumber(address.getHouseNumber());
                addr.setStreetAddress(address.getStreetName()+" "+address.getStreetTypeCode());
                addr.setCitySuburb(address.getSuburb());
                addr.setState(address.getState());
                addr.setCountry("Australia");
            } else {
                addr.setCountry("Australia");
                AsseticWorkRequestPayload.WorkRequestSpatialLocation spLocation = new AsseticWorkRequestPayload.WorkRequestSpatialLocation();
                spLocation.setPointString("POINT ("+gps.getLongitude()+" "+gps.getLatitude()+")");
                payload.setWorkRequestSpatialLocation(spLocation);
                payload.setLocation(gps.getLatitude()+", "+gps.getLongitude());  //This order "latitude, longitude" was requested by the account manager
            }

            location.setAddress(addr);
            payload.setWorkRequestPhysicalLocation(location);
            payload.setRequestor(requestor);
            payloadStr =mapper.writeValueAsString(payload);
            log.info("{} new create contact payload: {}",trackId,payloadStr);
        } catch (JsonProcessingException e) {
            log.error("{} Error in creating W/R payload name: {}",trackId, e.toString());
        }
        return payloadStr;
    }

    public String getDescription(AuthorityNameResponse.Result name){
        AuthorityCrmResponse.ResultItem crm = (AuthorityCrmResponse.ResultItem) input.get("crm");
        String crmDescription = AuthorityManager.getDescription(crm,trackId, authorityToken, TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC);

        return TenantConstants.ASSETIC_SUPPORT_INFO_PREFIX_FROM_AUTHORITY+crmDescription + " | customer name: "+ name.getFullName().getName()+ " | customer address: "+getNameAddress(name)+ " | mobile: "+name.getMobilePhone()+
                " | home: "+name.getHomePhone1()+" | email: "+name.getEmailAddress()+" | source: "+crm.getContactMethodCode();
    }

    public String getPhone(AuthorityNameResponse.Result name) {
        List<String> phoneNumbers = Arrays.asList(name.getMobilePhone(), name.getHomePhone1(),
                name.getHomePhone2(), name.getBusinessPhone());


        return phoneNumbers.stream()
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining(","));
    }
    public String getNameAddress(AuthorityNameResponse.Result name) {
        List<String> addr = Arrays.asList(name.getFormatAddress1(), name.getFormatAddress2(),
                name.getFormatAddress3(), name.getFormatAddress4());
        return addr.stream()
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining(","));
    }

    public String getPropertyAddress(AuthorityPropertyResponse.ParcelAddressResult address) {
        return address.getFormattedAddressNoState()!= null ? address.getFormattedAddressNoState() : "NoAddress";
    }

    public void updateInvestigateTaskNotes(AuthorityTaskResponse.Result task, String wrId) {
        String friendlyWrId = getWRFriendlyId(wrId);
        String note = "["+getCurrentLocalTime()+"]"+"["+friendlyWrId+"] Status: Created request for Operations & Maintenance";
        String encodedNote = URLEncoder.encode(note, StandardCharsets.UTF_8);
        HttpRsponseData response = AuthorityManager.updateTaskNotes(trackId, String.valueOf(task.getId()),
                authorityToken,encodedNote,TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC, task.getFormattedAccount(),wrId);
        if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
            log.info("{} Task notes updated successfully: {}",trackId,task.getId());
            DynamoManager.updateEntryOnDynamo(String.valueOf(task.getId())+"/"+task.getFormattedAccount()+"/"+crm.getId(),TenantConstants.DYNAMO_STATUS_SUCCESS,"wr"+wrId,response.getBody(),trackId);
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    task.getId()+"/"+task.getFormattedAccount(), friendlyWrId, TenantConstants.COMPLETED_EVENT_SUCCESS,
                    1, TenantConstants.STATUS_CODE_200, response.getBody(),Integer.parseInt(TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC));

            handleSGCompleteEvent(items,trackId);
        } else {
            log.error("{} Error in updating task notes: {}",trackId, response.getBody());
            DynamoManager.updateEntryOnDynamo(String.valueOf(task.getId())+"/"+task.getFormattedAccount()+"/"+crm.getId(),TenantConstants.DYNAMO_STATUS_FAILED_ACTN_TASK_NOTES,"wr"+wrId,response.getBody(),trackId);
        }

    }

    public static String getCurrentLocalTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssxxx");
        ZonedDateTime melbourneNow = ZonedDateTime.now(ZoneId.of("Australia/Melbourne"));
        return melbourneNow.format(formatter);
    }

    public void addTaskNotesToAssetic(String note, String wrId, String friendlyId) {
        String payload = supportInfoPayload(TenantConstants.ASSETIC_SUPPORT_INFO_PREFIX_FROM_AUTHORITY+note);
        log.info("{} new create contact payload: {}",trackId,payload);
        HttpRsponseData response = AsseticManager.addSupportInfoToWorkRequest(trackId, payload, wrId,TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC,task.getId()+"/"+task.getFormattedAccount());
        if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_201)) {
            DynamoManager.updateEntryOnDynamo(String.valueOf(task.getId())+"/"+task.getFormattedAccount()+"/"+crm.getId(),TenantConstants.DYNAMO_STATUS_SUCCESS,wrId,response.getBody(),trackId);
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    task.getId()+"/"+task.getFormattedAccount(), friendlyId, TenantConstants.COMPLETED_EVENT_SUCCESS,
                    1, TenantConstants.STATUS_CODE_201, response.getBody(),Integer.parseInt(TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC));

            handleSGCompleteEvent(items,trackId);
        } else {
            log.error("{} Error in adding support info",trackId);
            String error = response.getBody();
            DynamoManager.updateEntryOnDynamo(String.valueOf(task.getId())+"/"+task.getFormattedAccount()+"/"+crm.getId(),TenantConstants.DYNAMO_STATUS_FAILED,wrId,response.getBody(),trackId);
        }
    }

    public String supportInfoPayload(String note) {
        String payload = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            AsseticAddSupportInfoRequest req = new AsseticAddSupportInfoRequest();
            req.setDescription(note);
            payload = mapper.writeValueAsString(req);
        } catch (JsonProcessingException e) {
            log.error("{} Error in creating W/R payload name: {}",trackId, e.toString());
        }
        return payload;
    }

    public String getWRFriendlyId(String wrId) {
        AsseticWorkRequestByExternalIdResponse.Resource wr = AsseticManager.getWorkRequestByFilter(trackId,wrId,TenantConstants.ASSETIC_ID_FILTER,TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC,task.getId()+"/"+task.getFormattedAccount());
        if (wr != null && wr.getFriendlyIdStr() != null) {
            return wr.getFriendlyIdStr();
        } else {
            return wrId;
        }
    }

    public void uploadAttachments(String wrId) {
        log.info("{} Uploading attachments for task: {}",trackId,task.getFormattedAccount());
        Thread thread = new Thread(new AuthorityAttachmentsProcessor(trackId, authorityToken,task.getFormattedAccount(),wrId));
        thread.start();
        EventManager.wait(thread,trackId);
    }

    public String getDocumentId(String id) {
        String[] parts = id.split("\\.");
        return String.valueOf(Integer.parseInt(parts[2]));
    }


}
