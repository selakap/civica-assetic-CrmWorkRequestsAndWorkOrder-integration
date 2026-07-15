package org.symphony3.smartglue.integration.murrindindi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AuthorityTaskCloseRequest {

    @JsonProperty("determinationCode")
    private String determinationCode;

    @JsonProperty("completionDateTime")
    private String completionDateTime;

    // Getters and Setters
    public String getDeterminationCode() {
        return determinationCode;
    }

    public void setDeterminationCode(String determinationCode) {
        this.determinationCode = determinationCode;
    }

    public String getCompletionDateTime() {
        return completionDateTime;
    }

    public void setCompletionDateTime(String completionDateTime) {
        this.completionDateTime = completionDateTime;
    }
}
