package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.projects.Project;
import com.ironhack.locmgmt.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<Project> getAllProjects() {
       return projectService.getAllProjects();
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Project getUserById(@PathVariable Long id) {
        Project user = projectService.getProjectById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        }
        return user;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    @GetMapping("/get/byTotalBudgetGreaterThan/{totalBudget}")
    @ResponseStatus(HttpStatus.OK)
    public List<Project> findByTotalBudgetGreaterThan(@PathVariable BigDecimal totalBudget) {
        return projectService.findByTotalBudgetGreaterThan(totalBudget);
    }

    @GetMapping("/get/byTotalBudgetLessThan/{totalBudget}")
    @ResponseStatus(HttpStatus.OK)
    public List<Project> findByTotalBudgetLessThan(@PathVariable BigDecimal totalBudget) {
        return projectService.findByTotalBudgetLessThan(totalBudget);
    }
}
