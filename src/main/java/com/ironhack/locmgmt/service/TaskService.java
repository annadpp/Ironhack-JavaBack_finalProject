package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.BillingStatus;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.TaskStatus;
import com.ironhack.locmgmt.repository.TaskRepository;

import com.ironhack.locmgmt.util.TaskUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.List;

import static com.ironhack.locmgmt.util.TaskUtil.updateTaskDates;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        try {
            List<Task> rates = taskRepository.findAll();
            if (rates.isEmpty()) {
                throw new EmptyListException("No tasks were found");
            }
            return rates;
        } catch (DataAccessException e) {
            throw new DataRetrievalFailureException("Error while retrieving all tasks", e);
        }
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
    }

    public Task createTask(Task task) {
        //Sets taskStatus and billingStatus to NOT if info not passed by the user when creating task
        task.setTaskStatus(task.getTaskStatus() != null ? task.getTaskStatus() : TaskStatus.NOT_STARTED);
        task.setBillingStatus(task.getBillingStatus() != null ? task.getBillingStatus() : BillingStatus.NOT_INVOICED);

        //Update task dates and time remaining
        TaskUtil.updateTaskDates(task);
        TaskUtil.updateTimeRemaining(task);

        try {
            return taskRepository.save(task);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error while creating the task", e);
        }
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        if (taskDetails.getName() != null) {
            existingTask.setName(taskDetails.getName());
        }
        if (taskDetails.getDescription() != null) {
            existingTask.setDescription(taskDetails.getDescription());
        }
        if (taskDetails.getDeadline() != null) {
            existingTask.setDeadline(taskDetails.getDeadline());
            TaskUtil.updateTimeRemaining(existingTask);
        }
        if (taskDetails.getTaskStatus() != null) {
            existingTask.setTaskStatus(taskDetails.getTaskStatus());
            updateTaskDates(existingTask);
        }
        if (taskDetails.getProjectType() != null) {
            existingTask.setProjectType(taskDetails.getProjectType());
        }
        if (taskDetails.getBillingStatus() != null) {
            existingTask.setBillingStatus(taskDetails.getBillingStatus());
        }
        if (taskDetails.getSourceLanguage() != null) {
            existingTask.setSourceLanguage(taskDetails.getSourceLanguage());
        }
        if (taskDetails.getTargetLanguage() != null) {
            existingTask.setTargetLanguage(taskDetails.getTargetLanguage());
        }
        if (taskDetails.getLinguisticTechnology() != null) {
            existingTask.setLinguisticTechnology(taskDetails.getLinguisticTechnology());
        }
        if (taskDetails.getDtpTechnology() != null) {
            existingTask.setDtpTechnology(taskDetails.getDtpTechnology());
        }
        /*if (taskDetails.getProjectManager() != null) {
            existingTask.setProjectManager(taskDetails.getProjectManager());
        }
        if (taskDetails.getProject() != null) {
            existingTask.setProject(taskDetails.getProject());
        }
        if (taskDetails.getLinguist() != null) {
            existingTask.setLinguist(taskDetails.getLinguist());
        }*/

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        try {
            taskRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Task not found with id: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error deleting task with id: " + id);
        }
    }

    public List<Task> findTasksByDeadlineBetween(Date start, Date end) {
        List<Task> tasks = taskRepository.findByDeadlineBetween(start, end);
        if (tasks.isEmpty()) {
            throw new EmptyListException("No tasks found with the given deadline range");
        }
        return tasks;
    }

    public List<Task> findTasksByTimeRemainingLessThan(Duration duration) {
        if (duration.isNegative() || duration.isZero()) {
            throw new InvalidDataAccessApiUsageException("Duration must be a positive value");
        }
        List<Task> tasks = taskRepository.findByTimeRemainingLessThan(duration);
        return tasks;
    }

    public List<Task> findTasksByProjectType(ProjectType projectType) {
        List<Task> tasks = taskRepository.findByProjectType(projectType);
        if (tasks.isEmpty()) {
            throw new EmptyListException("No tasks found for the given project type");
        }
        return tasks;
    }

    public List<Task> findTasksByBillingStatus(BillingStatus billingStatus) {
        List<Task> tasks = taskRepository.findByBillingStatus(billingStatus);
        if (tasks.isEmpty()) {
            throw new EmptyListException("No tasks found for the given billing status");
        }
        return tasks;
    }
}