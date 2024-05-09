package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.dto.TaskDTO;
import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.BillingStatus;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.Status;
import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.repository.TaskRepository;

import com.ironhack.locmgmt.util.SecurityUtil;
import com.ironhack.locmgmt.util.TaskUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ironhack.locmgmt.util.TaskUtil.updateTaskDates;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SecurityUtil securityUtil;

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

    public List<TaskDTO> getAllTasksDTO() {
        try {
            Linguist linguist = securityUtil.getCurrentLinguist(); //Get current linguist

            List<Task> tasks = taskRepository.findByLinguist(linguist); //Filter tasks per current linguist - only current linguist can access its own tasks
            if (tasks.isEmpty()) {
                throw new EmptyListException("No tasks were found for the linguist");
            }

            List<TaskDTO> taskDTOs = new ArrayList<>();
            for (Task task : tasks) {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setId(task.getId());
                taskDTO.setName(task.getName());
                taskDTO.setDescription(task.getDescription());
                taskDTO.setDeadline(task.getDeadline());
                taskDTO.setTimeRemaining(task.getTimeRemaining());
                taskDTO.setTaskStatus(task.getTaskStatus());
                taskDTO.setProjectType(task.getProjectType());
                taskDTO.setStartDate(task.getStartDate());
                taskDTO.setEndDate(task.getEndDate());
                taskDTO.setBillingStatus(task.getBillingStatus());
                taskDTO.setSourceLanguage(task.getSourceLanguage());
                taskDTO.setTargetLanguage(task.getTargetLanguage());
                taskDTO.setLinguisticTechnology(task.getLinguisticTechnology());
                taskDTO.setDtpTechnology(task.getDtpTechnology());
                taskDTO.setLinguist(task.getLinguist());
                taskDTO.setProjectManager(task.getProjectManager());
                taskDTOs.add(taskDTO);
            }

            return taskDTOs;
        } catch (DataAccessException e) {
            throw new DataRetrievalFailureException("Error while retrieving tasks for the linguist", e);
        }
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
    }

    public Task createTask(Task task) {
        //Validate task type
        if (task.getProjectType() == ProjectType.LINGUISTIC) {
            throw new IllegalArgumentException("Invalid task type. Task type cannot be LINGUISTIC.");
        }
        if (taskRepository.existsByName(task.getName())) {
            throw new IllegalArgumentException("A task with the same name already exists.");
        }

        //Sets taskStatus and billingStatus to NOT if info not passed by the user when creating task
        task.setTaskStatus(task.getTaskStatus() != null ? task.getTaskStatus() : Status.NOT_STARTED);
        task.setBillingStatus(task.getBillingStatus() != null ? task.getBillingStatus() : BillingStatus.NOT_INVOICED);

        //Update task dates, time remaining and total time
        TaskUtil.updateTaskDates(task);
        TaskUtil.updateTimeRemaining(task);
        TaskUtil.updateTotalTime(task);

        try {
            return taskRepository.save(task);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error while creating the task", e);}
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        if (taskDetails.getName() != null && !taskDetails.getName().equals(existingTask.getName())) {
            if (taskRepository.existsByName(taskDetails.getName())) {
                throw new IllegalArgumentException("A task with the same name already exists.");
            } else {
                existingTask.setName(taskDetails.getName());
            }
        }
        if (taskDetails.getDescription() != null) {
            existingTask.setDescription(taskDetails.getDescription());
        }
        if (taskDetails.getDeadline() != null) {
            existingTask.setDeadline(taskDetails.getDeadline());
            TaskUtil.updateTimeRemaining(existingTask);
            TaskUtil.updateTotalTime(existingTask);
        }
        if (taskDetails.getTaskStatus() != null) {
            existingTask.setTaskStatus(taskDetails.getTaskStatus());
            updateTaskDates(existingTask);
        }
        if (taskDetails.getProjectType() != null) {
            if (taskDetails.getProjectType() == ProjectType.LINGUISTIC) {
                throw new IllegalArgumentException("Invalid task type. Task type cannot be LINGUISTIC.");
            } else {
                existingTask.setProjectType(taskDetails.getProjectType());
            }
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
        //Add project when updating task
        if (taskDetails.getProject() != null) {
            existingTask.setProject(taskDetails.getProject());
        }
        //Add linguist when updating task
        if (taskDetails.getLinguist() != null) {
            existingTask.setLinguist(taskDetails.getLinguist());
        }

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