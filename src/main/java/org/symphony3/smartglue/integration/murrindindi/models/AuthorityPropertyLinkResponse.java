package org.symphony3.smartglue.integration.murrindindi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityPropertyLinkResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("resultCount")
    private int resultCount;

    @JsonProperty("result")
    private List<ParcelResult> result;

    @JsonProperty("totalCount")
    private int totalCount;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("accessResult")
    private List<Object> accessResult;


    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public int getResultCount() { return resultCount; }
    public void setResultCount(int resultCount) { this.resultCount = resultCount; }

    public List<ParcelResult> getResult() { return result; }
    public void setResult(List<ParcelResult> result) { this.result = result; }

    public int getTotalCount() { return totalCount; }
    public void setTotalCount(int totalCount) { this.totalCount = totalCount; }

    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    public List<Object> getAccessResult() { return accessResult; }
    public void setAccessResult(List<Object> accessResult) { this.accessResult = accessResult; }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ParcelResult {

        @JsonProperty("parcelId")
        private int parcelId;

        @JsonProperty("sequenceNumber")
        private int sequenceNumber;

        @JsonProperty("moduleReference")
        private String moduleReference;

        @JsonProperty("formattedAccount")
        private String formattedAccount;

        @JsonProperty("accountNumber")
        private int accountNumber;

        public int getParcelId() { return parcelId; }
        public void setParcelId(int parcelId) { this.parcelId = parcelId; }

        public int getSequenceNumber() { return sequenceNumber; }
        public void setSequenceNumber(int sequenceNumber) { this.sequenceNumber = sequenceNumber; }

        public String getModuleReference() { return moduleReference; }
        public void setModuleReference(String moduleReference) { this.moduleReference = moduleReference; }

        public String getFormattedAccount() { return formattedAccount; }
        public void setFormattedAccount(String formattedAccount) { this.formattedAccount = formattedAccount; }

        public int getAccountNumber() { return accountNumber; }
        public void setAccountNumber(int accountNumber) { this.accountNumber = accountNumber; }
    }
}

