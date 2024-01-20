package org.example.canvassync.sync;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.canvassync.canvasclient.AccountEntity;
import org.example.canvassync.canvasclient.CanvasClient;
import org.example.canvassync.canvasclient.CourseEntity;
import org.example.canvassync.db.tables.records.CanvasEnrollmentsRecord;
import org.example.canvassync.oauth.CanvasHost;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CanvasSyncService {

    private final CanvasClient canvasClient;
    private final AccountsRepository accountsRepository;
    private final CoursesRepository coursesRepository;
    private final EnrollmentsRepository enrollmentsRepository;

    public CanvasSyncService(CanvasClient canvasClient, AccountsRepository accountsRepository, CoursesRepository coursesRepository, EnrollmentsRepository enrollmentsRepository) {
        this.canvasClient = canvasClient;
        this.accountsRepository = accountsRepository;
        this.coursesRepository = coursesRepository;
        this.enrollmentsRepository = enrollmentsRepository;
    }

    public CanvasSyncResult sync(CanvasHost canvasHost) {
        log.info("Syncing canvas objects: {}", canvasHost);
        val accounts = syncAccounts(canvasHost);
        val courses = syncCourses(canvasHost);
        return new CanvasSyncResult(accounts.size(), courses.size());
    }

    @NotNull
    private List<CourseEntity> syncCourses(CanvasHost canvasHost) {
        List<CourseEntity> courses = canvasClient.getCourses(canvasHost);
        coursesRepository.insertOrUpdateBatch(courses);
        log.info("Synced {} courses", courses.size());

        List<CanvasEnrollmentsRecord> enrollments = courses.stream()
                .map(
                        course -> course.enrollments()
                                .stream()
                                .map(enrollment -> EnrollmentsRepository.from(enrollment, course.id()))
                                .toList()
                )
                .flatMap(List::stream)
                .toList();
        enrollmentsRepository.insertOrUpdateBatch(enrollments);
        log.info("Synced {} enrollments", enrollments.size());
        return courses;
    }

    @NotNull
    private List<AccountEntity> syncAccounts(CanvasHost canvasHost) {
        List<AccountEntity> accounts = canvasClient.getAccounts(canvasHost);
        accountsRepository.insertOrUpdateBatch(accounts);
        log.info("Synced {} accounts", accounts.size());
        return accounts;
    }


    public record CanvasSyncResult(
            long syncedAccounts,
            long syncedCourses
    ) {
    }
}
