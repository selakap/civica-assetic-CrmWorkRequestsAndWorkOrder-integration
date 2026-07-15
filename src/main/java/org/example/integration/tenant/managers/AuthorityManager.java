package org.example.integration.tenant.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.integration.tenant.constants.TenantConstants;
import org.example.integration.tenant.data.AuthorityTaskManagingData;
import org.example.integration.tenant.data.HttpRsponseData;
import org.example.integration.tenant.data.IntegrationCompletedEventData;
import org.example.integration.tenant.models.*;
import org.example.integration.tenant.operators.HttpOperator;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.integration.tenant.managers.EventManager.handleSGCompleteEvent;

public class AuthorityManager {
    private static final Logger log = LogManager.getLogger(AuthorityManager.class);
    private AuthorityManager() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String generateToken(String trackId) {
        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String authorityToken = null;
        String urlString = TenantConstants.AUTHORITY_TOKEN_API;
        String urlParameters = "grant_type=password" +
                "&username="+TenantConstants.AUTHORITY_USER +
                "&password="+TenantConstants.AUTHORITY_PASSWORD +
                "&client_id="+TenantConstants.AUTHORITY_CLIENT_ID +
                "&client_secret="+ TenantConstants.AUTHORITY_CLIENT_SECRET+
                "&scope=openid authority_client";
        Map<String, String> authorityTokenHeaders = new HashMap<>();
        authorityTokenHeaders.put(TenantConstants.HEADER_CONTENT_TYPE, TenantConstants.CONTENT_TYPE_APPLICATION_X_WWW_FORM_URLENCODED);
        String method = TenantConstants.METHOD_POST;

        HttpRsponseData dynamicsTokenResponse = HttpOperator.commonHttpCall(urlString, urlParameters, authorityTokenHeaders, method, trackId);

        try {
            if (dynamicsTokenResponse.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AuthorityTokenResponce authorityTokenResponce = mapper.readValue(dynamicsTokenResponse.getBody(), AuthorityTokenResponce.class);
                authorityToken = authorityTokenResponce.getAccessToken();
                log.info("{} Authority Token generated successfully ",trackId);
            } else {
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        " ", " ", TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(dynamicsTokenResponse.getStatusCode()), dynamicsTokenResponse.getBody(),Integer.parseInt(TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC));

                handleSGCompleteEvent(items, trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in generating Authority Token: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    " ", " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC));

            handleSGCompleteEvent(items, trackId);
        }
        return authorityToken;
    }

    public static AuthorityTaskResponse getAuthorityTask(String trackId, String authorityToken,
                                                                      String lastRunTime, int skip, String direction) {
        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        AuthorityTaskResponse taskResponse = null;
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BEARER+authorityToken);
        String query = "createdDateTime ge "+lastRunTime;

        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlString = TenantConstants.AUTHORITY_GET_TASK_API+TenantConstants.AUTHORITY_REQUEST_FILTER_KEY+
                encodedQuery+"&%24skip="+skip;

        try {

            String method = TenantConstants.METHOD_GET;
            HttpRsponseData authorityTaskListResponse = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (authorityTaskListResponse.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                taskResponse = mapper.readValue(authorityTaskListResponse.getBody(), AuthorityTaskResponse.class);
                log.info("{} task List fetched successfully",trackId);
            } else {
                log.error("{} Error in fetching authority task list",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        " ", " ", TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(authorityTaskListResponse.getStatusCode()), authorityTaskListResponse.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in fetching authority task list: {}",trackId,e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    " ", " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
        }

        return taskResponse;
    }

    public static AuthorityCrmResponse.ResultItem getCrmByFormattedAccount(String trackId, String formattedAccount,
                                                                           String authorityToken, String direction) {

        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        AuthorityCrmResponse.ResultItem crm = null;
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BEARER+authorityToken);
        String baseUrlString = TenantConstants.AUTHORITY_GET_CRM_API;
        String query = TenantConstants.AUTHORITY_REQUEST_FORMATTED_ACC_FILTER_KEY+formattedAccount+"'";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlString = baseUrlString+TenantConstants.AUTHORITY_REQUEST_FILTER_KEY+
                encodedQuery;

        try {

            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AuthorityCrmResponse responseObj = mapper.readValue(response.getBody(), AuthorityCrmResponse.class);

                if (responseObj.getResult() != null && !responseObj.getResult().isEmpty()) {
                    crm = responseObj.getResult().get(0);
                    log.info("{}  authority crm fetched successfully",trackId);
                } else {
                    log.info("{} No crm found in result for formatted account: {}",trackId,formattedAccount);
                }

            } else {
                log.error("{} Error in fetching authority crm",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        formattedAccount, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in fetching crm: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    formattedAccount, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
        }
        return crm;
    }

    public static AuthorityTaskResponse.Result getCrmTaskById(String trackId, String authorityToken,
                                                                      String taskId, String direction, String sourceId) {
        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        AuthorityTaskResponse.Result task = null;
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BEARER+authorityToken);
        String query = "id eq "+taskId;
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlString = TenantConstants.AUTHORITY_GET_TASK_API+TenantConstants.AUTHORITY_REQUEST_FILTER_KEY+
                encodedQuery;

        try {

            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AuthorityTaskResponse responseObj = mapper.readValue(response.getBody(), AuthorityTaskResponse.class);

                if (responseObj.getResult() != null && !responseObj.getResult().isEmpty()) {
                    task = responseObj.getResult().get(0);
                    log.info("{}  authority task fetched successfully",trackId);
                } else {
                    log.info("{} No task found in result for task id: {}",trackId,taskId);
                }
            } else {
                log.error("{} Error in fetching authority task",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in authority task by id: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
        }

        return task;
    }

    public static int getNameLink(String formattedAccount, String trackId, String authorityToken, String direction) {
        int nameLink = 0;
        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BEARER+authorityToken);
        String baseUrlString = TenantConstants.AUTHORITY_GET_NAME_LINK_API;
        String query = TenantConstants.AUTHORITY_REQUEST_FORMATTED_ACC_FILTER_KEY+formattedAccount+"'";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlString = baseUrlString+TenantConstants.AUTHORITY_REQUEST_FILTER_KEY+ encodedQuery;

        try {
            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AuthorityNameLinkResponse responseObj = mapper.readValue(response.getBody(), AuthorityNameLinkResponse.class);

                if (responseObj.getResult() != null && !responseObj.getResult().isEmpty()) {
                    AuthorityNameLinkResponse.ResultItem nameLinkObj = responseObj.getResult().get(0);
                    nameLink = nameLinkObj.getNameId();
                    log.info("{}  authority name link fetched successfully",trackId);
                } else {
                    log.info("{} No name link found in result for formatted account: {}",trackId,formattedAccount);
                    IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                            formattedAccount, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                            1, String.valueOf(response.getStatusCode()), response.getBody(),Integer.parseInt(direction));

                    handleSGCompleteEvent(items,trackId);
                }

            } else {
                log.error("{} Error in fetching authority name link",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        formattedAccount, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in fetching name link: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    formattedAccount, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
        }

        return nameLink;
    }

    public static AuthorityNameResponse.Result getNameData(int nameID, String trackId, String authorityToken,
                                                           String direction, String sourceId) {

        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        AuthorityNameResponse.Result nameData = null;
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BEARER+authorityToken);
        String baseUrlString = TenantConstants.AUTHORITY_GET_NAME_DATA_API;
        String urlString = baseUrlString+String.valueOf(nameID);

        try {
            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AuthorityNameResponse responseObj = mapper.readValue(response.getBody(), AuthorityNameResponse.class);

                if (responseObj.getResult() != null) {
                    nameData = responseObj.getResult();
                    log.info("{}  authority name fetched successfully",trackId);
                } else {
                    log.info("{} No name found in result",trackId);
                    IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                            sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                            1, String.valueOf(response.getStatusCode()), response.getBody(),Integer.parseInt(direction));

                    handleSGCompleteEvent(items,trackId);
                }

            } else {
                log.error("{} Error in fetching authority name",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(), Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in fetching name: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
        }

        return nameData;
    }

