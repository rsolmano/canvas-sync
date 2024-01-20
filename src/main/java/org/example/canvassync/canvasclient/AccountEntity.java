package org.example.canvassync.canvasclient;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountEntity(
        Integer id,
        String name,
        @JsonProperty("workflow_state")
        String workflowState,
        @JsonProperty("parent_account_id")
        Integer parentAccountId,
        @JsonProperty("root_account_id")
        Integer rootAccountId,
        String uuid,
        @JsonProperty("default_storage_quota_mb")
        Integer defaultStorageQuotaMb,
        @JsonProperty("default_user_storage_quota_mb")
        Integer defaultUserStorageQuotaMb,
        @JsonProperty("default_group_storage_quota_mb")
        Integer defaultGroupStorageQuotaMb,
        @JsonProperty("default_time_zone")
        String defaultTimeZone,
        @JsonProperty("sis_account_id")
        String sisAccountId,
        @JsonProperty("integration_id")
        String integrationId,
        @JsonProperty("sis_import_id")
        Integer sisImportId,
        @JsonProperty("lti_guid")
        String ltiGuid
) {
}
