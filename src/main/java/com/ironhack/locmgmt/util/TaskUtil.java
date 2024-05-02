package com.ironhack.locmgmt.util;

import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.Status;

import java.time.Duration;
import java.util.Date;

public class TaskUtil {
    public static void updateTaskDates(Task task) {
        if (task.getTaskStatus() == Status.STARTED && task.getStartDate() == null) {
            task.setStartDate(new Date()); //Set the start date to the current date/time
        } else if (task.getTaskStatus() == Status.FINISHED && task.getEndDate() == null) {
            task.setEndDate(new Date()); //Set the end date to the current date/time
        } else if (task.getTaskStatus() == Status.NOT_STARTED) {
            task.setStartDate(null); //Set start date to null when task is not started
            task.setEndDate(null);
            task.setTimeRemaining(null);
            task.setDeadline(null);
        }

        if (task.getTaskStatus() == Status.STARTED && task.getDeadline() != null) {
            updateTimeRemaining(task);
        }
    }

    public static void updateTimeRemaining(Task task) {
        if (task.getDeadline() != null && task.getStartDate() != null) {
                long diffInMillies = Math.abs(task.getDeadline().getTime() - task.getStartDate().getTime());
                Duration duration = Duration.ofMillis(diffInMillies);
                task.setTimeRemaining(duration);
        }
    }
}