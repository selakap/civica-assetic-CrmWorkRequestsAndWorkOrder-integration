package org.symphony3.smartglue.integration.murrindindi.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AuthorityTaskOpenRequest {

    @JsonProperty("documentType")
    private int documentType;

    @JsonProperty("documentYear")
    private int documentYear;

    @JsonProperty("documentNumber")
    private int documentNumber;

    @JsonProperty("documentPart")
    private int documentPart;

    @JsonProperty("taskDetails")
    private List<TaskDetail> taskDetails;

    // Getters and Setters

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public int getDocumentYear() {
        return documentYear;
    }

    public void setDocumentYear(int documentYear) {
        this.documentYear = documentYear;
    }

    public int getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(int documentNumber) {
        this.documentNumber = documentNumber;
    }

    public int getDocumentPart() {
        return documentPart;
    }

    public void setDocumentPart(int documentPart) {
        this.documentPart = documentPart;
    }

    public List<TaskDetail> getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(List<TaskDetail> taskDetails) {
        this.taskDetails = taskDetails;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class TaskDetail {

        @JsonProperty("taskReasonCode")
        private String taskReasonCode;

        @JsonProperty("comment")
        private String comment;

        // Getters and Setters

        public String getTaskReasonCode() {
            return taskReasonCode;
        }

        public void setTaskReasonCode(String taskReasonCode) {
            this.taskReasonCode = taskReasonCode;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}

