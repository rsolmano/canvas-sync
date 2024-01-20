package org.example.canvassync.sync;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.canvassync.oauth.CanvasProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CanvasSyncController {

    private final CanvasSyncService canvasSyncService;

    public CanvasSyncController(CanvasSyncService canvasSyncService) {
        this.canvasSyncService = canvasSyncService;
    }

    @PostMapping(value = "/sync", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SyncResponse> sync() {
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
