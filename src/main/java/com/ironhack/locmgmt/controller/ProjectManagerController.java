package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.users.ProjectManager;
import com.ironhack.locmgmt.service.ProjectManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/project-managers")
public class ProjectManagerController {
    @Autowired
    private ProjectManagerService projectManagerService;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectManager> getAllClients() {
        return projectManagerService.getAllProjectManagers();
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectManager getProjectManagerById(@PathVariable Long id) {
        ProjectManager projectManager = projectManagerService.getProjectManagerById(id);
        if (projectManager == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project manager not found");
        }
        return projectManager;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectManager createProjectManager(@RequestBody ProjectManager projectManager) {
        return projectManagerService.createProjectManager(projectManager);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectManager updateProjectManager(@PathVariable Long id, @RequestBody ProjectManager projectManager) {
        return projectManagerService.updateProjectManager(id, projectManager);
    }

    /*Fix error*/
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProjectManager(@PathVariable Long id) {
         projectManagerService.deleteProjectManager(id);
    }

    @GetMapping("/get/bySpokenLanguage")
    public List<ProjectManager> getProjectManagersBySpokenLanguage(@RequestParam Languages language) {
        return projectManagerService.findProjectManagersBySpokenLanguage(language);
    }

    @GetMapping("/get/byProjectType")
    public List<ProjectManager> getProjectManagersByProjectType(@RequestParam ProjectType projectType) {
        return projectManagerService.findProjectManagersByProjectType(projectType);
    }
}
