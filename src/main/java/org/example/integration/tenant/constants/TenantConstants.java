package org.example.integration.tenant.constants;


public class TenantConstants {
    private TenantConstants() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static final String HTTPS= "https://";
    public static final String AUTHORITY_HOST= "AUTHORITY_HOST";
    public static final String ASSETIC_HOST= "ASSETIC_HOST";

    //Council Constants
    public static final String TENANT_ID =System.getenv("TENANT_ID");
    public static final String TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC =System.getenv("TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC");
    public static final String TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA =System.getenv("TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA");

    //HTTP Constants
    public static final String ENDPOINT_TIMEOUTS ="30000"; // IN MILLISECONDS
    public static final String CONNECTION_ESTABLISHMENT_TIMEOUTS ="30000"; // IN MILLISECONDS
    public static final String STATUS_CODE_200 ="200";
    public static final String STATUS_CODE_201 ="201";
    public static final String STATUS_CODE_204 ="204";
    public static final String STATUS_CODE_300 ="300";
    public static final String STATUS_CODE_500 ="500";
    public static final String HEADER_CONTENT_TYPE ="Content-Type";
    public static final String HEADER_AUTHORIZATION ="Authorization";
    public static final String HEADER_AUTHORIZATION_BEARER ="Bearer ";
    public static final String HEADER_AUTHORIZATION_BASIC ="Basic ";

    public static final String HEADER_ACCEPT ="Accept";
    public static final String HEADER_PREFER ="Prefer";
    public static final String CONTENT_TYPE_APPLICATION_X_WWW_FORM_URLENCODED ="application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_APPLICATION_JSON ="application/json";
    public static final String HEADER_VALUE_RETURN_REPRESENTATION ="return=representation";
    public static final String METHOD_POST ="POST";
    public static final String METHOD_GET ="GET";
    public static final String METHOD_PATCH ="PATCH";
    public static final String METHOD_DELETE ="DELETE";


    //AWS Constants
    public static final String AWS_ACCESS_KEY_INTEGRATION =System.getenv("AWS_ACCESS_KEY_INTEGRATION");
    public static final String AWS_SECRET_KEY_INTEGRATION =System.getenv("AWS_SECRET_KEY_INTEGRATION");
    public static final String AWS_REGION =System.getenv("AWS_REGION_INTEGRATION");
    public static final String AWS_DYNAMO_INTEGRATION_RECORD_TABLE_NAME ="integrationrecords";
    public static final String AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY1 ="tenant_integrations_id";
    public static final String AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY2 ="source_id";
    public static final String AWS_BUCKET_NAME =System.getenv("AWS_BUCKET_NAME");
    public static final String AWS_TENANT_FOLDER ="TenantConfigurations";
    public static final String AWS_LAST_RUN_TIME_FOLDER ="last_run_time";
    public static final String AWS_LAST_RUNTIME_JSON ="last_runtime.json";
    public static final String AWS_CONNECT_INSTANCE_ID =System.getenv("AWS_CONNECT_INSTANCE_ID");


    //integrationCompletedEvent Constants
    public static final String COMPLETED_EVENT_VERSION ="2";
    public static final String COMPLETED_EVENT_DETAIL_TYPE ="IntegrationCompletedEvent";
    public static final String COMPLETED_EVENT_RECORD_TYPE_RECORD ="1";
    public static final String COMPLETED_EVENT_RECORD_TYPE_ATTACHMENT ="2";
    public static final String COMPLETED_EVENT_RECORD_TYPE_API ="3";
    public static final String COMPLETED_EVENT_URL =System.getenv("COMPLETED_EVENT_URL");
    public static final String COMPLETED_EVENT_API_KEY_HEADER ="X-API-Key";
    public static final String COMPLETED_EVENT_API_KEY_VALUE =System.getenv("COMPLETED_EVENT_API_KEY_VALUE");
    public static final String COMPLETED_EVENT_FAILED ="failed";
    public static final String COMPLETED_EVENT_PARTIAL_SUCCESS ="Partial success";
    public static final String COMPLETED_EVENT_SUCCESS ="success";


    //DynamoDB Constants
    public static final String DYNAMO_STATUS_SUCCESS ="success";
    public static final String DYNAMO_STATUS_RECEIVED ="received";
    public static final String DYNAMO_STATUS_FAILED ="failed";
    public static final String DYNAMO_STATUS_FAILED_ACTN_TASK_NOTES ="failed_actn";
    public static final String DYNAMO_WR_INSPECT_PREFIX ="wrInspect";
    public static final String DYNAMO_WR_REJECT_PREFIX ="wrReject";
    public static final String DYNAMO_WR_SUPPORT_INFO_PREFIX ="wrSupportInfo";
    public static final String DYNAMO_WO_NEW_PREFIX ="woNew";
    public static final String DYNAMO_WO_INPRG_PREFIX ="woINPRG";
    public static final String DYNAMO_WO_TCOMP_PREFIX ="woTCOMP";
    public static final String DYNAMO_WO_SUPPORT_INFO_PREFIX ="woSupportInfo";


