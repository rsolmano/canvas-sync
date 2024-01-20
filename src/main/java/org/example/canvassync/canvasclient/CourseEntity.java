package org.example.canvassync.canvasclient;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CourseEntity(

        @JsonProperty("root_account_id")
        Long rootAccountId,

        @JsonProperty("storage_quota_mb")
        Long storageQuotaMb,

        @JsonProperty("end_at")
        String endAt,

        @JsonProperty("integration_id")
        Long integrationId,

        @JsonProperty("public_syllabus_to_auth")
        Boolean publicSyllabusToAuth,

        @JsonProperty("public_syllabus")
        Boolean publicSyllabus,

        @JsonProperty("created_at")
        String createdAt,

        @JsonProperty("sis_course_id")
        Long sisCourseId,

        @JsonProperty("start_at")
        String startAt,

        @JsonProperty("enrollment_term_id")
        Long enrollmentTermId,

        @JsonProperty("uuid")
        String uuid,

        @JsonProperty("course_code")
        String courseCode,

        @JsonProperty("grading_standard_id")
        Long gradingStandardId,

        @JsonProperty("workflow_state")
        String workflowState,

        @JsonProperty("id")
        Long id,

        @JsonProperty("sis_import_id")
        Long sisImportId,

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
        List<EnrollmentsItem> enrollments,

        @JsonProperty("blueprint")
        Boolean blueprint,

        @JsonProperty("license")
        String license,

        @JsonProperty("account_id")
        Long accountId,

        @JsonProperty("name")
        String name,

        @JsonProperty("is_public")
        Boolean isPublic,

        @JsonProperty("overridden_course_visibility")
        String overriddenCourseVisibility
) {
}

record EnrollmentsItem(

        @JsonProperty("role")
        String role,

        @JsonProperty("enrollment_state")
        String enrollmentState,

        @JsonProperty("role_id")
        Integer roleId,

        @JsonProperty("user_id")
        Integer userId,

        @JsonProperty("type")
        String type
) {
}

record Calendar(

        @JsonProperty("ics")
        String ics
) {
}