package org.example.integration.tenant.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityNameResponse {

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
    private List<AccessResult> accessResult;

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

    public List<AccessResult> getAccessResult() {
        return accessResult;
    }

    public void setAccessResult(List<AccessResult> accessResult) {
        this.accessResult = accessResult;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {

        @JsonProperty("id")
        private int id;

        @JsonProperty("familiarName")
        private String familiarName;

        @JsonProperty("alphaSortKey")
        private String alphaSortKey;

        @JsonProperty("familyName")
        private String familyName;

        @JsonProperty("givenName1")
        private String givenName1;

        @JsonProperty("givenName2")
        private String givenName2;

        @JsonProperty("initials")
        private String initials;

        @JsonProperty("title")
        private String title;

        @JsonProperty("seniorityFlag")
        private String seniorityFlag;

        @JsonProperty("gender")
        private String gender;

        @JsonProperty("maritalStatus")
        private String maritalStatus;

        @JsonProperty("pensionerType")
        private Integer pensionerType;

        @JsonProperty("pensionerNumber")
        private String pensionerNumber;

        @JsonProperty("dateOfBirth")
        private String dateOfBirth;

        @JsonProperty("dateOfDeath")
        private String dateOfDeath;

        @JsonProperty("formatName1")
        private String formatName1;

        @JsonProperty("formatName2")
        private String formatName2;

        @JsonProperty("homePhone1")
        private String homePhone1;

        @JsonProperty("homePhone2")
        private String homePhone2;

        @JsonProperty("mobilePhone")
        private String mobilePhone;

        @JsonProperty("businessPhone")
        private String businessPhone;

        @JsonProperty("faxPhone")
        private String faxPhone;

        @JsonProperty("communicationsPhone")
        private String communicationsPhone;

        @JsonProperty("documentExchangeNumber")
        private String documentExchangeNumber;

        @JsonProperty("businessMobilePhone")
        private String businessMobilePhone;

        @JsonProperty("businessExtension")
        private String businessExtension;

        @JsonProperty("emailAddress")
        private String emailAddress;

        @JsonProperty("nonGstRegisteredOrganisationNumber")
        private String nonGstRegisteredOrganisationNumber;

        @JsonProperty("gstRegisteredOrganisationNumber")
        private String gstRegisteredOrganisationNumber;

        @JsonProperty("homeWebPage")
        private String homeWebPage;

        @JsonProperty("privacyGroupName")
        private String privacyGroupName;

        @JsonProperty("privacyGroupAddress")
        private String privacyGroupAddress;

        @JsonProperty("privacyGroupPhone")
        private String privacyGroupPhone;

        @JsonProperty("privacyGroupEmail")
        private String privacyGroupEmail;

        @JsonProperty("privacyGroupBirth")
        private String privacyGroupBirth;

        @JsonProperty("active")
        private String active;

        @JsonProperty("addressId")
        private Integer addressId;

        @JsonProperty("organisationName")
        private String organisationName;

        @JsonProperty("alternateName")
        private String alternateName;

        @JsonProperty("alternateNameType")
        private String alternateNameType;

        @JsonProperty("careOfNameId")
        private String careOfNameId;

        @JsonProperty("mergeNotAllowedFlag")
        private String mergeNotAllowedFlag;

        @JsonProperty("isOrganisation")
        private boolean isOrganisation;

        @JsonProperty("formatAddress1")
        private String formatAddress1;

        @JsonProperty("formatAddress2")
        private String formatAddress2;

        @JsonProperty("formatAddress3")
        private String formatAddress3;

        @JsonProperty("formatAddress4")
        private String formatAddress4;

        @JsonProperty("formatAddress5")
        private String formatAddress5;

        @JsonProperty("formatAddress6")
        private String formatAddress6;

        @JsonProperty("modifiedOperatorName")
        private String modifiedOperatorName;

        @JsonProperty("fullName")
        private FullName fullName;

        @JsonProperty("createdDateTime")
        private String createdDateTime;

        @JsonProperty("modifiedDateTime")
        private String modifiedDateTime;

        @JsonProperty("modifiedOperatorId")
        private Integer modifiedOperatorId;

        @JsonProperty("rowVersion")
        private String rowVersion;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFamiliarName() {
            return familiarName;
        }

        public void setFamiliarName(String familiarName) {
            this.familiarName = familiarName;
        }

        public String getAlphaSortKey() {
            return alphaSortKey;
        }

        public void setAlphaSortKey(String alphaSortKey) {
            this.alphaSortKey = alphaSortKey;
        }

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }

        public String getGivenName1() {
            return givenName1;
        }

        public void setGivenName1(String givenName1) {
            this.givenName1 = givenName1;
        }

        public String getGivenName2() {
            return givenName2;
        }

        public void setGivenName2(String givenName2) {
            this.givenName2 = givenName2;
        }

        public String getInitials() {
            return initials;
        }

        public void setInitials(String initials) {
            this.initials = initials;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSeniorityFlag() {
            return seniorityFlag;
        }

        public void setSeniorityFlag(String seniorityFlag) {
            this.seniorityFlag = seniorityFlag;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public Integer getPensionerType() {
            return pensionerType;
        }

        public void setPensionerType(Integer pensionerType) {
            this.pensionerType = pensionerType;
        }

        public String getPensionerNumber() {
            return pensionerNumber;
        }

        public void setPensionerNumber(String pensionerNumber) {
            this.pensionerNumber = pensionerNumber;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getDateOfDeath() {
            return dateOfDeath;
        }

        public void setDateOfDeath(String dateOfDeath) {
            this.dateOfDeath = dateOfDeath;
        }

        public String getFormatName1() {
            return formatName1;
        }

        public void setFormatName1(String formatName1) {
            this.formatName1 = formatName1;
        }

        public String getFormatName2() {
            return formatName2;
        }

        public void setFormatName2(String formatName2) {
            this.formatName2 = formatName2;
        }

        public String getHomePhone1() {
            return homePhone1;
        }

        public void setHomePhone1(String homePhone1) {
            this.homePhone1 = homePhone1;
        }

        public String getHomePhone2() {
            return homePhone2;
        }

        public void setHomePhone2(String homePhone2) {
            this.homePhone2 = homePhone2;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getBusinessPhone() {
            return businessPhone;
        }

        public void setBusinessPhone(String businessPhone) {
            this.businessPhone = businessPhone;
        }

        public String getFaxPhone() {
            return faxPhone;
        }

        public void setFaxPhone(String faxPhone) {
            this.faxPhone = faxPhone;
        }

        public String getCommunicationsPhone() {
            return communicationsPhone;
        }

        public void setCommunicationsPhone(String communicationsPhone) {
            this.communicationsPhone = communicationsPhone;
        }

        public String getDocumentExchangeNumber() {
            return documentExchangeNumber;
        }

        public void setDocumentExchangeNumber(String documentExchangeNumber) {
            this.documentExchangeNumber = documentExchangeNumber;
        }

        public String getBusinessMobilePhone() {
            return businessMobilePhone;
        }

        public void setBusinessMobilePhone(String businessMobilePhone) {
            this.businessMobilePhone = businessMobilePhone;
        }

        public String getBusinessExtension() {
            return businessExtension;
        }

        public void setBusinessExtension(String businessExtension) {
            this.businessExtension = businessExtension;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getNonGstRegisteredOrganisationNumber() {
            return nonGstRegisteredOrganisationNumber;
        }

        public void setNonGstRegisteredOrganisationNumber(String nonGstRegisteredOrganisationNumber) {
            this.nonGstRegisteredOrganisationNumber = nonGstRegisteredOrganisationNumber;
        }

        public String getGstRegisteredOrganisationNumber() {
            return gstRegisteredOrganisationNumber;
        }

        public void setGstRegisteredOrganisationNumber(String gstRegisteredOrganisationNumber) {
            this.gstRegisteredOrganisationNumber = gstRegisteredOrganisationNumber;
        }

        public String getHomeWebPage() {
            return homeWebPage;
        }

        public void setHomeWebPage(String homeWebPage) {
            this.homeWebPage = homeWebPage;
        }

        public String getPrivacyGroupName() {
            return privacyGroupName;
        }

        public void setPrivacyGroupName(String privacyGroupName) {
            this.privacyGroupName = privacyGroupName;
        }

        public String getPrivacyGroupAddress() {
            return privacyGroupAddress;
        }

        public void setPrivacyGroupAddress(String privacyGroupAddress) {
            this.privacyGroupAddress = privacyGroupAddress;
        }

        public String getPrivacyGroupPhone() {
            return privacyGroupPhone;
        }

        public void setPrivacyGroupPhone(String privacyGroupPhone) {
            this.privacyGroupPhone = privacyGroupPhone;
        }

        public String getPrivacyGroupEmail() {
            return privacyGroupEmail;
        }

        public void setPrivacyGroupEmail(String privacyGroupEmail) {
            this.privacyGroupEmail = privacyGroupEmail;
        }

        public String getPrivacyGroupBirth() {
            return privacyGroupBirth;
        }

        public void setPrivacyGroupBirth(String privacyGroupBirth) {
            this.privacyGroupBirth = privacyGroupBirth;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public Integer getAddressId() {
            return addressId;
        }

        public void setAddressId(Integer addressId) {
            this.addressId = addressId;
        }

        public String getOrganisationName() {
            return organisationName;
        }

        public void setOrganisationName(String organisationName) {
            this.organisationName = organisationName;
        }

        public String getAlternateName() {
            return alternateName;
        }

        public void setAlternateName(String alternateName) {
            this.alternateName = alternateName;
        }

        public String getAlternateNameType() {
            return alternateNameType;
        }

        public void setAlternateNameType(String alternateNameType) {
            this.alternateNameType = alternateNameType;
        }

        public String getCareOfNameId() {
            return careOfNameId;
        }

        public void setCareOfNameId(String careOfNameId) {
            this.careOfNameId = careOfNameId;
        }

        public String getMergeNotAllowedFlag() {
            return mergeNotAllowedFlag;
        }

        public void setMergeNotAllowedFlag(String mergeNotAllowedFlag) {
            this.mergeNotAllowedFlag = mergeNotAllowedFlag;
        }

        public boolean isOrganisation() {
            return isOrganisation;
        }

        public void setOrganisation(boolean organisation) {
            isOrganisation = organisation;
        }

        public String getFormatAddress1() {
            return formatAddress1;
        }

        public void setFormatAddress1(String formatAddress1) {
            this.formatAddress1 = formatAddress1;
        }

        public String getFormatAddress2() {
            return formatAddress2;
        }

        public void setFormatAddress2(String formatAddress2) {
            this.formatAddress2 = formatAddress2;
        }

        public String getFormatAddress3() {
            return formatAddress3;
        }

        public void setFormatAddress3(String formatAddress3) {
            this.formatAddress3 = formatAddress3;
        }

        public String getFormatAddress4() {
            return formatAddress4;
        }

        public void setFormatAddress4(String formatAddress4) {
            this.formatAddress4 = formatAddress4;
        }

        public String getFormatAddress5() {
            return formatAddress5;
        }

        public void setFormatAddress5(String formatAddress5) {
            this.formatAddress5 = formatAddress5;
        }

        public String getFormatAddress6() {
            return formatAddress6;
        }

        public void setFormatAddress6(String formatAddress6) {
            this.formatAddress6 = formatAddress6;
        }

        public String getModifiedOperatorName() {
            return modifiedOperatorName;
        }

        public void setModifiedOperatorName(String modifiedOperatorName) {
            this.modifiedOperatorName = modifiedOperatorName;
        }

        public FullName getFullName() {
            return fullName;
        }

        public void setFullName(FullName fullName) {
            this.fullName = fullName;
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

        public Integer getModifiedOperatorId() {
            return modifiedOperatorId;
        }

        public void setModifiedOperatorId(Integer modifiedOperatorId) {
            this.modifiedOperatorId = modifiedOperatorId;
        }

        public String getRowVersion() {
            return rowVersion;
        }

        public void setRowVersion(String rowVersion) {
            this.rowVersion = rowVersion;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FullName {
        @JsonProperty("nameId")
        private int nameId;

        @JsonProperty("name")
        private String name;

        public int getNameId() {
            return nameId;
        }

        public void setNameId(int nameId) {
            this.nameId = nameId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
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
        private Map<String, Object> disabledContent;

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

        public Map<String, Object> getDisabledContent() {
            return disabledContent;
        }

        public void setDisabledContent(Map<String, Object> disabledContent) {
            this.disabledContent = disabledContent;
        }
    }
}

