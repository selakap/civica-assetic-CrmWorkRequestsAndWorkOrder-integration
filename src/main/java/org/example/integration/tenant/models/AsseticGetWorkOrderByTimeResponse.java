package org.example.integration.tenant.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AsseticGetWorkOrderByTimeResponse {

    @JsonProperty("TotalResults")
    private int totalResults;

    @JsonProperty("TotalPages")
    private int totalPages;

    @JsonProperty("Page")
    private int page;

    @JsonProperty("ResourceList")
    private List<WorkOrder> resourceList;

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

    public List<WorkOrder> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<WorkOrder> resourceList) {
        this.resourceList = resourceList;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class WorkOrder {

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
        private String workOrderSourceId;

        @JsonProperty("ReferenceWorkOrderId")
        private String referenceWorkOrderId;

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
        private String responsibleOfficerId;

        @JsonProperty("WorkOrderWorkGroup")
        private String workOrderWorkGroup;

        @JsonProperty("RequestedBy")
        private User requestedBy;

        @JsonProperty("CreatedBy")
        private User createdBy;

        @JsonProperty("ResponsibleOfficer")
        private User responsibleOfficer;

        @JsonProperty("Status")
        private String status;

        @JsonProperty("AssetId")
        private String assetId;

        @JsonProperty("PriorityId")
        private int priorityId;

        @JsonProperty("Priority")
        private String priority;

        @JsonProperty("WorkTasks")
        private List<WorkTask> workTasks;

        @JsonProperty("LocationDescription")
        private String locationDescription;

        @JsonProperty("PointString")
        private String pointString;

        @JsonProperty("EstimatedDuration")
        private double estimatedDuration;

        @JsonProperty("Asset")
        private Asset asset;

        @JsonProperty("SupportingInformation")
        private List<SupportingInformation> supportingInformation;

        @JsonProperty("WRSupportingInformation")
        private List<WRSupportingInformation> wrSupportingInformation;

        @JsonProperty("MaintenanceMaterials")
        private List<Object> maintenanceMaterials;

        @JsonProperty("MaintenanceServices")
        private List<Object> maintenanceServices;

        @JsonProperty("WorkOrderType")
        private WorkOrderType workOrderType;

        @JsonProperty("FailureSubCodeId")
        private int failureSubCodeId;

        @JsonProperty("CauseSubCodeId")
        private int causeSubCodeId;

        @JsonProperty("RemedyCodeId")
        private int remedyCodeId;

        @JsonProperty("PreventiveMaintenanceId")
        private String preventiveMaintenanceId;

        @JsonProperty("Scheduling")
        private Scheduling scheduling;

        @JsonProperty("Labours")
        private List<Labour> labours;

        @JsonProperty("MeterReadingRequired")
        private boolean meterReadingRequired;

        @JsonProperty("LinkedWorkRequests")
        private List<LinkedWorkRequest> linkedWorkRequests;

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

        public String getWorkOrderSourceId() {
            return workOrderSourceId;
        }

        public void setWorkOrderSourceId(String workOrderSourceId) {
            this.workOrderSourceId = workOrderSourceId;
        }

        public String getReferenceWorkOrderId() {
            return referenceWorkOrderId;
        }

        public void setReferenceWorkOrderId(String referenceWorkOrderId) {
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

        public String getResponsibleOfficerId() {
            return responsibleOfficerId;
        }

        public void setResponsibleOfficerId(String responsibleOfficerId) {
            this.responsibleOfficerId = responsibleOfficerId;
        }

        public String getWorkOrderWorkGroup() {
            return workOrderWorkGroup;
        }

        public void setWorkOrderWorkGroup(String workOrderWorkGroup) {
            this.workOrderWorkGroup = workOrderWorkGroup;
        }

        public User getRequestedBy() {
            return requestedBy;
        }

        public void setRequestedBy(User requestedBy) {
            this.requestedBy = requestedBy;
        }

        public User getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(User createdBy) {
            this.createdBy = createdBy;
        }

        public User getResponsibleOfficer() {
            return responsibleOfficer;
        }

        public void setResponsibleOfficer(User responsibleOfficer) {
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

        public List<WorkTask> getWorkTasks() {
            return workTasks;
        }

        public void setWorkTasks(List<WorkTask> workTasks) {
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

        public double getEstimatedDuration() {
            return estimatedDuration;
        }

        public void setEstimatedDuration(double estimatedDuration) {
            this.estimatedDuration = estimatedDuration;
        }

        public Asset getAsset() {
            return asset;
        }

        public void setAsset(Asset asset) {
            this.asset = asset;
        }

        public List<SupportingInformation> getSupportingInformation() {
            return supportingInformation;
        }

        public void setSupportingInformation(List<SupportingInformation> supportingInformation) {
            this.supportingInformation = supportingInformation;
        }

        public List<WRSupportingInformation> getWrSupportingInformation() {
            return wrSupportingInformation;
        }

        public void setWrSupportingInformation(List<WRSupportingInformation> wrSupportingInformation) {
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

        public int getFailureSubCodeId() {
            return failureSubCodeId;
        }

        public void setFailureSubCodeId(int failureSubCodeId) {
            this.failureSubCodeId = failureSubCodeId;
        }

        public int getCauseSubCodeId() {
            return causeSubCodeId;
        }

        public void setCauseSubCodeId(int causeSubCodeId) {
            this.causeSubCodeId = causeSubCodeId;
        }

        public int getRemedyCodeId() {
            return remedyCodeId;
        }

        public void setRemedyCodeId(int remedyCodeId) {
            this.remedyCodeId = remedyCodeId;
        }

        public String getPreventiveMaintenanceId() {
            return preventiveMaintenanceId;
        }

        public void setPreventiveMaintenanceId(String preventiveMaintenanceId) {
            this.preventiveMaintenanceId = preventiveMaintenanceId;
        }

        public Scheduling getScheduling() {
            return scheduling;
        }

        public void setScheduling(Scheduling scheduling) {
            this.scheduling = scheduling;
        }

        public List<Labour> getLabours() {
            return labours;
        }

        public void setLabours(List<Labour> labours) {
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
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class User {
        @JsonProperty("Id")
        private String id;

        @JsonProperty("DisplayName")
        private String displayName;

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
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class WorkTask {
        @JsonProperty("Id")
        private String id;

        @JsonProperty("FriendlyId")
        private int friendlyId;

        @JsonProperty("BriefDescription")
        private String briefDescription;

        @JsonProperty("Status")
        private int status;

        @JsonProperty("Type")
        private String type;

        @JsonProperty("TypeId")
        private int typeId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getFriendlyId() {
            return friendlyId;
        }

        public void setFriendlyId(int friendlyId) {
            this.friendlyId = friendlyId;
        }

        public String getBriefDescription() {
            return briefDescription;
        }

        public void setBriefDescription(String briefDescription) {
            this.briefDescription = briefDescription;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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

        @JsonProperty("AssetCategoryId")
        private String assetCategoryId;

        @JsonProperty("AssetCategory")
        private String assetCategory;

        @JsonProperty("AssetMaintenanceType")
        private String assetMaintenanceType;

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
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class WRSupportingInformation {
        @JsonProperty("Id")
        private String id;

        @JsonProperty("Description")
        private String description;

        @JsonProperty("CreatedBy")
        private String createdBy;

        @JsonProperty("CreatedByDisplayName")
        private String createdByDisplayName;

        @JsonProperty("CreatedDateTime")
        private String createdDateTime;

        @JsonProperty("SystemAddedTime")
        private String systemAddedTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedByDisplayName() {
            return createdByDisplayName;
        }

        public void setCreatedByDisplayName(String createdByDisplayName) {
            this.createdByDisplayName = createdByDisplayName;
        }

        public String getCreatedDateTime() {
            return createdDateTime;
        }

        public void setCreatedDateTime(String createdDateTime) {
            this.createdDateTime = createdDateTime;
        }

        public String getSystemAddedTime() {
            return systemAddedTime;
        }

        public void setSystemAddedTime(String systemAddedTime) {
            this.systemAddedTime = systemAddedTime;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class SupportingInformation {
        @JsonProperty("Id")
        private String id;

        @JsonProperty("Description")
        private String description;

        @JsonProperty("CreatedBy")
        private String createdBy;

        @JsonProperty("CreatedByDisplayName")
        private String createdByDisplayName;

        @JsonProperty("CreatedDateTime")
        private String createdDateTime;

        @JsonProperty("SystemAddedTime")
        private String systemAddedTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedByDisplayName() {
            return createdByDisplayName;
        }

        public void setCreatedByDisplayName(String createdByDisplayName) {
            this.createdByDisplayName = createdByDisplayName;
        }

        public String getCreatedDateTime() {
            return createdDateTime;
        }

        public void setCreatedDateTime(String createdDateTime) {
            this.createdDateTime = createdDateTime;
        }

        public String getSystemAddedTime() {
            return systemAddedTime;
        }

        public void setSystemAddedTime(String systemAddedTime) {
            this.systemAddedTime = systemAddedTime;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class Scheduling {
        @JsonProperty("DueDate")
        private String dueDate;

        @JsonProperty("TargetStart")
        private String targetStart;

        @JsonProperty("TargetFinish")
        private String targetFinish;

        @JsonProperty("ActualStart")
        private String actualStart;

        @JsonProperty("ActualFinish")
        private String actualFinish;

        @JsonProperty("ScheduledStart")
        private String scheduledStart;

        @JsonProperty("ScheduledFinish")
        private String scheduledFinish;

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

        public String getActualStart() {
            return actualStart;
        }

        public void setActualStart(String actualStart) {
            this.actualStart = actualStart;
        }

        public String getActualFinish() {
            return actualFinish;
        }

        public void setActualFinish(String actualFinish) {
            this.actualFinish = actualFinish;
        }

        public String getScheduledStart() {
            return scheduledStart;
        }

        public void setScheduledStart(String scheduledStart) {
            this.scheduledStart = scheduledStart;
        }

        public String getScheduledFinish() {
            return scheduledFinish;
        }

        public void setScheduledFinish(String scheduledFinish) {
            this.scheduledFinish = scheduledFinish;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class Labour {
        @JsonProperty("Id")
        private String id;

        @JsonProperty("LinkedWorkTaskId")
        private String linkedWorkTaskId;

        @JsonProperty("PlannedGroupCraftId")
        private String plannedGroupCraftId;

        @JsonProperty("QuantityRequired")
        private double quantityRequired;

        @JsonProperty("HoursRequired")
        private double hoursRequired;

        @JsonProperty("PlannedCosts")
        private double plannedCosts;

        @JsonProperty("ActualCosts")
        private double actualCosts;

        @JsonProperty("Craft")
        private String craft;

        @JsonProperty("WorkGroup")
        private String workGroup;

        @JsonProperty("MaintenanceResources")
        private List<MaintenanceResource> maintenanceResources;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLinkedWorkTaskId() {
            return linkedWorkTaskId;
        }

        public void setLinkedWorkTaskId(String linkedWorkTaskId) {
            this.linkedWorkTaskId = linkedWorkTaskId;
        }

        public String getPlannedGroupCraftId() {
            return plannedGroupCraftId;
        }

        public void setPlannedGroupCraftId(String plannedGroupCraftId) {
            this.plannedGroupCraftId = plannedGroupCraftId;
        }

        public double getQuantityRequired() {
            return quantityRequired;
        }

        public void setQuantityRequired(double quantityRequired) {
            this.quantityRequired = quantityRequired;
        }

        public double getHoursRequired() {
            return hoursRequired;
        }

        public void setHoursRequired(double hoursRequired) {
            this.hoursRequired = hoursRequired;
        }

        public double getPlannedCosts() {
            return plannedCosts;
        }

        public void setPlannedCosts(double plannedCosts) {
            this.plannedCosts = plannedCosts;
        }

        public double getActualCosts() {
            return actualCosts;
        }

        public void setActualCosts(double actualCosts) {
            this.actualCosts = actualCosts;
        }

        public String getCraft() {
            return craft;
        }

        public void setCraft(String craft) {
            this.craft = craft;
        }

        public String getWorkGroup() {
            return workGroup;
        }

        public void setWorkGroup(String workGroup) {
            this.workGroup = workGroup;
        }

        public List<MaintenanceResource> getMaintenanceResources() {
            return maintenanceResources;
        }

        public void setMaintenanceResources(List<MaintenanceResource> maintenanceResources) {
            this.maintenanceResources = maintenanceResources;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class MaintenanceResource {
        @JsonProperty("Id")
        private String id;

        @JsonProperty("HoursRequired")
        private double hoursRequired;

        @JsonProperty("ActualHours")
        private Double actualHours;

        @JsonProperty("ActualCosts")
        private Double actualCosts;

        @JsonProperty("Resource")
        private Resource resource;

        @JsonProperty("StatusId")
        private int statusId;

        @JsonProperty("ActualStart")
        private String actualStart;

        @JsonProperty("ActualFinish")
        private String actualFinish;

        @JsonProperty("AssignedGroupCraftId")
        private String assignedGroupCraftId;

        @JsonProperty("Craft")
        private String craft;

        @JsonProperty("WorkGroup")
        private String workGroup;

        @JsonProperty("WorkGroupId")
        private int workGroupId;

        @JsonProperty("GroupCraftId")
        private String groupCraftId;

        @JsonProperty("UnitCraftId")
        private String unitCraftId;

        @JsonProperty("CraftQuantity")
        private int craftQuantity;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getHoursRequired() {
            return hoursRequired;
        }

        public void setHoursRequired(double hoursRequired) {
            this.hoursRequired = hoursRequired;
        }

        public Double getActualHours() {
            return actualHours;
        }

        public void setActualHours(Double actualHours) {
            this.actualHours = actualHours;
        }

        public Double getActualCosts() {
            return actualCosts;
        }

        public void setActualCosts(Double actualCosts) {
            this.actualCosts = actualCosts;
        }

        public Resource getResource() {
            return resource;
        }

        public void setResource(Resource resource) {
            this.resource = resource;
        }

        public int getStatusId() {
            return statusId;
        }

        public void setStatusId(int statusId) {
            this.statusId = statusId;
        }

        public String getActualStart() {
            return actualStart;
        }

        public void setActualStart(String actualStart) {
            this.actualStart = actualStart;
        }

        public String getActualFinish() {
            return actualFinish;
        }

        public void setActualFinish(String actualFinish) {
            this.actualFinish = actualFinish;
        }

        public String getAssignedGroupCraftId() {
            return assignedGroupCraftId;
        }

        public void setAssignedGroupCraftId(String assignedGroupCraftId) {
            this.assignedGroupCraftId = assignedGroupCraftId;
        }

        public String getCraft() {
            return craft;
        }

        public void setCraft(String craft) {
            this.craft = craft;
        }

        public String getWorkGroup() {
            return workGroup;
        }

        public void setWorkGroup(String workGroup) {
            this.workGroup = workGroup;
        }

        public int getWorkGroupId() {
            return workGroupId;
        }

        public void setWorkGroupId(int workGroupId) {
            this.workGroupId = workGroupId;
        }

        public String getGroupCraftId() {
            return groupCraftId;
        }

        public void setGroupCraftId(String groupCraftId) {
            this.groupCraftId = groupCraftId;
        }

        public String getUnitCraftId() {
            return unitCraftId;
        }

        public void setUnitCraftId(String unitCraftId) {
            this.unitCraftId = unitCraftId;
        }

        public int getCraftQuantity() {
            return craftQuantity;
        }

        public void setCraftQuantity(int craftQuantity) {
            this.craftQuantity = craftQuantity;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class Resource {
        @JsonProperty("ContractorOrganization")
        private String contractorOrganization;

        @JsonProperty("Id")
        private String id;

        @JsonProperty("DisplayName")
        private String displayName;

        public String getContractorOrganization() {
            return contractorOrganization;
        }

        public void setContractorOrganization(String contractorOrganization) {
            this.contractorOrganization = contractorOrganization;
        }

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
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class LinkedWorkRequest {
        @JsonProperty("FriendlyID")
        private String friendlyID;

        @JsonProperty("ID")
        private String id;

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
    }
}

