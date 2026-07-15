package org.example.integration.tenant.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityTaskConfiguration {

    @JsonProperty("authority_tasks_config")
    private List<AuthorityTaskConfig> authorityTasksConfig;

    public List<AuthorityTaskConfig> getAuthorityTasksConfig() {
        return authorityTasksConfig;
    }

    public void setAuthorityTasksConfig(List<AuthorityTaskConfig> authorityTasksConfig) {
        this.authorityTasksConfig = authorityTasksConfig;
    }

    public static class AuthorityTaskConfig {

        @JsonProperty("category_id")
        private String categoryId;

        @JsonProperty("action_code")
        private List<String> actionCode;

        @JsonProperty("crm_category_description")
        private String crmCategoryDescription;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public List<String> getActionCode() {
            return actionCode;
        }

        public void setActionCode(List<String> actionCode) {
            this.actionCode = actionCode;
        }

        public String getCrmCategoryDescription() {
            return crmCategoryDescription;
        }

        public void setCrmCategoryDescription(String crmCategoryDescription) {
            this.crmCategoryDescription = crmCategoryDescription;
        }
    }
}

