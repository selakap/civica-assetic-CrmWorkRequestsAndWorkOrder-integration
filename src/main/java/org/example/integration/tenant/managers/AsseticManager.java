package org.example.integration.tenant.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.integration.tenant.constants.TenantConstants;
import org.example.integration.tenant.data.HttpRsponseData;
import org.example.integration.tenant.data.IntegrationCompletedEventData;
import org.example.integration.tenant.models.*;
import org.example.integration.tenant.operators.HttpOperator;
import org.example.integration.tenant.operators.S3Operator;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.integration.tenant.managers.EventManager.handleSGCompleteEvent;

public class AsseticManager {
    private static final Logger log = LogManager.getLogger(AsseticManager.class);
    private AsseticManager() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static HttpRsponseData createWorkRequest(String trackId, String payload, String direction, String sourceId) {
        Map<String, String> headers = new HashMap<>();
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BASIC+TenantConstants.ASSETIC_API_BASIC);
        String error = null;
        String urlString = TenantConstants.ASSETIC_WORK_REQUEST_API;

        try {
            String method = TenantConstants.METHOD_POST;
            HttpRsponseData res = HttpOperator.commonHttpCall(urlString, payload, headers, method, trackId);
            if (res.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_201)) {
                log.info("{} assetic work request created successfully",trackId);
                return res;
            } else {
                log.error("{} Error in assetic work request creation",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(res.getStatusCode()), res.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
                return new HttpRsponseData(res.getBody(), Integer.parseInt(TenantConstants.STATUS_CODE_500));
            }
        } catch (Exception e) {
            log.error("{} Error in work request creation: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
            return new HttpRsponseData(e.getMessage(), Integer.parseInt(TenantConstants.STATUS_CODE_500));
        }
    }

    public static AsseticWorkRequestByExternalIdResponse.Resource getWorkRequestByFilter(String trackId, String externalId,
                                                                                         String filterAttribute, String direction, String sourceId) {
        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        AsseticWorkRequestByExternalIdResponse.Resource workRequestData = null;
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BASIC+TenantConstants.ASSETIC_API_BASIC);
        String baseUrlString = TenantConstants.ASSETIC_WORK_REQUEST_API;
        String query = filterAttribute+"~eq~'"+externalId+"'";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlString = baseUrlString+TenantConstants.ASSETIC_REQUEST_FILTER_KEY+ encodedQuery;

        try {
            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AsseticWorkRequestByExternalIdResponse responseObj = mapper.readValue(response.getBody(),
                                                                        AsseticWorkRequestByExternalIdResponse.class);

                if (responseObj.getResourceList() != null && !responseObj.getResourceList().isEmpty()) {
                    workRequestData = responseObj.getResourceList().get(0);
                    log.info("{}  assetic W/R fetched successfully",trackId);
                } else {
                    log.info("{} No assetic W/R found in result",trackId);
                }

            } else {
                log.error("{} Error in fetching assetic W/R",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in fetching assetic W/R: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
        }
        return workRequestData;
    }

    /*public static AsseticWorkRequestByExternalIdResponse.Resource getWorkRequestByFriendlyIdStr(String trackId, String friendlyIdStr) {
        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        AsseticWorkRequestByExternalIdResponse.Resource workRequestData = null;
        headers.put("Authorization", "Basic "+TenantConstants.ASSETIC_API_BASIC);
        String error = null;
        String baseUrlString = TenantConstants.ASSETIC_WORK_REQUEST_API;
        String query = "FriendlyIdStr~eq~'"+friendlyIdStr+"'";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlString = baseUrlString+"?requestParams.filters="+ encodedQuery;

        try {
            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AsseticWorkRequestByExternalIdResponse responseObj = mapper.readValue(response.getBody(),
                        AsseticWorkRequestByExternalIdResponse.class);

                if (responseObj.getResourceList() != null && !responseObj.getResourceList().isEmpty()) {
                    workRequestData = responseObj.getResourceList().get(0);
                    log.info(trackId + "  assetic W/R fetched successfully");
                } else {
                    log.info(trackId+" No assetic W/R found in result");
                }

            } else {
                log.error(trackId + " Error in fetching assetic W/R");
                error = response.getBody();
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        "assetic W/R retrieval", " ", TenantConstants.COMPLETED_EVENT_FAILED,
                        1, TenantConstants.STATUS_CODE_500, error);

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error(trackId + " Error in fetching assetic W/R: " + e.toString());
            error = e.toString();
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    "assetic W/R retrieval", " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, error);

            handleSGCompleteEvent(items,trackId);
        }
        return workRequestData;
    }*/

