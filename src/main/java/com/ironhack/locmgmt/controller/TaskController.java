package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.dto.TaskDTO;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.BillingStatus;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/get/project-manager")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTasksPM() {
        return taskService.getAllTasks();
    }

    @GetMapping("/get/linguist")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDTO> getAllTasksLinguist() {
        return taskService.getAllTasksDTO();
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rate not found");
        } else {
            return task;
        }
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/get/byDeadlineBetween")
    public List<Task> getTasksByDeadlineBetween(@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam Date start,
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam Date end) {
        return taskService.findTasksByDeadlineBetween(start, end);
    }

    @GetMapping("/get/byTimeRemaining")
    public List<Task> getTasksByTimeRemainingLessThan(@RequestParam Duration duration) {
        return taskService.findTasksByTimeRemainingLessThan(duration);
    }

    @GetMapping("/get/byProjectType")
    public List<Task> getTasksByProjectType(@RequestParam ProjectType projectType) {
        return taskService.findTasksByProjectType(projectType);
    }

    @GetMapping("/get/byBillingStatus")
    public List<Task> getTasksByBillingStatus(@RequestParam BillingStatus billingStatus) {
        return taskService.findTasksByBillingStatus(billingStatus);
    }

     @GetMapping("/get/byPagesGreaterThan/{pages}")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getProjectsByPagesGreaterThan(@PathVariable Integer pages) {
        return taskService.findByPagesGreaterThan(pages);
    }

    @GetMapping("/get/byPagesLessThan/{pages}")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getProjectsByPagesLessThan(@PathVariable Integer pages) {
        return taskService.findByPagesLessThan(pages);
    }

    @GetMapping("/get/byNWGT-FWGT")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> findByNewWordsGreaterThanAndFuzzyWordsGreaterThan(@RequestParam Integer newWords, @RequestParam Integer fuzzyWords) {
        return taskService.findByNewWordsGreaterThanAndFuzzyWordsGreaterThan(newWords, fuzzyWords);
    }

    @GetMapping("/get/byNWLT-FWLT")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> findByNewWordsLessThanAndFuzzyWordsLessThan(@RequestParam Integer newWords, @RequestParam Integer fuzzyWords) {
        return taskService.findByNewWordsLessThanAndFuzzyWordsLessThan(newWords, fuzzyWords);
    }

    @GetMapping("/get/byTotalWordsGreaterThan/{totalWords}")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> findByTotalWordsGreaterThan(@PathVariable Integer totalWords) {
        return taskService.findByTotalWordsGreaterThan(totalWords);
    }

    @GetMapping("/get/byTotalWordsLessThan/{totalWords}")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> findByTotalWordsLessThan(@PathVariable Integer totalWords) {
        return taskService.findByTotalWordsLessThan(totalWords);
    }
}
