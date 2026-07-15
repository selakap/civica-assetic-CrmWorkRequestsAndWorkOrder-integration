package org.symphony3.smartglue.integration.murrindindi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AsseticGetDocumentResponse {

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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class Resource {

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

        @JsonProperty("DocumentSize")
        private double documentSize;

        @JsonProperty("DocumentLink")
        private String documentLink;

        @JsonProperty("MimeType")
        private String mimeType;

        @JsonProperty("DocumentGroup")
        private int documentGroup;

        @JsonProperty("DocGroupValue")
        private String docGroupValue;

        @JsonProperty("DocGroupLabel")
        private String docGroupLabel;

        @JsonProperty("DocumentSubCategory")
        private int documentSubCategory;

        @JsonProperty("DocSubCategoryValue")
        private String docSubCategoryValue;

        @JsonProperty("DocSubCategoryLabel")
        private String docSubCategoryLabel;

        @JsonProperty("DocumentCategory")
        private int documentCategory;

        @JsonProperty("DocCategoryValue")
        private String docCategoryValue;

        @JsonProperty("DocCategoryLabel")
        private String docCategoryLabel;

        @JsonProperty("CreatedUserFirstName")
        private String createdUserFirstName;

        @JsonProperty("CreatedUserLastName")
        private String createdUserLastName;

        @JsonProperty("LastModifiedUserFirstName")
        private String lastModifiedUserFirstName;

        @JsonProperty("LastModifiedUserLastName")
        private String lastModifiedUserLastName;

        @JsonProperty("FileProperty")
        private List<Object> fileProperty;

        @JsonProperty("DocumentSource")
        private String documentSource;

        @JsonProperty("ParentType")
        private int parentType;

        @JsonProperty("ParentId")
        private String parentId;

        @JsonProperty("ParentIdentifier")
        private String parentIdentifier;

        @JsonProperty("RecordId")
        private String recordId;

        @JsonProperty("IsKeyPhoto")
        private boolean isKeyPhoto;

        @JsonProperty("IsImage")
        private boolean isImage;

        @JsonProperty("Status")
        private int status;

        @JsonProperty("DocumentAsset")
        private Object documentAsset;

        @JsonProperty("DocumentWorkRequest")
        private DocumentWorkRequest documentWorkRequest;

        @JsonProperty("DocumentWorkOrder")
        private Object documentWorkOrder;

        @JsonProperty("DocumentAssessmentFormResult")
        private Object documentAssessmentFormResult;

        @JsonProperty("DocumentGroupAsset")
        private Object documentGroupAsset;

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

        public double getDocumentSize() {
            return documentSize;
        }

        public void setDocumentSize(double documentSize) {
            this.documentSize = documentSize;
        }

        public String getDocumentLink() {
            return documentLink;
        }

        public void setDocumentLink(String documentLink) {
            this.documentLink = documentLink;
        }

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public int getDocumentGroup() {
            return documentGroup;
        }

        public void setDocumentGroup(int documentGroup) {
            this.documentGroup = documentGroup;
        }

        public String getDocGroupValue() {
            return docGroupValue;
        }

        public void setDocGroupValue(String docGroupValue) {
            this.docGroupValue = docGroupValue;
        }

        public String getDocGroupLabel() {
            return docGroupLabel;
        }

        public void setDocGroupLabel(String docGroupLabel) {
            this.docGroupLabel = docGroupLabel;
        }

        public int getDocumentSubCategory() {
            return documentSubCategory;
        }

        public void setDocumentSubCategory(int documentSubCategory) {
            this.documentSubCategory = documentSubCategory;
        }

        public String getDocSubCategoryValue() {
            return docSubCategoryValue;
        }

        public void setDocSubCategoryValue(String docSubCategoryValue) {
            this.docSubCategoryValue = docSubCategoryValue;
        }

        public String getDocSubCategoryLabel() {
            return docSubCategoryLabel;
        }

        public void setDocSubCategoryLabel(String docSubCategoryLabel) {
            this.docSubCategoryLabel = docSubCategoryLabel;
        }

        public int getDocumentCategory() {
            return documentCategory;
        }

        public void setDocumentCategory(int documentCategory) {
            this.documentCategory = documentCategory;
        }

        public String getDocCategoryValue() {
            return docCategoryValue;
        }

        public void setDocCategoryValue(String docCategoryValue) {
            this.docCategoryValue = docCategoryValue;
        }

        public String getDocCategoryLabel() {
            return docCategoryLabel;
        }

        public void setDocCategoryLabel(String docCategoryLabel) {
            this.docCategoryLabel = docCategoryLabel;
        }

        public String getCreatedUserFirstName() {
            return createdUserFirstName;
        }

        public void setCreatedUserFirstName(String createdUserFirstName) {
            this.createdUserFirstName = createdUserFirstName;
        }

        public String getCreatedUserLastName() {
            return createdUserLastName;
        }

        public void setCreatedUserLastName(String createdUserLastName) {
            this.createdUserLastName = createdUserLastName;
        }

        public String getLastModifiedUserFirstName() {
            return lastModifiedUserFirstName;
        }

        public void setLastModifiedUserFirstName(String lastModifiedUserFirstName) {
            this.lastModifiedUserFirstName = lastModifiedUserFirstName;
        }

        public String getLastModifiedUserLastName() {
            return lastModifiedUserLastName;
        }

        public void setLastModifiedUserLastName(String lastModifiedUserLastName) {
            this.lastModifiedUserLastName = lastModifiedUserLastName;
        }

        public List<Object> getFileProperty() {
            return fileProperty;
        }

        public void setFileProperty(List<Object> fileProperty) {
            this.fileProperty = fileProperty;
        }

        public String getDocumentSource() {
            return documentSource;
        }

        public void setDocumentSource(String documentSource) {
            this.documentSource = documentSource;
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

        public String getParentIdentifier() {
            return parentIdentifier;
        }

        public void setParentIdentifier(String parentIdentifier) {
            this.parentIdentifier = parentIdentifier;
        }

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }

        public boolean isKeyPhoto() {
            return isKeyPhoto;
        }

        public void setKeyPhoto(boolean keyPhoto) {
            isKeyPhoto = keyPhoto;
        }

        public boolean isImage() {
            return isImage;
        }

        public void setImage(boolean image) {
            isImage = image;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getDocumentAsset() {
            return documentAsset;
        }

        public void setDocumentAsset(Object documentAsset) {
            this.documentAsset = documentAsset;
        }

        public DocumentWorkRequest getDocumentWorkRequest() {
            return documentWorkRequest;
        }

        public void setDocumentWorkRequest(DocumentWorkRequest documentWorkRequest) {
            this.documentWorkRequest = documentWorkRequest;
        }

        public Object getDocumentWorkOrder() {
            return documentWorkOrder;
        }

        public void setDocumentWorkOrder(Object documentWorkOrder) {
            this.documentWorkOrder = documentWorkOrder;
        }

        public Object getDocumentAssessmentFormResult() {
            return documentAssessmentFormResult;
        }

        public void setDocumentAssessmentFormResult(Object documentAssessmentFormResult) {
            this.documentAssessmentFormResult = documentAssessmentFormResult;
        }

        public Object getDocumentGroupAsset() {
            return documentGroupAsset;
        }

        public void setDocumentGroupAsset(Object documentGroupAsset) {
            this.documentGroupAsset = documentGroupAsset;
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class DocumentWorkRequest {

        @JsonProperty("WorkRequestId")
        private String workRequestId;

        @JsonProperty("FriendlyId")
        private String friendlyId;

        @JsonProperty("DocumentAsset")
        private DocumentAsset documentAsset;

        @JsonProperty("_links")
        private List<Object> links;

        @JsonProperty("_embedded")
        private Object embedded;

        public String getWorkRequestId() {
            return workRequestId;
        }

        public void setWorkRequestId(String workRequestId) {
            this.workRequestId = workRequestId;
        }

        public String getFriendlyId() {
            return friendlyId;
        }

        public void setFriendlyId(String friendlyId) {
            this.friendlyId = friendlyId;
        }

        public DocumentAsset getDocumentAsset() {
            return documentAsset;
        }

        public void setDocumentAsset(DocumentAsset documentAsset) {
            this.documentAsset = documentAsset;
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class DocumentAsset {

        @JsonProperty("AssetName")
        private String assetName;

        @JsonProperty("Id")
        private String id;

        @JsonProperty("AssetId")
        private String assetId;

        @JsonProperty("AssetCategoryId")
        private String assetCategoryId;

        @JsonProperty("AssetCategory")
        private String assetCategory;

        @JsonProperty("_links")
        private List<Object> links;

        @JsonProperty("_embedded")
        private Object embedded;

        public String getAssetName() {
            return assetName;
        }

        public void setAssetName(String assetName) {
            this.assetName = assetName;
        }

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
