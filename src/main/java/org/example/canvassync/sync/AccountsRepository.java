package org.example.canvassync.sync;

import org.example.canvassync.canvasclient.AccountEntity;
import org.example.canvassync.db.tables.records.CanvasAccountsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.jooq.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountsRepository {

    private final DSLContext jooq;

    public AccountsRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public static CanvasAccountsRecord from(AccountEntity entity) {
        return new CanvasAccountsRecord(
                entity.id(),
                entity.name(),
                entity.workflowState(),
                entity.parentAccountId(),
                entity.rootAccountId(),
                entity.uuid(),
                entity.defaultStorageQuotaMb(),
                entity.defaultUserStorageQuotaMb(),
                entity.defaultGroupStorageQuotaMb(),
                entity.defaultTimeZone(),
                entity.sisAccountId(),
                entity.integrationId(),
                entity.sisImportId(),
                entity.ltiGuid(),
                null
        );
    }

    public void insertOrUpdateBatch(List<AccountEntity> entity) {
        jooq.batch(
                entity.stream()
                        .map(AccountsRepository::from)
                        .map(this::prepareQuery)
                        .toList()
        ).execute();
    }

    @NotNull
    private InsertOnDuplicateSetMoreStep<CanvasAccountsRecord> prepareQuery(CanvasAccountsRecord canvasAccountsRecord) {
        return jooq.insertInto(org.example.canvassync.db.tables.CanvasAccounts.CANVAS_ACCOUNTS)
                .set(canvasAccountsRecord)
                .onDuplicateKeyUpdate()
                .set(canvasAccountsRecord);
    }
}
