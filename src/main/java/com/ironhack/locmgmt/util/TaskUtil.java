package com.ironhack.locmgmt.util;

import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.TaskStatus;

import java.time.Duration;
import java.util.Date;

public class TaskUtil {

    public void updateDatesAndTimeRemaining(Task task, TaskStatus newStatus) {
        if (newStatus == TaskStatus.STARTED) {
            task.setStartDate(new Date());
        } else if (newStatus == TaskStatus.FINISHED) {
            task.setEndDate(new Date());
        }

        // Update timeRemaining
        if (task.getStartDate() != null && task.getEndDate() == null) {
            task.setTimeRemaining(Duration.between(task.getStartDate().toInstant(), task.getDeadline().toInstant()));
        } else {
            task.setTimeRemaining(null);
        }
    }
}
