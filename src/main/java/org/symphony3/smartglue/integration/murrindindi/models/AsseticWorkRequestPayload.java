package org.symphony3.smartglue.integration.murrindindi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AsseticWorkRequestPayload {

    @JsonProperty("WorkRequestSourceId")
    private int workRequestSourceId;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("SupportingInformation")
    private String supportingInformation;

    @JsonProperty("Location")
    private String location;

    @JsonProperty("ExternalIdentifier")
    private String externalIdentifier;

    @JsonProperty("ReferenceNumber")
    private String referenceNumber;

    @JsonProperty("Requestor")
    private Requestor requestor;

    @JsonProperty("WorkRequestPhysicalLocation")
    private WorkRequestPhysicalLocation workRequestPhysicalLocation;

    @JsonProperty("WorkRequestSpatialLocation")
    private WorkRequestSpatialLocation workRequestSpatialLocation;

    public WorkRequestSpatialLocation getWorkRequestSpatialLocation() {
        return workRequestSpatialLocation;
    }

    public void setWorkRequestSpatialLocation(WorkRequestSpatialLocation workRequestSpatialLocation) {
        this.workRequestSpatialLocation = workRequestSpatialLocation;
    }

    public int getWorkRequestSourceId() {
        return workRequestSourceId;
    }

    public void setWorkRequestSourceId(int workRequestSourceId) {
        this.workRequestSourceId = workRequestSourceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExternalIdentifier() {
        return externalIdentifier;
    }

    public void setExternalIdentifier(String externalIdentifier) {
        this.externalIdentifier = externalIdentifier;
    }

    public Requestor getRequestor() {
        return requestor;
    }

    public void setRequestor(Requestor requestor) {
        this.requestor = requestor;
    }

    public WorkRequestPhysicalLocation getWorkRequestPhysicalLocation() {
        return workRequestPhysicalLocation;
    }

    public void setWorkRequestPhysicalLocation(WorkRequestPhysicalLocation workRequestPhysicalLocation) {
        this.workRequestPhysicalLocation = workRequestPhysicalLocation;
    }

    public String getSupportingInformation() {
        return supportingInformation;
    }

    public void setSupportingInformation(String supportingInformation) {
        this.supportingInformation = supportingInformation;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class Requestor {
        @JsonProperty("FirstName")
        private String firstName;

        @JsonProperty("Surname")
        private String surName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        public String getSurName() {
            return surName;
        }

        public void setSurName(String surName) {
            this.surName = surName;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class WorkRequestPhysicalLocation {
        @JsonProperty("Address")
        private Address address;

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class Address {
        @JsonProperty("StreetNumber")
        private String streetNumber;

        @JsonProperty("StreetAddress")
        private String streetAddress;

        @JsonProperty("CitySuburb")
        private String citySuburb;

        @JsonProperty("State")
        private String state;

        @JsonProperty("ZipPostcode")
        private String zipPostcode;

        @JsonProperty("Country")
        private String country;

        // Getters and Setters
        public String getStreetNumber() {
            return streetNumber;
        }

        public void setStreetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
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

        public String getZipPostcode() {
            return zipPostcode;
        }

        public void setZipPostcode(String zipPostcode) {
            this.zipPostcode = zipPostcode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class WorkRequestSpatialLocation {
        @JsonProperty("PointString")
        private String pointString;

        public String getPointString() {
            return pointString;
        }

        public void setPointString(String pointString) {
            this.pointString = pointString;
        }
    }

}

