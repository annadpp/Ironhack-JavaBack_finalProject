package com.ironhack.locmgmt.util;

import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.TaskStatus;
import com.ironhack.locmgmt.model.projects.LinguisticProject;
import com.ironhack.locmgmt.model.projects.Project;

import java.time.Duration;
import java.util.Date;

public class ProjectUtil {
    public static void updateProjectDates(Project project) {
        if (project.getProjectStatus() == TaskStatus.STARTED && project.getStartDate() == null) {
            project.setStartDate(new Date()); //Set the start date to the current date/time
        } else if (project.getProjectStatus() == TaskStatus.FINISHED && project.getEndDate() == null) {
            project.setEndDate(new Date()); //Set the end date to the current date/time
        } else if (project.getProjectStatus() == TaskStatus.NOT_STARTED) {
            project.setStartDate(null); //Set start date to null when task is not started
            project.setEndDate(null);
            project.setTimeRemaining(null);
            project.setDeadline(null);
        }

        if (project.getProjectStatus() == TaskStatus.STARTED && project.getDeadline() != null) {
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

    public static void calculateTotalWords(LinguisticProject linguisticProject) {
        Integer newWords = linguisticProject.getNewWords() != null ? linguisticProject.getNewWords() : 0;
        Integer fuzzyWords = linguisticProject.getFuzzyWords() != null ? linguisticProject.getFuzzyWords() : 0;
        linguisticProject.setTotalWords(newWords + fuzzyWords);
    }
}