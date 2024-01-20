package org.example.canvassync.canvasclient;

import org.example.canvassync.oauth.CanvasHost;

import java.util.List;

public interface CanvasClient {
    List<AccountEntity> getAccounts(CanvasHost canvasHost);

    List<CourseEntity> getCourses(CanvasHost canvasHost);
}
