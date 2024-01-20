package org.example.canvassync.canvasclient;

import org.example.canvassync.oauth.CanvasHost;

import java.util.List;

public interface CanvasClient {
    public List<AccountEntity> getAccounts(CanvasHost canvasHost);
    public List<CourseEntity> getCourses(CanvasHost canvasHost);
}
