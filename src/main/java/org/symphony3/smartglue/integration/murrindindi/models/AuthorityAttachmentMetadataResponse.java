package org.symphony3.smartglue.integration.murrindindi.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AuthorityAttachmentMetadataResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("resultCount")
    private int resultCount;

    @JsonProperty("result")
    private List<Result> result;

    @JsonProperty("totalCount")
    private int totalCount;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("accessResult")
    private List<String> accessResult;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<String> getAccessResult() {
        return accessResult;
    }

    public void setAccessResult(List<String> accessResult) {
        this.accessResult = accessResult;
    }
// Getters and setters

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class Result {
        @JsonProperty("formattedAccount")
        private String formattedAccount;

        @JsonProperty("attachmentMetadata")
        private List<AttachmentMetadata> attachmentMetadata;

        public String getFormattedAccount() {
            return formattedAccount;
        }

        public void setFormattedAccount(String formattedAccount) {
            this.formattedAccount = formattedAccount;
        }

        public List<AttachmentMetadata> getAttachmentMetadata() {
            return attachmentMetadata;
        }

        public void setAttachmentMetadata(List<AttachmentMetadata> attachmentMetadata) {
            this.attachmentMetadata = attachmentMetadata;
        }
// Getters and setters
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class AttachmentMetadata {
        @JsonProperty("allowDownload")
        private boolean allowDownload;

        @JsonProperty("displayName")
        private String displayName;

        @JsonProperty("created")
        private String created;

        @JsonProperty("downloadId")
        private String downloadId;

        @JsonProperty("externalAttachmentType")
        private String externalAttachmentType;

        @JsonProperty("fileName")
        private String fileName;

        @JsonProperty("formattedAccount")
        private String formattedAccount;

        @JsonProperty("held")
        private boolean held;

        @JsonProperty("id")
        private String id;

        @JsonProperty("inTransit")
        private boolean inTransit;

        @JsonProperty("moduleReference")
        private String moduleReference;

        @JsonProperty("size")
        private long size;

        @JsonProperty("url")
        private String url;

        @JsonProperty("useDownloadUrl")
        private boolean useDownloadUrl;

        public boolean isAllowDownload() {
            return allowDownload;
        }

        public void setAllowDownload(boolean allowDownload) {
            this.allowDownload = allowDownload;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getDownloadId() {
            return downloadId;
        }

        public void setDownloadId(String downloadId) {
            this.downloadId = downloadId;
        }

        public String getExternalAttachmentType() {
            return externalAttachmentType;
        }

        public void setExternalAttachmentType(String externalAttachmentType) {
            this.externalAttachmentType = externalAttachmentType;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFormattedAccount() {
            return formattedAccount;
        }

        public void setFormattedAccount(String formattedAccount) {
            this.formattedAccount = formattedAccount;
        }

        public boolean isHeld() {
            return held;
        }

        public void setHeld(boolean held) {
            this.held = held;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isInTransit() {
            return inTransit;
        }

        public void setInTransit(boolean inTransit) {
            this.inTransit = inTransit;
        }

        public String getModuleReference() {
            return moduleReference;
        }

        public void setModuleReference(String moduleReference) {
            this.moduleReference = moduleReference;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUseDownloadUrl() {
            return useDownloadUrl;
        }

        public void setUseDownloadUrl(boolean useDownloadUrl) {
            this.useDownloadUrl = useDownloadUrl;
        }
// Getters and setters
    }
}

