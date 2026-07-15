package org.symphony3.smartglue.integration.murrindindi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsseticWorkRequestByExternalIdResponse {

    @JsonProperty("TotalResults")
    private int totalResults;

    @JsonProperty("TotalPages")
    private int totalPages;

    @JsonProperty("Page")
    private int page;

    @JsonProperty("ResourceList")
    private List<Resource> resourceList;

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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Resource {

        @JsonProperty("Id")
        private String id;

        @JsonProperty("FriendlyIdStr")
        private String friendlyIdStr;

        @JsonProperty("ContactType")
        private int contactType;

        @JsonProperty("RequestorId")
        private String requestorId;

        @JsonProperty("WorkRequestSourceId")
        private int workRequestSourceId;

        @JsonProperty("WorkRequestPriorityId")
        private String workRequestPriorityId;

        @JsonProperty("Description")
        private String description;

        @JsonProperty("SupportingInformation")
        private String supportingInformation;

        @JsonProperty("CreatedDateTime")
        private String createdDateTime;

        @JsonProperty("FeedbackRequired")
        private boolean feedbackRequired;

        @JsonProperty("FeedbackMethodId")
        private int feedbackMethodId;

        @JsonProperty("Location")
        private String location;

        @JsonProperty("WorkRequestStatusId")
        private int workRequestStatusId;

        @JsonProperty("WorkRequestStatus")
        private String workRequestStatus;

        @JsonProperty("WorkRequestTypeId")
        private int workRequestTypeId;

        @JsonProperty("WorkRequestSubTypeId")
        private String workRequestSubTypeId;

        @JsonProperty("ClosedDate")
        private String closedDate;

        @JsonProperty("AcknowledgeDueDate")
        private String acknowledgeDueDate;

        @JsonProperty("ResolutionDueDate")
        private String resolutionDueDate;

        @JsonProperty("ComplexAssetId")
        private String complexAssetId;

        @JsonProperty("AssetId")
        private String assetId;

        @JsonProperty("CreatedBy")
        private String createdBy;

        @JsonProperty("WorkOrderId")
        private String workOrderId;

        @JsonProperty("ExternalIdentifier")
        private String externalIdentifier;

        @JsonProperty("WorkOrderPriorityId")
        private String workOrderPriorityId;

        @JsonProperty("ReferenceNumber")
        private String referenceNumber;

        @JsonProperty("LastModified")
        private String lastModified;

        @JsonProperty("Requestor")
        private Requestor requestor;

        @JsonProperty("SupportingInformationHistory")
        private List<SupportingInformation> supportingInformationHistory;

        @JsonProperty("AttachedDocuments")
        private List<DocumentMetadata> attachedDocuments;

        @JsonProperty("WorkRequestPhysicalLocation")
        private WorkRequestPhysicalLocation workRequestPhysicalLocation;

        @JsonProperty("WorkRequestSpatialLocation")
        private WorkRequestSpatialLocation workRequestSpatialLocation;

        @JsonProperty("ReactiveInspectorId")
        private String reactiveInspectorId;

        @JsonProperty("ReactiveInspectionDate")
        private String reactiveInspectionDate;

        @JsonProperty("ReactiveInspector")
        private Inspector reactiveInspector;

        public String getReactiveInspectorId() {
            return reactiveInspectorId;
        }

        public void setReactiveInspectorId(String reactiveInspectorId) {
            this.reactiveInspectorId = reactiveInspectorId;
        }

        public String getReactiveInspectionDate() {
            return reactiveInspectionDate;
        }

        public void setReactiveInspectionDate(String reactiveInspectionDate) {
            this.reactiveInspectionDate = reactiveInspectionDate;
        }

        public Inspector getReactiveInspector() {
            return reactiveInspector;
        }

        public void setReactiveInspector(Inspector reactiveInspector) {
            this.reactiveInspector = reactiveInspector;
        }

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

        public int getContactType() {
            return contactType;
        }

        public void setContactType(int contactType) {
            this.contactType = contactType;
        }

        public String getRequestorId() {
            return requestorId;
        }

        public void setRequestorId(String requestorId) {
            this.requestorId = requestorId;
        }

        public int getWorkRequestSourceId() {
            return workRequestSourceId;
        }

        public void setWorkRequestSourceId(int workRequestSourceId) {
            this.workRequestSourceId = workRequestSourceId;
        }

        public String getWorkRequestPriorityId() {
            return workRequestPriorityId;
        }

        public void setWorkRequestPriorityId(String workRequestPriorityId) {
            this.workRequestPriorityId = workRequestPriorityId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSupportingInformation() {
            return supportingInformation;
        }

        public void setSupportingInformation(String supportingInformation) {
            this.supportingInformation = supportingInformation;
        }

        public String getCreatedDateTime() {
            return createdDateTime;
        }

        public void setCreatedDateTime(String createdDateTime) {
            this.createdDateTime = createdDateTime;
        }

        public boolean isFeedbackRequired() {
            return feedbackRequired;
        }

        public void setFeedbackRequired(boolean feedbackRequired) {
            this.feedbackRequired = feedbackRequired;
        }

        public int getFeedbackMethodId() {
            return feedbackMethodId;
        }

        public void setFeedbackMethodId(int feedbackMethodId) {
            this.feedbackMethodId = feedbackMethodId;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getWorkRequestStatusId() {
            return workRequestStatusId;
        }

        public void setWorkRequestStatusId(int workRequestStatusId) {
            this.workRequestStatusId = workRequestStatusId;
        }

        public String getWorkRequestStatus() {
            return workRequestStatus;
        }

        public void setWorkRequestStatus(String workRequestStatus) {
            this.workRequestStatus = workRequestStatus;
        }

        public int getWorkRequestTypeId() {
            return workRequestTypeId;
        }

        public void setWorkRequestTypeId(int workRequestTypeId) {
            this.workRequestTypeId = workRequestTypeId;
        }

        public String getWorkRequestSubTypeId() {
            return workRequestSubTypeId;
        }

        public void setWorkRequestSubTypeId(String workRequestSubTypeId) {
            this.workRequestSubTypeId = workRequestSubTypeId;
        }

        public String getClosedDate() {
            return closedDate;
        }

        public void setClosedDate(String closedDate) {
            this.closedDate = closedDate;
        }

        public String getAcknowledgeDueDate() {
            return acknowledgeDueDate;
        }

        public void setAcknowledgeDueDate(String acknowledgeDueDate) {
            this.acknowledgeDueDate = acknowledgeDueDate;
        }

        public String getResolutionDueDate() {
            return resolutionDueDate;
        }

        public void setResolutionDueDate(String resolutionDueDate) {
            this.resolutionDueDate = resolutionDueDate;
        }

        public String getComplexAssetId() {
            return complexAssetId;
        }

        public void setComplexAssetId(String complexAssetId) {
            this.complexAssetId = complexAssetId;
        }

        public String getAssetId() {
            return assetId;
        }

        public void setAssetId(String assetId) {
            this.assetId = assetId;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getWorkOrderId() {
            return workOrderId;
        }

        public void setWorkOrderId(String workOrderId) {
            this.workOrderId = workOrderId;
        }

        public String getExternalIdentifier() {
            return externalIdentifier;
        }

        public void setExternalIdentifier(String externalIdentifier) {
            this.externalIdentifier = externalIdentifier;
        }

        public String getWorkOrderPriorityId() {
            return workOrderPriorityId;
        }

        public void setWorkOrderPriorityId(String workOrderPriorityId) {
            this.workOrderPriorityId = workOrderPriorityId;
        }

        public String getReferenceNumber() {
            return referenceNumber;
        }

        public void setReferenceNumber(String referenceNumber) {
            this.referenceNumber = referenceNumber;
        }

        public String getLastModified() {
            return lastModified;
        }

        public void setLastModified(String lastModified) {
            this.lastModified = lastModified;
        }

        public Requestor getRequestor() {
            return requestor;
        }

        public void setRequestor(Requestor requestor) {
            this.requestor = requestor;
        }

        public List<SupportingInformation> getSupportingInformationHistory() {
            return supportingInformationHistory;
        }

        public void setSupportingInformationHistory(List<SupportingInformation> supportingInformationHistory) {
            this.supportingInformationHistory = supportingInformationHistory;
        }

        public List<DocumentMetadata> getAttachedDocuments() {
            return attachedDocuments;
        }

        public void setAttachedDocuments(List<DocumentMetadata> attachedDocuments) {
            this.attachedDocuments = attachedDocuments;
        }

        public WorkRequestPhysicalLocation getWorkRequestPhysicalLocation() {
            return workRequestPhysicalLocation;
        }

        public void setWorkRequestPhysicalLocation(WorkRequestPhysicalLocation workRequestPhysicalLocation) {
            this.workRequestPhysicalLocation = workRequestPhysicalLocation;
        }

        public WorkRequestSpatialLocation getWorkRequestSpatialLocation() {
            return workRequestSpatialLocation;
        }

        public void setWorkRequestSpatialLocation(WorkRequestSpatialLocation workRequestSpatialLocation) {
            this.workRequestSpatialLocation = workRequestSpatialLocation;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Requestor {
        @JsonProperty("Id")
        private String id;
        @JsonProperty("DisplayName")
        private String displayName;
        @JsonProperty("FirstName")
        private String firstName;
        @JsonProperty("StatusId")
        private int statusId;
        @JsonProperty("Status")
        private String status;

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

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public int getStatusId() {
            return statusId;
        }

        public void setStatusId(int statusId) {
            this.statusId = statusId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
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
    public static class WorkRequestPhysicalLocation {
        @JsonProperty("Address")
        private Address address;
        @JsonProperty("OtherLocation")
        private String otherLocation;
        @JsonProperty("WhereLocation")
        private String whereLocation;
        @JsonProperty("AddressId")
        private String addressId;

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public String getOtherLocation() {
            return otherLocation;
        }

        public void setOtherLocation(String otherLocation) {
            this.otherLocation = otherLocation;
        }

        public String getWhereLocation() {
            return whereLocation;
        }

        public void setWhereLocation(String whereLocation) {
            this.whereLocation = whereLocation;
        }

        public String getAddressId() {
            return addressId;
        }

        public void setAddressId(String addressId) {
            this.addressId = addressId;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        @JsonProperty("Id")
        private String id;
        @JsonProperty("StreetAddress")
        private String streetAddress;
        @JsonProperty("CitySuburb")
        private String citySuburb;
        @JsonProperty("State")
        private String state;
        @JsonProperty("Country")
        private String country;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStreetAddress() {
            return streetAddress;
        }

        public void setStreetAddress(String streetAddress) {
            this.streetAddress = streetAddress;
        }

        public String getCitySuburb() {
            return citySuburb;
        }

        public void setCitySuburb(String citySuburb) {
            this.citySuburb = citySuburb;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WorkRequestSpatialLocation {
        @JsonProperty("Point")
        private Point point;
        @JsonProperty("PointString")
        private String pointString;

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }

        public String getPointString() {
            return pointString;
        }

        public void setPointString(String pointString) {
            this.pointString = pointString;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Point {
        @JsonProperty("Geography")
        private Geography geography;

        public Geography getGeography() {
            return geography;
        }

        public void setGeography(Geography geography) {
            this.geography = geography;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geography {
        @JsonProperty("CoordinateSystemId")
        private int coordinateSystemId;
        @JsonProperty("WellKnownText")
        private String wellKnownText;

        public int getCoordinateSystemId() {
            return coordinateSystemId;
        }

        public void setCoordinateSystemId(int coordinateSystemId) {
            this.coordinateSystemId = coordinateSystemId;
        }

        public String getWellKnownText() {
            return wellKnownText;
        }

        public void setWellKnownText(String wellKnownText) {
            this.wellKnownText = wellKnownText;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Inspector {
        @JsonProperty("Id")
        private String Id;
        @JsonProperty("DisplayName")
        private String DisplayName;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getDisplayName() {
            return DisplayName;
        }

        public void setDisplayName(String displayName) {
            DisplayName = displayName;
        }
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DocumentMetadata {

        @JsonProperty("Id")
        private String id;

        @JsonProperty("Name")
        private String name;

        @JsonProperty("Label")
        private String label;

        @JsonProperty("ExternalId")
        private String externalId;

        @JsonProperty("Description")
        private String description;

        @JsonProperty("ExpiryDate")
        private String expiryDate;

        @JsonProperty("IsExpired")
        private boolean isExpired;

        @JsonProperty("CreatedDate")
        private String createdDate;

        @JsonProperty("LastModified")
        private String lastModified;

        @JsonProperty("ParentType")
        private int parentType;

        @JsonProperty("ParentId")
        private String parentId;

        @JsonProperty("RecordId")
        private String recordId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getExternalId() {
            return externalId;
        }

        public void setExternalId(String externalId) {
            this.externalId = externalId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public boolean isExpired() {
            return isExpired;
        }

        public void setExpired(boolean expired) {
            isExpired = expired;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getLastModified() {
            return lastModified;
        }

        public void setLastModified(String lastModified) {
            this.lastModified = lastModified;
        }

        public int getParentType() {
            return parentType;
        }

        public void setParentType(int parentType) {
            this.parentType = parentType;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }
    }
}
