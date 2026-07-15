package org.symphony3.smartglue.integration.murrindindi.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityTaskNotesResponse {

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

    @JsonProperty("accessResult")
    private List<Object> accessResult;

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

    public List<Object> getAccessResult() {
        return accessResult;
    }

    public void setAccessResult(List<Object> accessResult) {
        this.accessResult = accessResult;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        @JsonProperty("commentFormatted")
        private String commentFormatted;

        // Getter and Setter
        public String getCommentFormatted() {
            return commentFormatted;
        }

        public void setCommentFormatted(String commentFormatted) {
            this.commentFormatted = commentFormatted;
        }
    }
}