    public static AsseticGetWorkOrderByTimeResponse.WorkOrder getWorkOrderByExternalId(String trackId,
                                                                                       String externalId, String direction, String sourceId) {
        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        AsseticGetWorkOrderByTimeResponse.WorkOrder workOrderData = null;
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BASIC+TenantConstants.ASSETIC_API_BASIC);
        String baseUrlString = TenantConstants.ASSETIC_WORK_ORDER_API;
        String query = "ExternalId~contains~'"+externalId+"'";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlString = baseUrlString+TenantConstants.ASSETIC_REQUEST_FILTER_KEY+ encodedQuery;

        try {
            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AsseticGetWorkOrderByTimeResponse responseObj = mapper.readValue(response.getBody(),
                        AsseticGetWorkOrderByTimeResponse.class);

                if (responseObj.getResourceList() != null && !responseObj.getResourceList().isEmpty()) {
                    workOrderData = responseObj.getResourceList().get(0);
                    log.info("{}  assetic W/O fetched successfully",trackId);
                } else {
                    log.info("{} No assetic W/O found in result",trackId);
                }

            } else {
                log.error("{} Error in fetching assetic W/O",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in fetching assetic W/O: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    sourceId, " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
        }
        return workOrderData;
    }


    public static HttpRsponseData addSupportInfoToWorkRequest(String trackId, String payload,String wrId,
                                                              String direction, String sourceId) {
        Map<String, String> headers = new HashMap<>();
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BASIC+TenantConstants.ASSETIC_API_BASIC);
        String baseUrlString = TenantConstants.ASSETIC_WORK_REQUEST_API;
        String urlString = baseUrlString+"/"+wrId+"/supportinginfo";

