package org.symphony3.smartglue.integration.murrindindi.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityCrmResponse {

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

    public List<Object> getAccessResult() {
        return accessResult;
    }

    public void setAccessResult(List<Object> accessResult) {
        this.accessResult = accessResult;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ResultItem {

        @JsonProperty("id")
        private int id;

        @JsonProperty("documentType")
        private int documentType;

        @JsonProperty("documentYear")
        private int documentYear;

        @JsonProperty("documentNumber")
        private int documentNumber;

        @JsonProperty("documentPart")
        private int documentPart;

        @JsonProperty("formattedAccount")
        private String formattedAccount;

        @JsonProperty("workflowCode")
        private String workflowCode;

        @JsonProperty("folioNumber")
        private String folioNumber;

        @JsonProperty("externalReference")
        private String externalReference;

        @JsonProperty("conveyanceReference")
        private String conveyanceReference;

        @JsonProperty("documentPrecis")
        private String documentPrecis;

        @JsonProperty("writtenDatetime")
        private String writtenDatetime;

        @JsonProperty("receivedDatetime")
        private String receivedDatetime;

        @JsonProperty("dueNumberDays")
        private int dueNumberDays;

        @JsonProperty("dueNumberHours")
        private Integer dueNumberHours;

        @JsonProperty("dueNumberMins")
        private Integer dueNumberMins;

        @JsonProperty("dueDate")
        private String dueDate;

        @JsonProperty("determinationDatetime")
        private String determinationDatetime;

        @JsonProperty("determationNameId")
        private String determationNameId;

        @JsonProperty("determinationCode")
        private String determinationCode;

        @JsonProperty("changedDatetime")
        private String changedDatetime;

        @JsonProperty("actioningNameId")
        private int actioningNameId;

        @JsonProperty("actioningInitials")
        private String actioningInitials;

        @JsonProperty("actioningRoleId")
        private int actioningRoleId;

        @JsonProperty("delegatingInitials")
        private String delegatingInitials;

        @JsonProperty("delegatingNameId")
        private int delegatingNameId;

        @JsonProperty("delegatingRoleId")
        private int delegatingRoleId;

        @JsonProperty("courtDeterminationCode")
        private String courtDeterminationCode;

        @JsonProperty("courtDeterminationDatetime")
        private String courtDeterminationDatetime;

        @JsonProperty("interimDatetime")
        private String interimDatetime;

        @JsonProperty("contactMethodCode")
        private String contactMethodCode;

        @JsonProperty("categoryId")
        private int categoryId;

        @JsonProperty("createdDatetime")
        private String createdDatetime;

        @JsonProperty("modifiedDatetime")
        private String modifiedDatetime;

        @JsonProperty("modifiedOperatorId")
        private int modifiedOperatorId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public String getFormattedAccount() {
            return formattedAccount;
        }

        public void setFormattedAccount(String formattedAccount) {
            this.formattedAccount = formattedAccount;
        }

        public String getWorkflowCode() {
            return workflowCode;
        }

        public void setWorkflowCode(String workflowCode) {
            this.workflowCode = workflowCode;
        }

        public String getFolioNumber() {
            return folioNumber;
        }

        public void setFolioNumber(String folioNumber) {
            this.folioNumber = folioNumber;
        }

        public String getExternalReference() {
            return externalReference;
        }

        public void setExternalReference(String externalReference) {
            this.externalReference = externalReference;
        }

        public String getConveyanceReference() {
            return conveyanceReference;
        }

        public void setConveyanceReference(String conveyanceReference) {
            this.conveyanceReference = conveyanceReference;
        }

        public String getDocumentPrecis() {
            return documentPrecis;
        }

        public void setDocumentPrecis(String documentPrecis) {
            this.documentPrecis = documentPrecis;
        }

        public String getWrittenDatetime() {
            return writtenDatetime;
        }

        public void setWrittenDatetime(String writtenDatetime) {
            this.writtenDatetime = writtenDatetime;
        }

        public String getReceivedDatetime() {
            return receivedDatetime;
        }

        public void setReceivedDatetime(String receivedDatetime) {
            this.receivedDatetime = receivedDatetime;
        }

        public int getDueNumberDays() {
            return dueNumberDays;
        }

        public void setDueNumberDays(int dueNumberDays) {
            this.dueNumberDays = dueNumberDays;
        }

        public Integer getDueNumberHours() {
            return dueNumberHours;
        }

        public void setDueNumberHours(Integer dueNumberHours) {
            this.dueNumberHours = dueNumberHours;
        }

        public Integer getDueNumberMins() {
            return dueNumberMins;
        }

        public void setDueNumberMins(Integer dueNumberMins) {
            this.dueNumberMins = dueNumberMins;
        }

        public String getDueDate() {
            return dueDate;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

        public String getDeterminationDatetime() {
            return determinationDatetime;
        }

        public void setDeterminationDatetime(String determinationDatetime) {
            this.determinationDatetime = determinationDatetime;
        }

        public String getDetermationNameId() {
            return determationNameId;
        }

        public void setDetermationNameId(String determationNameId) {
            this.determationNameId = determationNameId;
        }

        public String getDeterminationCode() {
            return determinationCode;
        }

        public void setDeterminationCode(String determinationCode) {
            this.determinationCode = determinationCode;
        }

        public String getChangedDatetime() {
            return changedDatetime;
        }

        public void setChangedDatetime(String changedDatetime) {
            this.changedDatetime = changedDatetime;
        }

        public int getActioningNameId() {
            return actioningNameId;
        }

        public void setActioningNameId(int actioningNameId) {
            this.actioningNameId = actioningNameId;
        }

        public String getActioningInitials() {
            return actioningInitials;
        }

        public void setActioningInitials(String actioningInitials) {
            this.actioningInitials = actioningInitials;
        }

        public int getActioningRoleId() {
            return actioningRoleId;
        }

        public void setActioningRoleId(int actioningRoleId) {
            this.actioningRoleId = actioningRoleId;
        }

        public String getDelegatingInitials() {
            return delegatingInitials;
        }

        public void setDelegatingInitials(String delegatingInitials) {
            this.delegatingInitials = delegatingInitials;
        }

        public int getDelegatingNameId() {
            return delegatingNameId;
        }

        public void setDelegatingNameId(int delegatingNameId) {
            this.delegatingNameId = delegatingNameId;
        }

        public int getDelegatingRoleId() {
            return delegatingRoleId;
        }

        public void setDelegatingRoleId(int delegatingRoleId) {
            this.delegatingRoleId = delegatingRoleId;
        }

        public String getCourtDeterminationCode() {
            return courtDeterminationCode;
        }

        public void setCourtDeterminationCode(String courtDeterminationCode) {
            this.courtDeterminationCode = courtDeterminationCode;
        }

        public String getCourtDeterminationDatetime() {
            return courtDeterminationDatetime;
        }

        public void setCourtDeterminationDatetime(String courtDeterminationDatetime) {
            this.courtDeterminationDatetime = courtDeterminationDatetime;
        }

        public String getInterimDatetime() {
            return interimDatetime;
        }

        public void setInterimDatetime(String interimDatetime) {
            this.interimDatetime = interimDatetime;
        }

        public String getContactMethodCode() {
            return contactMethodCode;
        }

        public void setContactMethodCode(String contactMethodCode) {
            this.contactMethodCode = contactMethodCode;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCreatedDatetime() {
            return createdDatetime;
        }

        public void setCreatedDatetime(String createdDatetime) {
            this.createdDatetime = createdDatetime;
        }

        public String getModifiedDatetime() {
            return modifiedDatetime;
        }

        public void setModifiedDatetime(String modifiedDatetime) {
            this.modifiedDatetime = modifiedDatetime;
        }

        public int getModifiedOperatorId() {
            return modifiedOperatorId;
        }

        public void setModifiedOperatorId(int modifiedOperatorId) {
            this.modifiedOperatorId = modifiedOperatorId;
        }
    }
}
