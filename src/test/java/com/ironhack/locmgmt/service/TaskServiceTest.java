package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.BillingStatus;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.Status;
import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.enums.DTPTechnology;
import com.ironhack.locmgmt.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;

import jakarta.persistence.EntityNotFoundException;

import java.time.Duration;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void createTask_Success() {
        Task newTask = new Task("New Task", "Description", new Date(), Duration.ofHours(8), Status.NOT_STARTED, ProjectType.TRANSLATION, null, null, BillingStatus.NOT_INVOICED, Languages.ENGLISH, Languages.GERMAN, LinguisticTechnology.EMAIL, null, null, null);

        Task createdTask = taskService.createTask(newTask);

        assertNotNull(createdTask);
        assertNotNull(createdTask.getId());
        assertEquals(newTask.getName(), createdTask.getName());
    }

    @Test
    void deleteTask_Success() {
        Date futureDate = new Date(System.currentTimeMillis() + 86400000); //Adding one day (in ms) to make it a future date

        Task newTask = new Task("New Task", "Description", futureDate, Duration.ofHours(8), Status.NOT_STARTED, ProjectType.TRANSLATION, null, null, BillingStatus.NOT_INVOICED, Languages.ENGLISH, Languages.GERMAN, LinguisticTechnology.EMAIL, null, null, null);
        Task savedTask = taskRepository.save(newTask);

        taskService.deleteTask(savedTask.getId());

        assertThrows(EntityNotFoundException.class, () -> taskService.getTaskById(savedTask.getId()));
    }

    @Test
    void findTasksByProjectType_Success() {
        Date futureDate = new Date(System.currentTimeMillis() + 86400000); //Adding one day (in ms) to make it a future date
        Task newTask = new Task("New Task", "Description", futureDate, Duration.ofHours(8), Status.NOT_STARTED, ProjectType.TRANSLATION, null, null, BillingStatus.NOT_INVOICED, Languages.ENGLISH, Languages.GERMAN, LinguisticTechnology.EMAIL, null, null, null);
        taskRepository.save(newTask);

        List<Task> tasks = taskService.findTasksByProjectType(ProjectType.TRANSLATION);

        assertFalse(tasks.isEmpty());
        assertTrue(tasks.stream().allMatch(task -> task.getProjectType().equals(ProjectType.TRANSLATION)));
    }

}
