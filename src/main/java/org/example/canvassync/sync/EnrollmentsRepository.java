package org.example.canvassync.sync;

import org.example.canvassync.canvasclient.EnrolmentsItem;
import org.example.canvassync.db.tables.records.CanvasEnrollmentsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.canvassync.db.tables.CanvasEnrollments.CANVAS_ENROLLMENTS;

@Repository
public class EnrollmentsRepository {

    private final DSLContext jooq;

    public EnrollmentsRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public static CanvasEnrollmentsRecord from(EnrolmentsItem entity, Integer courseId) {
        return new CanvasEnrollmentsRecord(
                courseId,
                entity.userId(),
                entity.role(),
                entity.enrollmentState(),
                entity.roleId(),
                entity.type(),
                null
        );
    }

    public void insertOrUpdateBatch(List<CanvasEnrollmentsRecord> entity) {
        jooq.batch(
                entity.stream()
                        .map(this::prepareQuery)
                        .toList()
        ).execute();
    }

    public List<CanvasEnrollmentsRecord> getAll() {
        return jooq.selectFrom(CANVAS_ENROLLMENTS)
                .fetchInto(CanvasEnrollmentsRecord.class);
    }

    @NotNull
    private InsertOnDuplicateSetMoreStep<CanvasEnrollmentsRecord> prepareQuery(CanvasEnrollmentsRecord record) {
        return jooq.insertInto(CANVAS_ENROLLMENTS)
                .set(record)
                .onDuplicateKeyUpdate()
                .set(record);
    }


}
