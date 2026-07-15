package org.example.integration.tenant.data;

public class IntegrationCompletedEventData {
    private final int recordType;
    private final String sourceId;
    private final String targetId;
    private final String status;
    private final int retryCount;
    private final String responseCode;
    private final String response;
    private final int tenantIntegrationId;

    public IntegrationCompletedEventData(int recordType, String sourceId, String targetId, String status, int retryCount, String responseCode, String response, int tenantIntegrationId) {
        this.recordType = recordType;
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.status = status;
        this.retryCount = retryCount;
        this.responseCode = responseCode;
        this.response = response;
        this.tenantIntegrationId = tenantIntegrationId;
    }

    public int getRecordType() { return recordType; }
    public String getSourceId() { return sourceId; }
    public String getTargetId() { return targetId; }
    public String getStatus() { return status; }
    public int getRetryCount() { return retryCount; }
    public String getResponseCode() { return responseCode; }
    public String getResponse() { return response; }
    public int getTenantIntegrationId() {
        return tenantIntegrationId;
    }
}
