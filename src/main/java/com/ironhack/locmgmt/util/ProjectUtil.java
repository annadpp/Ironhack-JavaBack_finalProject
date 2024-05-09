package com.ironhack.locmgmt.util;

/*
import com.ironhack.locmgmt.model.Task;
*/
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.Status;
import com.ironhack.locmgmt.model.projects.LinguisticProject;
import com.ironhack.locmgmt.model.projects.Project;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Date;

public class ProjectUtil {
    public static void updateProjectDates(Project project) {
        if (project.getProjectStatus() == Status.STARTED && project.getStartDate() == null) {
            project.setStartDate(new Date()); //Set the start date to the current date/time
        } else if (project.getProjectStatus() == Status.FINISHED && project.getEndDate() == null) {
            project.setEndDate(new Date()); //Set the end date to the current date/time
        } else if (project.getProjectStatus() == Status.NOT_STARTED) {
            project.setStartDate(null); //Set start date to null when task is not started
            project.setEndDate(null);
            project.setTimeRemaining(null);
            project.setDeadline(null);
        }

        if (project.getProjectStatus() == Status.STARTED && project.getDeadline() != null) {
            updateTimeRemaining(project);
        }
    }

    public static void updateTimeRemaining(Project project) {
        if (project.getDeadline() != null && project.getStartDate() != null) {
            long diffInMillies = Math.abs(project.getDeadline().getTime() - project.getStartDate().getTime());
            Duration duration = Duration.ofMillis(diffInMillies);
            project.setTimeRemaining(duration);
        }
    }

    public static void calculateMargin(Project project) {
        BigDecimal totalTaskCost = BigDecimal.ZERO;
        for (Task task : project.getTasks()) {
            if (task.getTaskCost() != null) {
                totalTaskCost = totalTaskCost.add(task.getTaskCost());
            }
        }

        BigDecimal margin = project.getTotalBudget().subtract(totalTaskCost);
        project.setMargin(margin);
    }

    public static void calculateMarginPercentage(Project project) {
        if (project.getTotalBudget().compareTo(BigDecimal.ZERO) == 0) {
            project.setMarginPercentage(BigDecimal.ZERO);
        } else {
            BigDecimal marginPercentage = project.getMargin().divide(project.getTotalBudget(), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
            project.setMarginPercentage(marginPercentage);
        }
    }
}