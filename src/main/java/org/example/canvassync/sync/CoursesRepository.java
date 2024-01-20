package org.example.canvassync.sync;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.canvassync.canvasclient.CourseEntity;
import org.example.canvassync.db.tables.records.CanvasCoursesRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.jooq.JSONB;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.canvassync.db.tables.CanvasCourses.CANVAS_COURSES;

@Repository
public class CoursesRepository {

    private final DSLContext jooq;
    private final ObjectMapper objectMapper;

    public CoursesRepository(DSLContext jooq, ObjectMapper objectMapper) {
        this.jooq = jooq;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public static CanvasCoursesRecord from(CourseEntity entity, ObjectMapper objectMapper) {
        return new CanvasCoursesRecord(
                entity.id(),
                entity.name(),
                entity.workflowState(),
                entity.accountId(),
                entity.rootAccountId(),
                entity.uuid(),
                entity.courseCode(),
                entity.sisCourseId(),
                entity.integrationId(),
                entity.sisImportId(),
                entity.defaultView(),
                entity.license(),
                entity.startAt(),
                entity.endAt(),
                entity.publicSyllabus(),
                entity.publicSyllabusToAuth(),
                entity.storageQuotaMb(),
                entity.isPublic(),
                entity.isPublicToAuthUsers(),
                entity.hideFinalGrades(),
                entity.applyAssignmentGroupWeights(),
                JSONB.jsonb(objectMapper.writeValueAsString(entity.calendar())),
                entity.timeZone(),
                entity.blueprint(),
                entity.enrollmentTermId(),
                entity.gradingStandardId(),
                entity.createdAt(),
                entity.restrictEnrollmentsToCourseDates(),
                entity.overriddenCourseVisibility(),
                null
        );
    }

    public void insertOrUpdateBatch(List<CourseEntity> entity) {
        jooq.batch(
                entity.stream()
                        .map(e -> from(e, objectMapper))
                        .map(this::prepareQuery)
                        .toList()
        ).execute();
    }

    @NotNull
    private InsertOnDuplicateSetMoreStep<CanvasCoursesRecord> prepareQuery(CanvasCoursesRecord record) {
        return jooq.insertInto(CANVAS_COURSES)
                .set(record)
                .onDuplicateKeyUpdate()
                .set(record);
    }
}
