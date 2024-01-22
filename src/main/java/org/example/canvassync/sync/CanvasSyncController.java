package org.example.canvassync.sync;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CanvasSyncController {

    private final CanvasSyncService canvasSyncService;

    @Value("${backend.api-key}")
    private String apiKey;

    public CanvasSyncController(CanvasSyncService canvasSyncService) {
        this.canvasSyncService = canvasSyncService;
    }

    @PostMapping(value = "/sync", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SyncResponse> sync(@RequestHeader(value = "X-API-KEY", required = false) String authHeader) {
        if (authHeader == null || !authHeader.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        val result = canvasSyncService.sync();
        return ResponseEntity.ok(SyncResponse.from(result));
    }


    public record SyncResponse(
            long syncedAccounts,
            long syncedCourses
    ) {
        public static SyncResponse from(CanvasSyncService.CanvasSyncResult result) {
            return new SyncResponse(result.syncedAccounts(), result.syncedCourses());
        }
    }
}
