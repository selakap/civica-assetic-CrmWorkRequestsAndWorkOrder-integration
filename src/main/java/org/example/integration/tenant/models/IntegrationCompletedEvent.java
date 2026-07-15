package org.example.integration.tenant.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class IntegrationCompletedEvent {

    @JsonProperty("detail")
    private Detail detail;

    // Getters and Setters
    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public static class Detail {

        @JsonProperty("version")
        private int version;

        @JsonProperty("detail_type")
        private String detailType;

        @JsonProperty("recordType")
        private int recordType;

        @JsonProperty("TenantId")
        private int tenantId;

        @JsonProperty("TenantIntegrationId")
        private int tenantIntegrationId;

        @JsonProperty("sourceId")
        private String sourceId;

        @JsonProperty("targetId")
        private String targetId;

        @JsonProperty("Status")
        private String status;

        @JsonProperty("retry")
        private int retry;

        @JsonProperty("Timestamp")
        private String timestamp;

        @JsonProperty("UpdatedAt")
        private String updatedAt;

        @JsonProperty("Logs")
        private List<LogEntry> logs;

        // Getters and Setters
        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getDetailType() {
            return detailType;
        }

        public void setDetailType(String detailType) {
            this.detailType = detailType;
        }

        public int getRecordType() {
            return recordType;
        }

        public void setRecordType(int recordType) {
            this.recordType = recordType;
        }

        public int getTenantId() {
            return tenantId;
        }

        public void setTenantId(int tenantId) {
            this.tenantId = tenantId;
        }

        public int getTenantIntegrationId() {
            return tenantIntegrationId;
        }

        public void setTenantIntegrationId(int tenantIntegrationId) {
            this.tenantIntegrationId = tenantIntegrationId;
        }

        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getRetry() {
            return retry;
        }

        public void setRetry(int retry) {
            this.retry = retry;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public List<LogEntry> getLogs() {
            return logs;
        }

        public void setLogs(List<LogEntry> logs) {
            this.logs = logs;
        }
    }

    public static class LogEntry {

        @JsonProperty("Status")
        private String status;

        @JsonProperty("SyncTime")
        private String syncTime;

        @JsonProperty("ResponseCode")
        private String responseCode;

        @JsonProperty("Response")
        private String response;

        // Getters and Setters
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSyncTime() {
            return syncTime;
        }

        public void setSyncTime(String syncTime) {
            this.syncTime = syncTime;
        }

        public String getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(String responseCode) {
            this.responseCode = responseCode;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }
}