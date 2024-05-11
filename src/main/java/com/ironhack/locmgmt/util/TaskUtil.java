package com.ironhack.locmgmt.util;

import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.Status;
import com.ironhack.locmgmt.model.projects.LinguisticProject;
import com.ironhack.locmgmt.model.projects.Project;
import com.ironhack.locmgmt.model.users.Linguist;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class TaskUtil {
    public static void updateTaskDates(Task task) {
        if (task.getTaskStatus() == Status.STARTED && task.getStartDate() == null) {
            task.setStartDate(new Date()); //Sets the start date to the current date/time
        } else if (task.getTaskStatus() == Status.FINISHED && task.getEndDate() == null) {
            task.setEndDate(new Date()); //Sets the end date to the current date/time
        } else if (task.getTaskStatus() == Status.NOT_STARTED) {
            task.setStartDate(null); //Sets start date to null when task is not started
            task.setEndDate(null);
            task.setTimeRemaining(null);
            task.setDeadline(null);
        }

        if (task.getTaskStatus() == Status.STARTED && task.getDeadline() != null) {
            updateTotalTime(task);
        }
    }

    public static void updateTotalTime(Task task) {
        if (task.getDeadline() != null && task.getStartDate() != null) {
                long diffInMillies = Math.abs(task.getDeadline().getTime() - task.getStartDate().getTime());
                Duration duration = Duration.ofMillis(diffInMillies);
                task.setTotalTime(duration);
        }
    }

    public static void updateTimeRemaining(Task task) {
        if (task.getDeadline() != null) {
            Instant currentTime = Instant.now();
            Instant deadlineTime = task.getDeadline().toInstant();
            Duration remainingDuration = Duration.between(currentTime, deadlineTime);
            task.setTimeRemaining(remainingDuration);
        }
    }

    public static void calculateTotalWords(Task task) {
        Integer newWords = task.getNewWords() != null ? task.getNewWords() : 0;
        Integer fuzzyWords = task.getFuzzyWords() != null ? task.getFuzzyWords() : 0;
        task.setTotalWords(newWords + fuzzyWords);
    }

    public static void calculateTaskCost(Task task) {
        if (!task.getProjectType().equals(ProjectType.DTP) && task.getTotalWords() != null) {
            Linguist linguist = task.getLinguist();
            BigDecimal wordPrice = null;
            for (Rate rate : linguist.getRates()) {
                if (rate.getSourceLanguage().equals(task.getSourceLanguage()) && rate.getTargetLanguage().equals(task.getTargetLanguage()) && rate.getProjectType() != ProjectType.DTP) {
                    wordPrice = rate.getWordPrice();
                    break;
                }
            }

            if (wordPrice != null) {
                BigDecimal totalCost = wordPrice.multiply(BigDecimal.valueOf(task.getTotalWords()));
                task.setTaskCost(totalCost);
            } else {
                System.out.println("No se encontró una tarifa para el par de idiomas correspondiente");
            }
        } else {
            if (task.getProjectType().equals(ProjectType.DTP) && task.getPages() != null) {
                Linguist linguist = task.getLinguist();
                BigDecimal pagePrice = null;
                for (Rate rate : linguist.getRates()) {
                    if (rate.getSourceLanguage().equals(task.getSourceLanguage()) && rate.getTargetLanguage().equals(task.getTargetLanguage()) && rate.getProjectType() == ProjectType.DTP) {
                        pagePrice = rate.getPagePrice();
                        break;
                    }
                }

                if (pagePrice != null) {
                    BigDecimal totalCost = pagePrice.multiply(BigDecimal.valueOf(task.getPages()));
                    task.setTaskCost(totalCost);
                } else {
                    System.out.println("No se encontró una tarifa para el par de idiomas correspondiente");
                }
        }
    }
}}