    public static int getPropertyLink(String formattedAccount, String trackId, String authorityToken) {
        int propertyLink = 0;
        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BEARER+authorityToken);
        String baseUrlString = TenantConstants.AUTHORITY_GET_PROPERTY_LINK_API;
        String query = TenantConstants.AUTHORITY_REQUEST_FORMATTED_ACC_FILTER_KEY+formattedAccount+"'";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlString = baseUrlString+TenantConstants.AUTHORITY_REQUEST_FILTER_KEY+ encodedQuery;

        try {
            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AuthorityPropertyLinkResponse responseObj = mapper.readValue(response.getBody(), AuthorityPropertyLinkResponse.class);

                if (responseObj.getResult() != null && !responseObj.getResult().isEmpty()) {
                    AuthorityPropertyLinkResponse.ParcelResult propertyLinkObj = responseObj.getResult().get(0);
                    propertyLink = propertyLinkObj.getParcelId();
                    log.info("{}  authority property link fetched successfully",trackId);
                } else {
                    log.info("{} No property link found in result for formatted account: {}",trackId,formattedAccount);
                }

            } else {
                log.error("{} Error in fetching authority property link",trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in fetching property Link: {}",trackId, e.toString());
        }

        return propertyLink;
    }

    public static AuthorityPropertyResponse.ParcelAddressResult getPropertyData(int parcelID, String trackId,
                                                                                String authorityToken, String direction, String sourceId) {

        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        AuthorityPropertyResponse.ParcelAddressResult propertyData = null;
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BEARER+authorityToken);
        String baseUrlString = TenantConstants.AUTHORITY_GET_PROPERTY_API;
        String query = "parcelId eq "+parcelID;
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlString = baseUrlString+TenantConstants.AUTHORITY_REQUEST_FILTER_KEY+ encodedQuery;

