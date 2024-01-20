package org.example.canvassync.canvasclient;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record CourseEntity(

        @JsonProperty("root_account_id")
        Integer rootAccountId,

        @JsonProperty("storage_quota_mb")
        Integer storageQuotaMb,

        @JsonProperty("end_at")
        LocalDateTime endAt,

        @JsonProperty("integration_id")
        Integer integrationId,

        @JsonProperty("public_syllabus_to_auth")
        Boolean publicSyllabusToAuth,

        @JsonProperty("public_syllabus")
        Boolean publicSyllabus,

        @JsonProperty("created_at")
        LocalDateTime createdAt,

        @JsonProperty("sis_course_id")
        Integer sisCourseId,

        @JsonProperty("start_at")
        LocalDateTime startAt,

        @JsonProperty("enrollment_term_id")
        Integer enrollmentTermId,

        @JsonProperty("uuid")
        String uuid,

        @JsonProperty("course_code")
        String courseCode,

        @JsonProperty("grading_standard_id")
        Integer gradingStandardId,

        @JsonProperty("workflow_state")
        String workflowState,

        @JsonProperty("id")
        Integer id,

        @JsonProperty("sis_import_id")
        Integer sisImportId,

        @JsonProperty("default_view")
        String defaultView,

        @JsonProperty("is_public_to_auth_users")
        Boolean isPublicToAuthUsers,

        @JsonProperty("calendar")
        Calendar calendar,

        @JsonProperty("apply_assignment_group_weights")
        Boolean applyAssignmentGroupWeights,

        @JsonProperty("hide_final_grades")
        Boolean hideFinalGrades,

        @JsonProperty("restrict_enrollments_to_course_dates")
        Boolean restrictEnrollmentsToCourseDates,

        @JsonProperty("time_zone")
        String timeZone,

        @JsonProperty("enrollments")
        List<EnrolmentsItem> enrollments,

        @JsonProperty("blueprint")
        Boolean blueprint,

        @JsonProperty("license")
        String license,

        @JsonProperty("account_id")
        Integer accountId,

        @JsonProperty("name")
        String name,

        @JsonProperty("is_public")
        Boolean isPublic,

        @JsonProperty("overridden_course_visibility")
        String overriddenCourseVisibility
) {
}

record Calendar(

        @JsonProperty("ics")
        String ics
) {
}