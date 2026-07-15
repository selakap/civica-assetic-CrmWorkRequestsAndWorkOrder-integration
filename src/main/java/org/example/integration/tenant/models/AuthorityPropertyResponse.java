package org.example.integration.tenant.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityPropertyResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("resultCount")
    private int resultCount;

    @JsonProperty("result")
    private List<ParcelAddressResult> result;

    @JsonProperty("totalCount")
    private int totalCount;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("accessResult")
    private List<Object> accessResult;

    // Getters and Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public int getResultCount() { return resultCount; }
    public void setResultCount(int resultCount) { this.resultCount = resultCount; }

    public List<ParcelAddressResult> getResult() { return result; }
    public void setResult(List<ParcelAddressResult> result) { this.result = result; }

    public int getTotalCount() { return totalCount; }
    public void setTotalCount(int totalCount) { this.totalCount = totalCount; }

    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    public List<Object> getAccessResult() { return accessResult; }
    public void setAccessResult(List<Object> accessResult) { this.accessResult = accessResult; }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ParcelAddressResult {

        @JsonProperty("parcelId")
        private int parcelId;

        @JsonProperty("unitNumber")
        private String unitNumber;

        @JsonProperty("parcelFlag")
        private String parcelFlag;

        @JsonProperty("unitAlpha")
        private String unitAlpha;

        @JsonProperty("houseNumber")
        private String houseNumber;

        @JsonProperty("houseAlpha")
        private String houseAlpha;

        @JsonProperty("houseEndNumber")
        private String houseEndNumber;

        @JsonProperty("houseEndAlpha")
        private String houseEndAlpha;

        @JsonProperty("streetName")
        private String streetName;

        @JsonProperty("streetTypeCode")
        private String streetTypeCode;

        @JsonProperty("streetTypeDescription")
        private String streetTypeDescription;

        @JsonProperty("suburb")
        private String suburb;

        @JsonProperty("state")
        private String state;

        @JsonProperty("postcode")
        private String postcode;

        @JsonProperty("formattedAddressNoState")
        private String formattedAddressNoState;

        @JsonProperty("formattedAddressNoSuburb")
        private String formattedAddressNoSuburb;

        @JsonProperty("formattedHouseNumber")
        private String formattedHouseNumber;

        // Getters and Setters
        public int getParcelId() { return parcelId; }
        public void setParcelId(int parcelId) { this.parcelId = parcelId; }

        public String getUnitNumber() { return unitNumber; }
        public void setUnitNumber(String unitNumber) { this.unitNumber = unitNumber; }

        public String getParcelFlag() { return parcelFlag; }
        public void setParcelFlag(String parcelFlag) { this.parcelFlag = parcelFlag; }

        public String getUnitAlpha() { return unitAlpha; }
        public void setUnitAlpha(String unitAlpha) { this.unitAlpha = unitAlpha; }

        public String getHouseNumber() { return houseNumber; }
        public void setHouseNumber(String houseNumber) { this.houseNumber = houseNumber; }

        public String getHouseAlpha() { return houseAlpha; }
        public void setHouseAlpha(String houseAlpha) { this.houseAlpha = houseAlpha; }

        public String getHouseEndNumber() { return houseEndNumber; }
        public void setHouseEndNumber(String houseEndNumber) { this.houseEndNumber = houseEndNumber; }

        public String getHouseEndAlpha() { return houseEndAlpha; }
        public void setHouseEndAlpha(String houseEndAlpha) { this.houseEndAlpha = houseEndAlpha; }

        public String getStreetName() { return streetName; }
        public void setStreetName(String streetName) { this.streetName = streetName; }

        public String getStreetTypeCode() { return streetTypeCode; }
        public void setStreetTypeCode(String streetTypeCode) { this.streetTypeCode = streetTypeCode; }

        public String getStreetTypeDescription() { return streetTypeDescription; }
        public void setStreetTypeDescription(String streetTypeDescription) { this.streetTypeDescription = streetTypeDescription; }

        public String getSuburb() { return suburb; }
        public void setSuburb(String suburb) { this.suburb = suburb; }

        public String getState() { return state; }
        public void setState(String state) { this.state = state; }

        public String getPostcode() { return postcode; }
        public void setPostcode(String postcode) { this.postcode = postcode; }

        public String getFormattedAddressNoState() { return formattedAddressNoState; }
        public void setFormattedAddressNoState(String formattedAddressNoState) { this.formattedAddressNoState = formattedAddressNoState; }

        public String getFormattedAddressNoSuburb() { return formattedAddressNoSuburb; }
        public void setFormattedAddressNoSuburb(String formattedAddressNoSuburb) { this.formattedAddressNoSuburb = formattedAddressNoSuburb; }

        public String getFormattedHouseNumber() { return formattedHouseNumber; }
        public void setFormattedHouseNumber(String formattedHouseNumber) { this.formattedHouseNumber = formattedHouseNumber; }
    }
}