        try {
            String method = TenantConstants.METHOD_POST;
            HttpRsponseData res = HttpOperator.commonHttpCall(urlString, payload, headers, method, trackId);
            if (res.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_201)) {
                log.info("{} assetic work request supporting info created successfully",trackId);
            } else {
                log.error("{} Error in assetic work request supporting info creation",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        sourceId, wrId, TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(res.getStatusCode()), res.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
            return res;
        } catch (Exception e) {
            log.error("{} Error in work request creation: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    sourceId, wrId, TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
            return new HttpRsponseData(e.getMessage(), Integer.parseInt(TenantConstants.STATUS_CODE_500));
        }
    }

    public static List<AsseticWorkRequestByExternalIdResponse.Resource> getWorkRequestByLastModifiedTime(String trackId,
                                                                                                         String lastModifiedTime, String nextRunTime, String direction) {
        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        List<AsseticWorkRequestByExternalIdResponse.Resource> workRequestData = null;
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BASIC+TenantConstants.ASSETIC_API_BASIC);
        String baseUrlString = TenantConstants.ASSETIC_WORK_REQUEST_API;
        String query = "LastModified~gte~'"+lastModifiedTime+"'~or~ReactiveInspectionDate~gte~'"+lastModifiedTime+"'";
        //This ReactiveInspectionDate filter is due to limitation on assetic where inspector assign operation does not update
        //the LastModified date of the W/R.
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlString = baseUrlString+TenantConstants.ASSETIC_REQUEST_FILTER_KEY+ encodedQuery;

        try {
            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AsseticWorkRequestByExternalIdResponse responseObj = mapper.readValue(response.getBody(),
                        AsseticWorkRequestByExternalIdResponse.class);
                S3Operator.updateLastRuntime(trackId,nextRunTime,TenantConstants.ASSETIC_WR_LAST_RUNTIME);

                if (responseObj.getResourceList() != null && !responseObj.getResourceList().isEmpty()) {
                    workRequestData = responseObj.getResourceList();
                    log.info("{}  assetic W/R fetched successfully",trackId);
                } else {
                    log.info("{} No assetic W/R found in result",trackId);
                }

            } else {
                log.error("{} Error in fetching assetic W/R",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        " ", " ", TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(), Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in fetching assetic W/R: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    " ", " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
        }
        return workRequestData;
    }

    public static List<AsseticGetWorkOrderByTimeResponse.WorkOrder> getWorkOrdersByLastModifiedTime(String trackId,
                                                                                                    String lastModifiedTime, String nextRunTime, String direction) {
        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        List<AsseticGetWorkOrderByTimeResponse.WorkOrder> workOrderData = null;
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BASIC+TenantConstants.ASSETIC_API_BASIC);
        String baseUrlString = TenantConstants.ASSETIC_WORK_ORDER_API;
        String query = "LastModified~gte~'"+lastModifiedTime+"'";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlString = baseUrlString+TenantConstants.ASSETIC_REQUEST_FILTER_KEY+ encodedQuery;

        try {
            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AsseticGetWorkOrderByTimeResponse responseObj = mapper.readValue(response.getBody(),
                        AsseticGetWorkOrderByTimeResponse.class);
                S3Operator.updateLastRuntime(trackId,nextRunTime,TenantConstants.ASSETIC_WO_LAST_RUNTIME);
                if (responseObj.getResourceList() != null && !responseObj.getResourceList().isEmpty()) {
                    workOrderData = responseObj.getResourceList();
                    log.info("{}  assetic W/O fetched successfully",trackId);
                } else {
                    log.info("{} No assetic W/O found in result",trackId);
                }

            } else {
                log.error("{} Error in fetching assetic W/O",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        " ", " ", TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(), Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in fetching assetic W/O: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    " ", " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
        }
        return workOrderData;
    }

    public static HttpRsponseData getDocumentById(String trackId, String externalId, String direction, String sourceId, String targetId) {
        Map<String, String> headers = new HashMap<>();
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BASIC+TenantConstants.ASSETIC_API_BASIC);
        String baseUrlString = TenantConstants.ASSETIC_DOCUMENT_API;
        String query = "ExternalId~eq~'"+externalId+"'";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlString = baseUrlString+TenantConstants.ASSETIC_REQUEST_FILTER_KEY+ encodedQuery;

        try {
            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                log.info("{} assetic document fetched successfully",trackId);
            } else {
                log.error("{} Error in assetic document retrieval",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        sourceId, targetId, TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(),Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
            return response;
        } catch (Exception e) {
            log.error("{} Error in assetic document retrieval: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    sourceId, targetId, TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);
            return new HttpRsponseData(e.getMessage(), Integer.parseInt(TenantConstants.STATUS_CODE_500));
        }
    }

    /*public static AsseticGetDocumentResponse.Resource getDocumentById(String trackId, String externalId) {
        Map<String, String> headers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        AsseticGetDocumentResponse.Resource attachmentData = null;
        headers.put("Authorization", "Basic "+TenantConstants.ASSETIC_API_BASIC);
        String error = null;
        String baseUrlString = TenantConstants.ASSETIC_DOCUMENT_API;
        String query = "ExternalId~contains~'"+externalId+"'";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlString = baseUrlString+"?requestParams.filters="+ encodedQuery;

        try {
            String method = TenantConstants.METHOD_GET;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, null, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_200)) {
                AsseticGetDocumentResponse responseObj = mapper.readValue(response.getBody(),
                        AsseticGetDocumentResponse.class);

                if (responseObj.getResourceList() != null && !responseObj.getResourceList().isEmpty()) {
                    attachmentData = responseObj.getResourceList().get(0);
                    log.info(trackId + "  assetic attachment fetched successfully");
                } else {
                    log.info(trackId+" No assetic attachment found in result");
                }

            } else {
                log.error(trackId + " Error in fetching assetic attachment");
                error = response.getBody();
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        "assetic attachment retrieval", " ", TenantConstants.COMPLETED_EVENT_FAILED,
                        1, TenantConstants.STATUS_CODE_500, error);

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error(trackId + " Error in fetching assetic attachment: " + e.toString());
            error = e.toString();
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    "assetic attachment retrieval", " ", TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, error);

            handleSGCompleteEvent(items,trackId);
        }
        return attachmentData;
    }*/

    public static boolean uploadDocuments(String trackId, String payload, String direction, String sourceId, String targetId) {
        Map<String, String> headers = new HashMap<>();
        headers.put(TenantConstants.HEADER_AUTHORIZATION, TenantConstants.HEADER_AUTHORIZATION_BASIC+TenantConstants.ASSETIC_API_BASIC);
        String urlString = TenantConstants.ASSETIC_DOCUMENT_API;

        try {
            String method = TenantConstants.METHOD_POST;
            HttpRsponseData response = HttpOperator.commonHttpCall(urlString, payload, headers, method, trackId);
            if (response.getStatusCode() == Integer.parseInt(TenantConstants.STATUS_CODE_201)) {
                log.info("{} assetic document uploaded successfully",trackId);
                return true;
            } else {
                log.error("{} Error in assetic attachments upload",trackId);
                IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                        sourceId, targetId, TenantConstants.COMPLETED_EVENT_FAILED,
                        1, String.valueOf(response.getStatusCode()), response.getBody(), Integer.parseInt(direction));

                handleSGCompleteEvent(items,trackId);
            }
        } catch (Exception e) {
            log.error("{} Error in assetic attachments upload: {}",trackId, e.toString());
            IntegrationCompletedEventData items = new IntegrationCompletedEventData(Integer.parseInt(TenantConstants.COMPLETED_EVENT_RECORD_TYPE_RECORD),
                    sourceId, targetId, TenantConstants.COMPLETED_EVENT_FAILED,
                    1, TenantConstants.STATUS_CODE_500, e.toString(),Integer.parseInt(direction));

            handleSGCompleteEvent(items,trackId);

        }
        return false;
    }
}
