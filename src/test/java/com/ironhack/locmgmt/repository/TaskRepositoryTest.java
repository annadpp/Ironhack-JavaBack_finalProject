package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.enums.BillingStatus;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void findByDeadlineBetween() {
        Date currentDate = new Date();
        //Calculate the start and end dates for the deadline range
        long oneDayInMillis = 24 * 60 * 60 * 1000; // 1 day in milliseconds
        Date start = new Date(currentDate.getTime() + oneDayInMillis); // Add 1 day to the current date
        Date end = new Date(start.getTime() + (2 * oneDayInMillis)); // Add 2 days to the start date

        Task task1 = new Task("Task 1", "Description 1", start, Duration.ofHours(12), null, ProjectType.TRANSLATION, start, null, BillingStatus.INVOICED, Languages.ENGLISH, Languages.SPANISH, LinguisticTechnology.EMAIL, null, null, null);
        Task task2 = new Task("Task 2", "Description 2", end, Duration.ofHours(12), null, ProjectType.TRANSLATION, end, null, BillingStatus.INVOICED, Languages.SPANISH, Languages.FRENCH, LinguisticTechnology.EMAIL, null, null, null);
        taskRepository.saveAll(Arrays.asList(task1, task2));

        List<Task> foundTasks = taskRepository.findByDeadlineBetween(start, end);

        assertEquals(2, foundTasks.size());
    }

    @Test
    void findByTimeRemainingLessThan() {
        Task task1 = new Task("Task 1", "Description 1", null, Duration.ofHours(12), null, ProjectType.TRANSLATION, null, null, BillingStatus.INVOICED, Languages.ENGLISH, Languages.SPANISH, LinguisticTechnology.EMAIL, null, null, null);
        Task task2 = new Task("Task 2", "Description 2", null, Duration.ofHours(6), null, ProjectType.TRANSLATION, null, null, BillingStatus.INVOICED, Languages.SPANISH, Languages.FRENCH, LinguisticTechnology.EMAIL, null, null, null);
        taskRepository.saveAll(Arrays.asList(task1, task2));

        List<Task> foundTasks = taskRepository.findByTimeRemainingLessThan(Duration.ofHours(10));

        assertEquals(1, foundTasks.size());
    }

    @Test
    void findByProjectType() {
        Task task1 = new Task("Task 1", "Description 1", null, null, null, ProjectType.TRANSLATION, null, null, BillingStatus.INVOICED, Languages.ENGLISH, Languages.SPANISH, LinguisticTechnology.EMAIL, null, null, null);
        Task task2 = new Task("Task 2", "Description 2", null, null, null, ProjectType.REVIEW, null, null, BillingStatus.INVOICED, Languages.SPANISH, Languages.FRENCH, LinguisticTechnology.EMAIL, null, null, null);
        taskRepository.saveAll(Arrays.asList(task1, task2));

        List<Task> foundTasks = taskRepository.findByProjectType(ProjectType.TRANSLATION);

        assertEquals(1, foundTasks.size());
    }

    @Test
    void findByBillingStatus() {
        Task task1 = new Task("Task 1", "Description 1", null, null, null, ProjectType.TRANSLATION, null, null, BillingStatus.INVOICED, Languages.ENGLISH, Languages.SPANISH, LinguisticTechnology.EMAIL, null, null, null);
        Task task2 = new Task("Task 2", "Description 2", null, null, null, ProjectType.REVIEW, null, null, BillingStatus.NOT_INVOICED, Languages.SPANISH, Languages.FRENCH, LinguisticTechnology.EMAIL, null, null, null);
        taskRepository.saveAll(Arrays.asList(task1, task2));

        List<Task> foundTasks = taskRepository.findByBillingStatus(BillingStatus.INVOICED);

        assertEquals(1, foundTasks.size());
    }

}
