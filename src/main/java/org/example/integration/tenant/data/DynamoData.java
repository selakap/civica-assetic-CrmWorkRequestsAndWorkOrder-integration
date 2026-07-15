package org.example.integration.tenant.data;

public class DynamoData {
    private String tenantIntegrationsId;
    private String sourceId;
    private String targetId;
    private String status;
    private String retryCount;
    private String updatedAt;
    private String createdAt;
    private String json;
    private boolean isNew;
    private boolean isFailed;
    private boolean isRetriable;



    public String getTenantIntegrationsId() {
        return tenantIntegrationsId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public String getStatus() {
        return status;
    }

    public String getRetryCount() {
        return retryCount;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getJson() {
        return json;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isFailed() {
        return isFailed;
    }

    public void setFailed(boolean failed) {
        isFailed = failed;
    }

    public boolean isRetriable() {
        return isRetriable;
    }

    public void setRetriable(boolean rtriable) {
        isRetriable = rtriable;
    }

    public void setTenantIntegrationsId(String tenantIntegrationsId) {
        this.tenantIntegrationsId = tenantIntegrationsId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRetryCount(String retryCount) {
        this.retryCount = retryCount;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
