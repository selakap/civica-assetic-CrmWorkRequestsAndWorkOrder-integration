package org.symphony3.smartglue.integration.murrindindi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AsseticDocumentRequest {

    @JsonProperty("DocumentWorkRequest")
    private DocumentWorkRequest documentWorkRequest;

    @JsonProperty("ExternalId")
    private String externalId;

    @JsonProperty("FileProperty")
    private List<FileProperty> fileProperty;

    // Getters and Setters
    public DocumentWorkRequest getDocumentWorkRequest() {
        return documentWorkRequest;
    }

    public void setDocumentWorkRequest(DocumentWorkRequest documentWorkRequest) {
        this.documentWorkRequest = documentWorkRequest;
    }

    public List<FileProperty> getFileProperty() {
        return fileProperty;
    }

    public void setFileProperty(List<FileProperty> fileProperty) {
        this.fileProperty = fileProperty;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class DocumentWorkRequest {

        @JsonProperty("WorkRequestId")
        private String workRequestId;

        public String getWorkRequestId() {
            return workRequestId;
        }

        public void setWorkRequestId(String workRequestId) {
            this.workRequestId = workRequestId;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class FileProperty {

        @JsonProperty("Name")
        private String name;

        @JsonProperty("mimetype")
        private String mimetype;

        @JsonProperty("filecontent")
        private String filecontent;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMimetype() {
            return mimetype;
        }

        public void setMimetype(String mimetype) {
            this.mimetype = mimetype;
        }

        public String getFilecontent() {
            return filecontent;
        }

        public void setFilecontent(String filecontent) {
            this.filecontent = filecontent;
        }
    }
}

