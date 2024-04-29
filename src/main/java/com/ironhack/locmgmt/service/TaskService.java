package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.repository.TaskRepository;
import com.ironhack.locmgmt.model.enums.BillingStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, Task taskDetails) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        existingTask.setName(taskDetails.getName());
        existingTask.setDescription(taskDetails.getDescription());
        existingTask.setDeadline(taskDetails.getDeadline());
        existingTask.setTimeRemaining(taskDetails.getTimeRemaining());
        existingTask.setTaskStatus(taskDetails.getTaskStatus());
        existingTask.setProjectType(taskDetails.getProjectType());
        existingTask.setStartDate(taskDetails.getStartDate());
/*
        existingTask.setEndDate(taskDetails.getEndDate());
*/
/*
        existingTask.setBillingStatus(taskDetails.getBillingStatus());
*/
        existingTask.setSourceLanguage(taskDetails.getSourceLanguage());
        existingTask.setTargetLanguage(taskDetails.getTargetLanguage());
        existingTask.setLinguisticTechnology(taskDetails.getLinguisticTechnology());
        existingTask.setDtpTechnology(taskDetails.getDtpTechnology());
        existingTask.setProjectManager(taskDetails.getProjectManager());
        existingTask.setProject(taskDetails.getProject());
        existingTask.setLinguist(taskDetails.getLinguist());
        existingTask.setUser(taskDetails.getUser());

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public void updateBillingStatus(Long taskId, String billingStatus) {
        taskRepository.updateBillingStatus(taskId, BillingStatus.valueOf(billingStatus));
    }
}