    //Authority constants
    public static final String AUTHORITY_TOKEN_API =HTTPS+System.getenv(AUTHORITY_HOST)+"/idsrv/core/connect/token";
    public static final String AUTHORITY_GET_TASK_API =HTTPS+System.getenv(AUTHORITY_HOST)+"/main/api/v2/workflows/tasks";
    public static final String AUTHORITY_GET_CRM_API =HTTPS+System.getenv(AUTHORITY_HOST)+"/main/api/v2/crm/crm";
    public static final String AUTHORITY_GET_NAME_LINK_API =HTTPS+System.getenv(AUTHORITY_HOST)+"/main/api/v2/nameregister/namelinks";
    public static final String AUTHORITY_GET_NAME_DATA_API =HTTPS+System.getenv(AUTHORITY_HOST)+"/main/api/v2/nameregister/names/";
    public static final String AUTHORITY_GET_PROPERTY_LINK_API =HTTPS+System.getenv(AUTHORITY_HOST)+"/main/api/v2/property/propertylinks";
    public static final String AUTHORITY_GET_PROPERTY_API =HTTPS+System.getenv(AUTHORITY_HOST)+"/main/api/v2/property/propertyaddress";
    public static final String AUTHORITY_DOCUMENT_API =HTTPS+System.getenv(AUTHORITY_HOST)+"/main/api/v2/documents/documents";
    public static final String AUTHORITY_TASK_OPEN_API =HTTPS+System.getenv(AUTHORITY_HOST)+"/main/api/v2/workflows/tasksopen";
    public static final String AUTHORITY_ATTACHMENTS_METADATA_API =HTTPS+System.getenv(AUTHORITY_HOST)+"/main/api/v2/recordmanagement/attachmentmetadata";
    public static final String AUTHORITY_ATTACHMENTS_DOWNLOAD_API =HTTPS+System.getenv(AUTHORITY_HOST)+"/main/api/v2/recordmanagement/attachments";
    public static final String AUTHORITY_USER =System.getenv("AUTHORITY_USER");
    public static final String AUTHORITY_PASSWORD =System.getenv("AUTHORITY_PASSWORD");
    public static final String AUTHORITY_CLIENT_ID =System.getenv("AUTHORITY_CLIENT_ID");
    public static final String AUTHORITY_CLIENT_SECRET =System.getenv("AUTHORITY_CLIENT_SECRET");
    public static final String AUTHORITY_LAST_RUNTIME ="last_runtime_authority.json";
    public static final String AUTHORITY_CONFIG_BUCKET_NAME =System.getenv("AWS_BUCKET_NAME");
    public static final String AUTHORITY_CONFIG_FILE_NAME =System.getenv("AUTHORITY_CONFIG_CONFIGFILE_NAME");
    public static final String AUTHORITY_INVESTIGATE_TASK ="ACTN";
    public static final String AUTHORITY_FURTHER_INFO_TASK ="CRF";
    public static final String AUTHORITY_REJECTED_TASK ="ASS1";
    public static final String AUTHORITY_REACTIVE_INSPECTION_TASK ="ASS2";
    public static final String AUTHORITY_SUPPORT_INFO_TASK ="ASS5";
    public static final String AUTHORITY_TASK_CLOSE_CODE ="CAC";
    public static final String AUTHORITY_TASK_CLOSE_CODE_ACTN ="CCOM";
    public static final String AUTHORITY_WO_CREATED_TASK ="ASS3";
    public static final String AUTHORITY_WO_INPRG_TASK ="ASS4";
    public static final String AUTHORITY_REQUEST_FILTER_KEY ="?%24filter=";
    public static final String AUTHORITY_REQUEST_FORMATTED_ACC_FILTER_KEY ="formattedAccount eq '";


    //Assetic Constants
    public static final String ASSETIC_WORK_REQUEST_API =HTTPS+System.getenv(ASSETIC_HOST)+"/api/v2/workrequest";
    public static final String ASSETIC_WORK_ORDER_API =HTTPS+System.getenv(ASSETIC_HOST)+"/api/v2/workorder";
    public static final String ASSETIC_DOCUMENT_API =HTTPS+System.getenv(ASSETIC_HOST)+"/api/v2/document";
    public static final String ASSETIC_API_BASIC =System.getenv("ASSETIC_API_BASIC");
    public static final String ASSETIC_WR_LAST_RUNTIME ="last_runtime_assetic_work_request.json";
    public static final String ASSETIC_WO_LAST_RUNTIME ="last_runtime_assetic_work_order.json";
    public static final String ASSETIC_WR_STATUS_REJECTED ="Rejected";
    public static final String ASSETIC_WR_STATUS_CANCELLED ="Cancelled";
    public static final String ASSETIC_WO_STATUS_INPRG ="INPRG";
    public static final String ASSETIC_WO_STATUS_TCOMP ="TCOMP";
    public static final String ASSETIC_WO_STATUS_COMP ="COMP";
    public static final String ASSETIC_WO_STATUS_ASSES ="ASSESS";
    public static final String ASSETIC_SUPPORT_INFO_PREFIX_FROM_AUTHORITY ="Update from CRM integration: ";
    public static final String ASSETIC_EXTERNAL_IDENTIFIER_FILTER ="ExternalIdentifier";
    public static final String ASSETIC_ID_FILTER ="Id";
    public static final String ASSETIC_REQUEST_FILTER_KEY ="?requestParams.filters=";


    //Other constants
    public static final String SLASH ="/";

}
