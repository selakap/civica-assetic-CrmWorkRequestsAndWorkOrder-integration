package org.symphony3.smartglue.integration.murrindindi.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AuthorityGPSLocationResponse {

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
// Getters and Setters

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class Result {
        @JsonProperty("coordinates")
        private List<Coordinate> coordinates;

        @JsonProperty("latitude")
        private double latitude;

        @JsonProperty("longitude")
        private double longitude;

        public List<Coordinate> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<Coordinate> coordinates) {
            this.coordinates = coordinates;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
// Getters and Setters
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class Coordinate {
        @JsonProperty("sequence")
        private int sequence;

        @JsonProperty("latitude")
        private double latitude;

        @JsonProperty("longitude")
        private double longitude;

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
// Getters and Setters
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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
// Getters and Setters
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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
