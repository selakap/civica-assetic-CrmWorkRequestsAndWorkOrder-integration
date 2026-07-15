package org.example.integration.tenant.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsseticWorkOrderByExternalIdResponse {

    @JsonProperty("TotalResults")
    private int totalResults;

    @JsonProperty("TotalPages")
    private int totalPages;

    @JsonProperty("Page")
    private int page;

    @JsonProperty("ResourceList")
    private List<Resource> resourceList;

    @JsonProperty("_links")
    private List<Object> links;

    @JsonProperty("_embedded")
    private Object embedded;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public List<Object> getLinks() {
        return links;
    }

    public void setLinks(List<Object> links) {
        this.links = links;
    }

    public Object getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Object embedded) {
        this.embedded = embedded;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Resource {
        @JsonProperty("Id")
        private String id;

        @JsonProperty("FriendlyIdStr")
        private String friendlyIdStr;

        @JsonProperty("BriefDescription")
        private String briefDescription;

        @JsonProperty("ExternalId")
        private String externalId;

        @JsonProperty("ReferenceNumber")
        private String referenceNumber;

        @JsonProperty("WorkOrderSourceId")
        private Object workOrderSourceId;

        @JsonProperty("ReferenceWorkOrderId")
        private Object referenceWorkOrderId;

        @JsonProperty("LossOfServiceRequired")
        private boolean lossOfServiceRequired;

        @JsonProperty("CreatedDateTime")
        private String createdDateTime;

        @JsonProperty("LastModified")
        private String lastModified;

        @JsonProperty("CreatorId")
        private String creatorId;

        @JsonProperty("RequestorId")
        private String requestorId;

        @JsonProperty("ResponsibleOfficerId")
        private Object responsibleOfficerId;

        @JsonProperty("WorkOrderWorkGroup")
        private String workOrderWorkGroup;

        @JsonProperty("RequestedBy")
        private Person requestedBy;

        @JsonProperty("CreatedBy")
        private Person createdBy;

        @JsonProperty("ResponsibleOfficer")
        private Object responsibleOfficer;

        @JsonProperty("Status")
        private String status;

        @JsonProperty("AssetId")
        private String assetId;

        @JsonProperty("PriorityId")
        private int priorityId;

        @JsonProperty("Priority")
        private String priority;

        @JsonProperty("WorkTasks")
        private List<Object> workTasks;

        @JsonProperty("LocationDescription")
        private String locationDescription;

        @JsonProperty("PointString")
        private String pointString;

        @JsonProperty("EstimatedDuration")
        private Object estimatedDuration;

        @JsonProperty("Asset")
        private Asset asset;

        @JsonProperty("SupportingInformation")
        private List<Object> supportingInformation;

        @JsonProperty("WRSupportingInformation")
        private List<Object> wrSupportingInformation;

        @JsonProperty("MaintenanceMaterials")
        private List<Object> maintenanceMaterials;

        @JsonProperty("MaintenanceServices")
        private List<Object> maintenanceServices;

        @JsonProperty("WorkOrderType")
        private WorkOrderType workOrderType;

        @JsonProperty("FailureSubCodeId")
        private Object failureSubCodeId;

        @JsonProperty("CauseSubCodeId")
        private Object causeSubCodeId;

        @JsonProperty("RemedyCodeId")
        private Object remedyCodeId;

        @JsonProperty("PreventiveMaintenanceId")
        private Object preventiveMaintenanceId;

        @JsonProperty("Scheduling")
        private Scheduling scheduling;

        @JsonProperty("Labours")
        private List<Object> labours;

        @JsonProperty("MeterReadingRequired")
        private boolean meterReadingRequired;

        @JsonProperty("LinkedWorkRequests")
        private List<LinkedWorkRequest> linkedWorkRequests;

        @JsonProperty("_links")
        private List<Object> links;

        @JsonProperty("_embedded")
        private Object embedded;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFriendlyIdStr() {
            return friendlyIdStr;
        }

        public void setFriendlyIdStr(String friendlyIdStr) {
            this.friendlyIdStr = friendlyIdStr;
        }

        public String getBriefDescription() {
            return briefDescription;
        }

        public void setBriefDescription(String briefDescription) {
            this.briefDescription = briefDescription;
        }

        public String getExternalId() {
            return externalId;
        }

        public void setExternalId(String externalId) {
            this.externalId = externalId;
        }

        public String getReferenceNumber() {
            return referenceNumber;
        }

        public void setReferenceNumber(String referenceNumber) {
            this.referenceNumber = referenceNumber;
        }

        public Object getWorkOrderSourceId() {
            return workOrderSourceId;
        }

        public void setWorkOrderSourceId(Object workOrderSourceId) {
            this.workOrderSourceId = workOrderSourceId;
        }

        public Object getReferenceWorkOrderId() {
            return referenceWorkOrderId;
        }

        public void setReferenceWorkOrderId(Object referenceWorkOrderId) {
            this.referenceWorkOrderId = referenceWorkOrderId;
        }

        public boolean isLossOfServiceRequired() {
            return lossOfServiceRequired;
        }

        public void setLossOfServiceRequired(boolean lossOfServiceRequired) {
            this.lossOfServiceRequired = lossOfServiceRequired;
        }

        public String getCreatedDateTime() {
            return createdDateTime;
        }

        public void setCreatedDateTime(String createdDateTime) {
            this.createdDateTime = createdDateTime;
        }

        public String getLastModified() {
            return lastModified;
        }

        public void setLastModified(String lastModified) {
            this.lastModified = lastModified;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
        }

        public String getRequestorId() {
            return requestorId;
        }

        public void setRequestorId(String requestorId) {
            this.requestorId = requestorId;
        }

        public Object getResponsibleOfficerId() {
            return responsibleOfficerId;
        }

        public void setResponsibleOfficerId(Object responsibleOfficerId) {
            this.responsibleOfficerId = responsibleOfficerId;
        }

        public String getWorkOrderWorkGroup() {
            return workOrderWorkGroup;
        }

        public void setWorkOrderWorkGroup(String workOrderWorkGroup) {
            this.workOrderWorkGroup = workOrderWorkGroup;
        }

        public Person getRequestedBy() {
            return requestedBy;
        }

        public void setRequestedBy(Person requestedBy) {
            this.requestedBy = requestedBy;
        }

        public Person getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Person createdBy) {
            this.createdBy = createdBy;
        }

        public Object getResponsibleOfficer() {
            return responsibleOfficer;
        }

        public void setResponsibleOfficer(Object responsibleOfficer) {
            this.responsibleOfficer = responsibleOfficer;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAssetId() {
            return assetId;
        }

        public void setAssetId(String assetId) {
            this.assetId = assetId;
        }

        public int getPriorityId() {
            return priorityId;
        }

        public void setPriorityId(int priorityId) {
            this.priorityId = priorityId;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public List<Object> getWorkTasks() {
            return workTasks;
        }

        public void setWorkTasks(List<Object> workTasks) {
            this.workTasks = workTasks;
        }

        public String getLocationDescription() {
            return locationDescription;
        }

        public void setLocationDescription(String locationDescription) {
            this.locationDescription = locationDescription;
        }

        public String getPointString() {
            return pointString;
        }

        public void setPointString(String pointString) {
            this.pointString = pointString;
        }

        public Object getEstimatedDuration() {
            return estimatedDuration;
        }

        public void setEstimatedDuration(Object estimatedDuration) {
            this.estimatedDuration = estimatedDuration;
        }

        public Asset getAsset() {
            return asset;
        }

        public void setAsset(Asset asset) {
            this.asset = asset;
        }

        public List<Object> getSupportingInformation() {
            return supportingInformation;
        }

        public void setSupportingInformation(List<Object> supportingInformation) {
            this.supportingInformation = supportingInformation;
        }

        public List<Object> getWrSupportingInformation() {
            return wrSupportingInformation;
        }

        public void setWrSupportingInformation(List<Object> wrSupportingInformation) {
            this.wrSupportingInformation = wrSupportingInformation;
        }

        public List<Object> getMaintenanceMaterials() {
            return maintenanceMaterials;
        }

        public void setMaintenanceMaterials(List<Object> maintenanceMaterials) {
            this.maintenanceMaterials = maintenanceMaterials;
        }

        public List<Object> getMaintenanceServices() {
            return maintenanceServices;
        }

        public void setMaintenanceServices(List<Object> maintenanceServices) {
            this.maintenanceServices = maintenanceServices;
        }

        public WorkOrderType getWorkOrderType() {
            return workOrderType;
        }

        public void setWorkOrderType(WorkOrderType workOrderType) {
            this.workOrderType = workOrderType;
        }

        public Object getFailureSubCodeId() {
            return failureSubCodeId;
        }

        public void setFailureSubCodeId(Object failureSubCodeId) {
            this.failureSubCodeId = failureSubCodeId;
        }

        public Object getCauseSubCodeId() {
            return causeSubCodeId;
        }

        public void setCauseSubCodeId(Object causeSubCodeId) {
            this.causeSubCodeId = causeSubCodeId;
        }

        public Object getRemedyCodeId() {
            return remedyCodeId;
        }

        public void setRemedyCodeId(Object remedyCodeId) {
            this.remedyCodeId = remedyCodeId;
        }

        public Object getPreventiveMaintenanceId() {
            return preventiveMaintenanceId;
        }

        public void setPreventiveMaintenanceId(Object preventiveMaintenanceId) {
            this.preventiveMaintenanceId = preventiveMaintenanceId;
        }

        public Scheduling getScheduling() {
            return scheduling;
        }

        public void setScheduling(Scheduling scheduling) {
            this.scheduling = scheduling;
        }

        public List<Object> getLabours() {
            return labours;
        }

        public void setLabours(List<Object> labours) {
            this.labours = labours;
        }

        public boolean isMeterReadingRequired() {
            return meterReadingRequired;
        }

        public void setMeterReadingRequired(boolean meterReadingRequired) {
            this.meterReadingRequired = meterReadingRequired;
        }

        public List<LinkedWorkRequest> getLinkedWorkRequests() {
            return linkedWorkRequests;
        }

        public void setLinkedWorkRequests(List<LinkedWorkRequest> linkedWorkRequests) {
            this.linkedWorkRequests = linkedWorkRequests;
        }

        public List<Object> getLinks() {
            return links;
        }

        public void setLinks(List<Object> links) {
            this.links = links;
        }

        public Object getEmbedded() {
            return embedded;
        }

        public void setEmbedded(Object embedded) {
            this.embedded = embedded;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Person {
        @JsonProperty("Id")
        private String id;

        @JsonProperty("DisplayName")
        private String displayName;

        @JsonProperty("_links")
        private List<Object> links;

        @JsonProperty("_embedded")
        private Object embedded;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public List<Object> getLinks() {
            return links;
        }

        public void setLinks(List<Object> links) {
            this.links = links;
        }

        public Object getEmbedded() {
            return embedded;
        }

        public void setEmbedded(Object embedded) {
            this.embedded = embedded;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Asset {
        @JsonProperty("Id")
        private String id;

        @JsonProperty("AssetId")
        private String assetId;

        @JsonProperty("AssetName")
        private String assetName;

        @JsonProperty("WorkGroupId")
        private int workGroupId;

        @JsonProperty("AssetWorkGroup")
        private String assetWorkGroup;

        @JsonProperty("CriticalityId")
        private String criticalityId;

        @JsonProperty("AssetCriticality")
        private String assetCriticality;

        @JsonProperty("AssetCategoryId")
        private String assetCategoryId;

        @JsonProperty("AssetCategory")
        private String assetCategory;

        @JsonProperty("AssetMaintenanceType")
        private String assetMaintenanceType;

        @JsonProperty("_links")
        private List<Object> links;

        @JsonProperty("_embedded")
        private Object embedded;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAssetId() {
            return assetId;
        }

        public void setAssetId(String assetId) {
            this.assetId = assetId;
        }

        public String getAssetName() {
            return assetName;
        }

        public void setAssetName(String assetName) {
            this.assetName = assetName;
        }

        public int getWorkGroupId() {
            return workGroupId;
        }

        public void setWorkGroupId(int workGroupId) {
            this.workGroupId = workGroupId;
        }

        public String getAssetWorkGroup() {
            return assetWorkGroup;
        }

        public void setAssetWorkGroup(String assetWorkGroup) {
            this.assetWorkGroup = assetWorkGroup;
        }

        public String getCriticalityId() {
            return criticalityId;
        }

        public void setCriticalityId(String criticalityId) {
            this.criticalityId = criticalityId;
        }

        public String getAssetCriticality() {
            return assetCriticality;
        }

        public void setAssetCriticality(String assetCriticality) {
            this.assetCriticality = assetCriticality;
        }

        public String getAssetCategoryId() {
            return assetCategoryId;
        }

        public void setAssetCategoryId(String assetCategoryId) {
            this.assetCategoryId = assetCategoryId;
        }

        public String getAssetCategory() {
            return assetCategory;
        }

        public void setAssetCategory(String assetCategory) {
            this.assetCategory = assetCategory;
        }

        public String getAssetMaintenanceType() {
            return assetMaintenanceType;
        }

        public void setAssetMaintenanceType(String assetMaintenanceType) {
            this.assetMaintenanceType = assetMaintenanceType;
        }

        public List<Object> getLinks() {
            return links;
        }

        public void setLinks(List<Object> links) {
            this.links = links;
        }

        public Object getEmbedded() {
            return embedded;
        }

        public void setEmbedded(Object embedded) {
            this.embedded = embedded;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WorkOrderType {
        @JsonProperty("Id")
        private int id;

        @JsonProperty("Name")
        private String name;

        @JsonProperty("Code")
        private String code;

        @JsonProperty("BudgetType")
        private String budgetType;

        @JsonProperty("Description")
        private String description;

        @JsonProperty("IsFailureRequired")
        private boolean isFailureRequired;

        @JsonProperty("IsCauseRequired")
        private boolean isCauseRequired;

        @JsonProperty("IsRemedyRequired")
        private boolean isRemedyRequired;

        @JsonProperty("_links")
        private List<Object> links;

        @JsonProperty("_embedded")
        private Object embedded;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getBudgetType() {
            return budgetType;
        }

        public void setBudgetType(String budgetType) {
            this.budgetType = budgetType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isFailureRequired() {
            return isFailureRequired;
        }

        public void setFailureRequired(boolean failureRequired) {
            isFailureRequired = failureRequired;
        }

        public boolean isCauseRequired() {
            return isCauseRequired;
        }

        public void setCauseRequired(boolean causeRequired) {
            isCauseRequired = causeRequired;
        }

        public boolean isRemedyRequired() {
            return isRemedyRequired;
        }

        public void setRemedyRequired(boolean remedyRequired) {
            isRemedyRequired = remedyRequired;
        }

        public List<Object> getLinks() {
            return links;
        }

        public void setLinks(List<Object> links) {
            this.links = links;
        }

        public Object getEmbedded() {
            return embedded;
        }

        public void setEmbedded(Object embedded) {
            this.embedded = embedded;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Scheduling {
        @JsonProperty("DueDate")
        private String dueDate;

        @JsonProperty("TargetStart")
        private String targetStart;

        @JsonProperty("TargetFinish")
        private String targetFinish;

        @JsonProperty("ActualStart")
        private Object actualStart;

        @JsonProperty("ActualFinish")
        private Object actualFinish;

        @JsonProperty("ScheduledStart")
        private Object scheduledStart;

        @JsonProperty("ScheduledFinish")
        private Object scheduledFinish;

        @JsonProperty("_links")
        private List<Object> links;

        @JsonProperty("_embedded")
        private Object embedded;

        public String getDueDate() {
            return dueDate;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

        public String getTargetStart() {
            return targetStart;
        }

        public void setTargetStart(String targetStart) {
            this.targetStart = targetStart;
        }

        public String getTargetFinish() {
            return targetFinish;
        }

        public void setTargetFinish(String targetFinish) {
            this.targetFinish = targetFinish;
        }

        public Object getActualStart() {
            return actualStart;
        }

        public void setActualStart(Object actualStart) {
            this.actualStart = actualStart;
        }

        public Object getActualFinish() {
            return actualFinish;
        }

        public void setActualFinish(Object actualFinish) {
            this.actualFinish = actualFinish;
        }

        public Object getScheduledStart() {
            return scheduledStart;
        }

        public void setScheduledStart(Object scheduledStart) {
            this.scheduledStart = scheduledStart;
        }

        public Object getScheduledFinish() {
            return scheduledFinish;
        }

        public void setScheduledFinish(Object scheduledFinish) {
            this.scheduledFinish = scheduledFinish;
        }

        public List<Object> getLinks() {
            return links;
        }

        public void setLinks(List<Object> links) {
            this.links = links;
        }

        public Object getEmbedded() {
            return embedded;
        }

        public void setEmbedded(Object embedded) {
            this.embedded = embedded;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LinkedWorkRequest {
        @JsonProperty("FriendlyID")
        private String friendlyID;

        @JsonProperty("ID")
        private String id;

        @JsonProperty("_links")
        private List<Object> links;

        @JsonProperty("_embedded")
        private Object embedded;

        public String getFriendlyID() {
            return friendlyID;
        }

        public void setFriendlyID(String friendlyID) {
            this.friendlyID = friendlyID;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Object> getLinks() {
            return links;
        }

        public void setLinks(List<Object> links) {
            this.links = links;
        }

        public Object getEmbedded() {
            return embedded;
        }

        public void setEmbedded(Object embedded) {
            this.embedded = embedded;
        }
    }
}

