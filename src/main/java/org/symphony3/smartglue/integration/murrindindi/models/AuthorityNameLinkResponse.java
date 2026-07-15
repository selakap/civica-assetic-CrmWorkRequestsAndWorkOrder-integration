package org.symphony3.smartglue.integration.murrindindi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityNameLinkResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("resultCount")
    private int resultCount;

    @JsonProperty("result")
    private List<ResultItem> result;

    @JsonProperty("totalCount")
    private int totalCount;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("accessResult")
    private List<String> accessResult;

    // Getters and Setters

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

    public List<ResultItem> getResult() {
        return result;
    }

    public void setResult(List<ResultItem> result) {
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResultItem {

        @JsonProperty("nameId")
        private int nameId;

        @JsonProperty("moduleReference")
        private String moduleReference;

        @JsonProperty("formattedAccount")
        private String formattedAccount;

        @JsonProperty("moduleAccount")
        private int moduleAccount;

        @JsonProperty("familiarName")
        private String familiarName;

        @JsonProperty("alphaSortKey")
        private String alphaSortKey;

        @JsonProperty("pensionerType")
        private String pensionerType;

        @JsonProperty("pensionerNumber")
        private String pensionerNumber;

        // Getters and Setters

        public int getNameId() {
            return nameId;
        }

        public void setNameId(int nameId) {
            this.nameId = nameId;
        }

        public String getModuleReference() {
            return moduleReference;
        }

        public void setModuleReference(String moduleReference) {
            this.moduleReference = moduleReference;
        }

        public String getFormattedAccount() {
            return formattedAccount;
        }

        public void setFormattedAccount(String formattedAccount) {
            this.formattedAccount = formattedAccount;
        }

        public int getModuleAccount() {
            return moduleAccount;
        }

        public void setModuleAccount(int moduleAccount) {
            this.moduleAccount = moduleAccount;
        }

        public String getFamiliarName() {
            return familiarName;
        }

        public void setFamiliarName(String familiarName) {
            this.familiarName = familiarName;
        }

        public String getAlphaSortKey() {
            return alphaSortKey;
        }

        public void setAlphaSortKey(String alphaSortKey) {
            this.alphaSortKey = alphaSortKey;
        }

        public String getPensionerType() {
            return pensionerType;
        }

        public void setPensionerType(String pensionerType) {
            this.pensionerType = pensionerType;
        }

        public String getPensionerNumber() {
            return pensionerNumber;
        }

        public void setPensionerNumber(String pensionerNumber) {
            this.pensionerNumber = pensionerNumber;
        }
    }
}