        try {
            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AuthorityPropertyResponse responseObj = mapper.readValue(response.getBody(), AuthorityPropertyResponse.class);

                if (responseObj.getResult() != null && !responseObj.getResult().isEmpty()) {
                    propertyData = responseObj.getResult().get(0);
                    log.info("{}  authority property fetched successfully",trackId);
                } else {
                    log.info("{} No property found in result",trackId);
                    IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                            sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                            1, String.valueOf(response.getStatusCode()), response.getBody(),Integer.parseInt(direction));

                    handleSGCompleteEvent(items,trackId);
                }

            } else {
                log.error("{} Error in fetching authority property",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in fetching property: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
        }

        return propertyData;
    }

    public static String getDescription(AuthorityCrmResponse.ResultItem crm, String trackId,
                                        String authorityToken, String direction) {

        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String description = "";
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BEARER+authorityToken);
        String baseUrlString = TenantConstants.AUTHORITY_GET_CRM_API;
        String urlString = baseUrlString+"/"+ crm.getId()+"/Description";

        try {
            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AuthorityCrmDescriptionResponse responseObj = mapper.readValue(response.getBody(), AuthorityCrmDescriptionResponse.class);

                if (responseObj.getResult() != null) {
                    description = responseObj.getResult().getDescription();
                    log.info("{}  authority crm description fetched successfully",trackId);
                } else {
                    log.info("{} No crm description found in result",trackId);
                }

            } else {
                log.error("{} Error in crm description authority property",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        crm.getFormattedAccount(), " ", TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in fetching crm description: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    crm.getFormattedAccount(), " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
        }

        return description;
    }


    public static HttpRsponseData updateTaskNotes(String trackId, String taskId, String authorityToken,
                                                  String note, String direction, String sourceId, String targetId) {

        Map<String, String> headers = new HashMap<>();
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BEARER+authorityToken);
        String baseUrlString = TenantConstants.AUTHORITY_GET_TASK_API;
        String urlString = baseUrlString+"/"+taskId+"/comments?comment="+ note;

        try {
            String method = TenantConstants.METHOD_POST;
            HttpRsponseData res = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (res.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                log.info("{} authority crm task notes updated successfully",trackId);
            } else {
                log.error("{} Error in updating crm task notes",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        sourceId, targetId, TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(res.getStatusCode()), res.getBody(), Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
            return res;

        } catch (Exception e) {
            log.error("{} Error in mailchimp Audience contact creation: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    sourceId, targetId, TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
            return new HttpRsponseData(e.getMessage(), Integer.parseInt(TenantConstants.STATUS_CODE_500));
        }
    }

    public static String getTaskNotes(String trackId, String taskId, String authorityToken, String direction, String sourceId,String targetId) {

        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BEARER+authorityToken);
        String note = null;
        String baseUrlString = TenantConstants.AUTHORITY_GET_TASK_API;
        String urlString = baseUrlString+"/"+taskId+"/commentformatted";

        try {
            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AuthorityTaskNotesResponse responseObj = mapper.readValue(response.getBody(), AuthorityTaskNotesResponse.class);

                if (responseObj.getResult() != null) {
                    note = responseObj.getResult().getCommentFormatted();
                    log.info("{}  authority crm task notes fetched successfully",trackId);
                } else {
                    log.info("{} No crm task notes found in result",trackId);
                }

            } else {
                log.error("{} Error in crm task notes authority property",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        sourceId, targetId, TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in fetching crm task notes: {}",trackId,e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    sourceId, targetId, TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
        }
        return note;
    }

    public static List<AuthorityTaskResponse.Result> getCrmTaskByFormattedAccount(String trackId, String authorityToken,
                                                                                  String formattedAccount, String direction, String sourceId) {
        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        List<AuthorityTaskResponse.Result> tasks = null;
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BEARER+authorityToken);
        String query = TenantConstants.AUTHORITY_REQUEST_FORMATTED_ACC_FILTER_KEY+formattedAccount+"'";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlString = TenantConstants.AUTHORITY_GET_TASK_API+TenantConstants.AUTHORITY_REQUEST_FILTER_KEY+
                encodedQuery;

        try {

            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AuthorityTaskResponse responseObj = mapper.readValue(response.getBody(), AuthorityTaskResponse.class);

                if (responseObj.getResult() != null && !responseObj.getResult().isEmpty()) {
                    tasks = responseObj.getResult();
                    log.info("{}  authority task list fetched successfully",trackId);
                } else {
                    log.info("{} No task found in result for id: {}",trackId,formattedAccount);
                }
            } else {
                log.error("{} Error in fetching authority task list",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        sourceId, formattedAccount, TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in authority task list by formattedAccount: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    sourceId, formattedAccount, TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
        }

        return tasks;
    }

    public static HttpRsponseData openTask(String trackId, String payload, String authorityToken, String direction, AuthorityTaskManagingData taskData) {

        Map<String, String> headers = new HashMap<>();
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BEARER+authorityToken);
        headers.put(TenantConstants.HEADER_CONTENT_TYPE, TenantConstants.CONTENT_TYPE_APPLICATION_JSON);
        String urlString = TenantConstants.AUTHORITY_TASK_OPEN_API;


        try {
            String method = TenantConstants.METHOD_POST;
            HttpRsponseData res = HttpOperator.commonHttpCall(urlString, payload, headers, method, trackId);
            if (res.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                log.info("{} authority task opened successfully",trackId);
            } else {
                log.error("{} Error in opening authority task",trackId);

                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        taskData.getFriendlyIdStr(), taskData.getFormattedAccount(), TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(res.getStatusCode()), res.getBody(), Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
            return res;
        } catch (Exception e) {
            log.error("{} Error in opening authority task: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    taskData.getFriendlyIdStr(), taskData.getFormattedAccount(), TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
            return new HttpRsponseData(e.getMessage(), Integer.parseInt(TenantConstants.STATUS_CODE_500));
        }
    }

    public static HttpRsponseData closeTask(String trackId, String taskId, String payload,
                                            String authorityToken, String direction,AuthorityTaskManagingData taskData) {

        Map<String, String> headers = new HashMap<>();
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BEARER+authorityToken);
        headers.put(TenantConstants.HEADER_CONTENT_TYPE, TenantConstants.CONTENT_TYPE_APPLICATION_JSON);
        String baseUrlString = TenantConstants.AUTHORITY_GET_TASK_API;
        String urlString = baseUrlString+"/"+taskId+"/complete";


        try {
            String method = TenantConstants.METHOD_POST;
            HttpRsponseData res = HttpOperator.commonHttpCall(urlString, payload, headers, method, trackId);
            if (res.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                log.info("{} authority task closed successfully",trackId);
            } else {
                log.error("{} Error in closing authority task",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        taskData.getFriendlyIdStr(), taskData.getFormattedAccount(), TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(res.getStatusCode()), res.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
            return res;
        } catch (Exception e) {
            log.error("{} Error in closing authority task: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    taskData.getFriendlyIdStr(), taskData.getFormattedAccount(), TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
            return new HttpRsponseData(e.getMessage(), Integer.parseInt(TenantConstants.STATUS_CODE_500));
        }
    }


    public static List<AuthorityAttachmentMetadataResponse.AttachmentMetadata> getAttachmentsByFormattedAccount(String trackId,
                                                                                                                String authorityToken, String payload, String direction, String sourceId, String targetId) {
        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        List<AuthorityAttachmentMetadataResponse.AttachmentMetadata> attachments = null;
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BEARER+authorityToken);
        headers.put(TenantConstants.HEADER_CONTENT_TYPE, TenantConstants.CONTENT_TYPE_APPLICATION_JSON);
        String baseUrlString = TenantConstants.AUTHORITY_ATTACHMENTS_METADATA_API;

        try {

            String method = TenantConstants.METHOD_POST;
            HttpRsponseData response = HttpOperator.commonHttpCall(baseUrlString, payload, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AuthorityAttachmentMetadataResponse responseObj = mapper.readValue(response.getBody(), AuthorityAttachmentMetadataResponse.class);

                if (responseObj.getResult() != null && !responseObj.getResult().isEmpty()) {
                    attachments = responseObj.getResult().get(0).getAttachmentMetadata();
                    log.info("{}  authority attachment metadata fetched successfully",trackId);
                }
            } else {
                log.error("{} Error in fetching authority attachment metadata",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        sourceId, targetId, TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in fetching authority attachment metadata: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    sourceId, targetId, TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
        }

        return attachments;
    }


    public static HttpRsponseData getAttachmentsByDownloadId(String trackId,String authorityToken, String downloadId, String direction, String sourceId, String targetId) {
        Map<String, String> headers = new HashMap<>();
        List<AuthorityAttachmentMetadataResponse.AttachmentMetadata> attachments = null;
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BEARER+authorityToken);
        String baseUrlString = TenantConstants.AUTHORITY_ATTACHMENTS_DOWNLOAD_API+"/"+downloadId;

        try {

            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCallForBinaryResponse(baseUrlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                return response;
            } else {
                log.error("{} Error in fetching authority attachment",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        sourceId, targetId, TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in authority attachments: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    sourceId, targetId, TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
        }

        return null;
    }

    public static AuthorityGPSLocationResponse.Coordinate getGPSLocationByCrm(String trackId, String authorityToken,
                                                                              String crmId, String sourceId, String direction) {
        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        AuthorityGPSLocationResponse.Coordinate coordinate = null;
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BEARER+authorityToken);
        headers.put(TenantConstants.HEADER_CONTENT_TYPE, TenantConstants.CONTENT_TYPE_APPLICATION_JSON);
        String baseUrlString = TenantConstants.AUTHORITY_GET_CRM_API+"/"+crmId+"/gpsLocations";

        try {

            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(baseUrlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AuthorityGPSLocationResponse responseObj = mapper.readValue(response.getBody(), AuthorityGPSLocationResponse.class);

                if (responseObj.getResult() != null && !responseObj.getResult().isEmpty() &&
                        responseObj.getResult().get(0).getCoordinates() != null && !responseObj.getResult().get(0).getCoordinates().isEmpty()) {
                    coordinate = responseObj.getResult().get(0).getCoordinates().get(0);
                    log.info("{}  authority GPS location fetched successfully",trackId);
                } else {
                    log.error("{} Empty GPS location authority",trackId);
                    IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                            sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                            1, TenantConstants.STATUS_CODE_500, "Empty GPS location / property Link from Authority",Integer.parseInt(direction));

                    handleSGCompleteEvent(items,trackId);
                }
            } else {
                log.error("{} Error in fetching GPS location authority",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Exception in fetching GPS location: {}",trackId,e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
        }

        return coordinate;
    }
}
