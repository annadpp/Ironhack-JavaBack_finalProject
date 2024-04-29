package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.projects.Project;
import com.ironhack.locmgmt.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}
