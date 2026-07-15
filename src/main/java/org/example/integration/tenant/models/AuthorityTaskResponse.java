package org.example.integration.tenant.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityTaskResponse {

    @JsonProperty("result")
    private List<Result> result;

    @JsonProperty("resultCount")
    private int resultCount;

    @JsonProperty("totalCount")
    private int totalCount;

    @JsonProperty("status")
    private String status;

    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("accessResult")
    private List<AccessResult> accessResult;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<AccessResult> getAccessResult() {
        return accessResult;
    }

    public void setAccessResult(List<AccessResult> accessResult) {
        this.accessResult = accessResult;
    }

    public static class Result {
        @JsonProperty("id")
        private int id;
        @JsonProperty("moduleReference")
        private String moduleReference;
        @JsonProperty("formattedAccount")
        private String formattedAccount;
        @JsonProperty("workflowCode")
        private String workflowCode;
        @JsonProperty("workflowSequence")
        private int workflowSequence;
        @JsonProperty("moduleAccount")
        private int moduleAccount;
        @JsonProperty("documentType")
        private int documentType;
        @JsonProperty("documentYear")
        private int documentYear;
        @JsonProperty("documentNumber")
        private int documentNumber;
        @JsonProperty("documentPart")
        private int documentPart;
        @JsonProperty("workflowActionCode")
        private String workflowActionCode;
        @JsonProperty("workflowId")
        private int workflowId;
        @JsonProperty("workflowParentAction")
        private String workflowParentAction;
        @JsonProperty("workflowParentId")
        private int workflowParentId;
        @JsonProperty("answerFlag")
        private String answerFlag;
        @JsonProperty("openDate")
        private String openDate;
        @JsonProperty("openFlag")
        private int openFlag;
        @JsonProperty("numberDays")
        private int numberDays;
        @JsonProperty("numberHours")
        private int numberHours;
        @JsonProperty("numberMins")
        private int numberMins;
        @JsonProperty("dueDate")
        private String dueDate;
        @JsonProperty("delegatingOfficerInitials")
        private String delegatingOfficerInitials;
        @JsonProperty("delegatingNameId")
        private int delegatingNameId;
        @JsonProperty("delegatingRoleId")
        private int delegatingRoleId;
        @JsonProperty("actioningOfficerInitials")
        private String actioningOfficerInitials;
        @JsonProperty("actioningNameId")
        private int actioningNameId;
        @JsonProperty("actioningRoleId")
        private int actioningRoleId;
        @JsonProperty("stopTheClockFlag")
        private String stopTheClockFlag;
        @JsonProperty("stopTheClockCode")
        private String stopTheClockCode;
        @JsonProperty("inspectionDateTime")
        private String inspectionDateTime;
        @JsonProperty("inspectionOfficerInit")
        private String inspectionOfficerInit;
        @JsonProperty("inspectionNameId")
        private int inspectionNameId;
        @JsonProperty("inspectionRoleId")
        private int inspectionRoleId;
        @JsonProperty("determinationCode")
        private String determinationCode;
        @JsonProperty("determinationDate")
        private String determinationDate;
        @JsonProperty("stopTheClockUpdateFlag")
        private String stopTheClockUpdateFlag;
        @JsonProperty("comment")
        private String comment;
        @JsonProperty("stopTheClockStartDate")
        private String stopTheClockStartDate;
        @JsonProperty("stopTheClockEndDate")
        private String stopTheClockEndDate;
        @JsonProperty("newWorkflow")
        private String newWorkflow;
        @JsonProperty("reminderDate")
        private String reminderDate;
        @JsonProperty("customerContactMethod")
        private String customerContactMethod;
        @JsonProperty("createdDateTime")
        private String createdDateTime;
        @JsonProperty("modifiedDateTime")
        private String modifiedDateTime;
        @JsonProperty("modifiedOperatorId")
        private int modifiedOperatorId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getWorkflowCode() {
            return workflowCode;
        }

        public void setWorkflowCode(String workflowCode) {
            this.workflowCode = workflowCode;
        }

        public int getWorkflowSequence() {
            return workflowSequence;
        }

        public void setWorkflowSequence(int workflowSequence) {
            this.workflowSequence = workflowSequence;
        }

        public int getModuleAccount() {
            return moduleAccount;
        }

        public void setModuleAccount(int moduleAccount) {
            this.moduleAccount = moduleAccount;
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

        public String getWorkflowActionCode() {
            return workflowActionCode;
        }

        public void setWorkflowActionCode(String workflowActionCode) {
            this.workflowActionCode = workflowActionCode;
        }

        public int getWorkflowId() {
            return workflowId;
        }

        public void setWorkflowId(int workflowId) {
            this.workflowId = workflowId;
        }

        public String getWorkflowParentAction() {
            return workflowParentAction;
        }

        public void setWorkflowParentAction(String workflowParentAction) {
            this.workflowParentAction = workflowParentAction;
        }

        public int getWorkflowParentId() {
            return workflowParentId;
        }

        public void setWorkflowParentId(int workflowParentId) {
            this.workflowParentId = workflowParentId;
        }

        public String getAnswerFlag() {
            return answerFlag;
        }

        public void setAnswerFlag(String answerFlag) {
            this.answerFlag = answerFlag;
        }

        public String getOpenDate() {
            return openDate;
        }

        public void setOpenDate(String openDate) {
            this.openDate = openDate;
        }

        public int getOpenFlag() {
            return openFlag;
        }

        public void setOpenFlag(int openFlag) {
            this.openFlag = openFlag;
        }

        public int getNumberDays() {
            return numberDays;
        }

        public void setNumberDays(int numberDays) {
            this.numberDays = numberDays;
        }

        public int getNumberHours() {
            return numberHours;
        }

        public void setNumberHours(int numberHours) {
            this.numberHours = numberHours;
        }

        public int getNumberMins() {
            return numberMins;
        }

        public void setNumberMins(int numberMins) {
            this.numberMins = numberMins;
        }

        public String getDueDate() {
            return dueDate;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

        public String getDelegatingOfficerInitials() {
            return delegatingOfficerInitials;
        }

        public void setDelegatingOfficerInitials(String delegatingOfficerInitials) {
            this.delegatingOfficerInitials = delegatingOfficerInitials;
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

        public String getActioningOfficerInitials() {
            return actioningOfficerInitials;
        }

        public void setActioningOfficerInitials(String actioningOfficerInitials) {
            this.actioningOfficerInitials = actioningOfficerInitials;
        }

        public int getActioningNameId() {
            return actioningNameId;
        }

        public void setActioningNameId(int actioningNameId) {
            this.actioningNameId = actioningNameId;
        }

        public int getActioningRoleId() {
            return actioningRoleId;
        }

        public void setActioningRoleId(int actioningRoleId) {
            this.actioningRoleId = actioningRoleId;
        }

        public String getStopTheClockFlag() {
            return stopTheClockFlag;
        }

        public void setStopTheClockFlag(String stopTheClockFlag) {
            this.stopTheClockFlag = stopTheClockFlag;
        }

        public String getStopTheClockCode() {
            return stopTheClockCode;
        }

        public void setStopTheClockCode(String stopTheClockCode) {
            this.stopTheClockCode = stopTheClockCode;
        }

        public String getInspectionDateTime() {
            return inspectionDateTime;
        }

        public void setInspectionDateTime(String inspectionDateTime) {
            this.inspectionDateTime = inspectionDateTime;
        }

        public String getInspectionOfficerInit() {
            return inspectionOfficerInit;
        }

        public void setInspectionOfficerInit(String inspectionOfficerInit) {
            this.inspectionOfficerInit = inspectionOfficerInit;
        }

        public int getInspectionNameId() {
            return inspectionNameId;
        }

        public void setInspectionNameId(int inspectionNameId) {
            this.inspectionNameId = inspectionNameId;
        }

        public int getInspectionRoleId() {
            return inspectionRoleId;
        }

        public void setInspectionRoleId(int inspectionRoleId) {
            this.inspectionRoleId = inspectionRoleId;
        }

        public String getDeterminationCode() {
            return determinationCode;
        }

        public void setDeterminationCode(String determinationCode) {
            this.determinationCode = determinationCode;
        }

        public String getDeterminationDate() {
            return determinationDate;
        }

        public void setDeterminationDate(String determinationDate) {
            this.determinationDate = determinationDate;
        }

        public String getStopTheClockUpdateFlag() {
            return stopTheClockUpdateFlag;
        }

        public void setStopTheClockUpdateFlag(String stopTheClockUpdateFlag) {
            this.stopTheClockUpdateFlag = stopTheClockUpdateFlag;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getStopTheClockStartDate() {
            return stopTheClockStartDate;
        }

        public void setStopTheClockStartDate(String stopTheClockStartDate) {
            this.stopTheClockStartDate = stopTheClockStartDate;
        }

        public String getStopTheClockEndDate() {
            return stopTheClockEndDate;
        }

        public void setStopTheClockEndDate(String stopTheClockEndDate) {
            this.stopTheClockEndDate = stopTheClockEndDate;
        }

        public String getNewWorkflow() {
            return newWorkflow;
        }

        public void setNewWorkflow(String newWorkflow) {
            this.newWorkflow = newWorkflow;
        }

        public String getReminderDate() {
            return reminderDate;
        }

        public void setReminderDate(String reminderDate) {
            this.reminderDate = reminderDate;
        }

        public String getCustomerContactMethod() {
            return customerContactMethod;
        }

        public void setCustomerContactMethod(String customerContactMethod) {
            this.customerContactMethod = customerContactMethod;
        }

        public String getCreatedDateTime() {
            return createdDateTime;
        }

        public void setCreatedDateTime(String createdDateTime) {
            this.createdDateTime = createdDateTime;
        }

        public String getModifiedDateTime() {
            return modifiedDateTime;
        }

        public void setModifiedDateTime(String modifiedDateTime) {
            this.modifiedDateTime = modifiedDateTime;
        }

        public int getModifiedOperatorId() {
            return modifiedOperatorId;
        }

        public void setModifiedOperatorId(int modifiedOperatorId) {
            this.modifiedOperatorId = modifiedOperatorId;
        }
    }

    public static class AccessResult {
        @JsonProperty("actionName")
        private String actionName;

        @JsonProperty("resultList")
        private List<ResultListItem> resultList;

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public List<ResultListItem> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListItem> resultList) {
            this.resultList = resultList;
        }
    }

    public static class ResultListItem {
        @JsonProperty("metadataName")
        private String metadataName;

        @JsonProperty("hasAccess")
        private boolean hasAccess;

        @JsonProperty("actionName")
        private String actionName;

        @JsonProperty("reason")
        private String reason;

        @JsonProperty("disabledContent")
        private Map<String, String> disabledContent;

        public String getMetadataName() {
            return metadataName;
        }

        public void setMetadataName(String metadataName) {
            this.metadataName = metadataName;
        }

        public boolean isHasAccess() {
            return hasAccess;
        }

        public void setHasAccess(boolean hasAccess) {
            this.hasAccess = hasAccess;
        }

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public Map<String, String> getDisabledContent() {
            return disabledContent;
        }

        public void setDisabledContent(Map<String, String> disabledContent) {
            this.disabledContent = disabledContent;
        }
    }
}

