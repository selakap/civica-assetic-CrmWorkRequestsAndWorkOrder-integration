package org.symphony3.smartglue.integration.murrindindi.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AuthorityAttachmentsMetadataRequest {

    @JsonProperty("formattedAccounts")
    private List<String> formattedAccounts;

    @JsonProperty("moduleReference")
    private String moduleReference;

    @JsonProperty("includeInTransit")
    private boolean includeInTransit;

    @JsonProperty("includeOnHold")
    private boolean includeOnHold;

    // Getters and setters
    public List<String> getFormattedAccounts() {
        return formattedAccounts;
    }

    public void setFormattedAccounts(List<String> formattedAccounts) {
        this.formattedAccounts = formattedAccounts;
    }

    public String getModuleReference() {
        return moduleReference;
    }

    public void setModuleReference(String moduleReference) {
        this.moduleReference = moduleReference;
    }

    public boolean isIncludeInTransit() {
        return includeInTransit;
    }

    public void setIncludeInTransit(boolean includeInTransit) {
        this.includeInTransit = includeInTransit;
    }

    public boolean isIncludeOnHold() {
        return includeOnHold;
    }

    public void setIncludeOnHold(boolean includeOnHold) {
        this.includeOnHold = includeOnHold;
    }
}

