package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.BillingStatus;
import com.ironhack.locmgmt.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task client = taskService.getTaskById(id);
        if (client != null) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/saveTask")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/updateTask/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/updateTaskBillingStatus/{taskId}")
    public ResponseEntity<Task> updateTaskBillingStatus(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        Task task = taskService.getTaskById(taskId); //Get task by ID
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //If not found, 404
        }
        //Update task billing status
        task.setBillingStatus(updatedTask.getBillingStatus());
        Task savedTask = taskService.updateTask(taskId, task); //Save changes in db
        return new ResponseEntity<>(savedTask, HttpStatus.OK); //Updated task back with 200 OK
    }
}
