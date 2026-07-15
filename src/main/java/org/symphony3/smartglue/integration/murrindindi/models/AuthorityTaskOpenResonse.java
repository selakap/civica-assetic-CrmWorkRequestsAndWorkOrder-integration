package org.symphony3.smartglue.integration.murrindindi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AuthorityTaskOpenResonse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("resultCount")
    private int resultCount;

    @JsonProperty("result")
    private Result result;

    @JsonProperty("totalCount")
    private int totalCount;

    @JsonProperty("requestId")
    private String requestId;

    // Getters and Setters...

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
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

    // Inner Classes

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class Result {

        @JsonProperty("openTaskResponseData")
        private List<OpenTaskResponseData> openTaskResponseData;

        public List<OpenTaskResponseData> getOpenTaskResponseData() {
            return openTaskResponseData;
        }

        public void setOpenTaskResponseData(List<OpenTaskResponseData> openTaskResponseData) {
            this.openTaskResponseData = openTaskResponseData;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class OpenTaskResponseData {

        @JsonProperty("id")
        private long id;

        @JsonProperty("description")
        private String description;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}

