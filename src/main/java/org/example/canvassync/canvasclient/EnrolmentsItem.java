package org.example.canvassync.canvasclient;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EnrolmentsItem(

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
