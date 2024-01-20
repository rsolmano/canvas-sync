package org.example.canvassync.canvasclient;


import java.util.List;

public interface CanvasClient {
    List<AccountEntity> getAccounts();

    List<CourseEntity> getCourses();
}
