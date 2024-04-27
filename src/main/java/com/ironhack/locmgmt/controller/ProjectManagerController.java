package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.users.ProjectManager;
import com.ironhack.locmgmt.service.ProjectManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectManagerController {
    @Autowired
    private ProjectManagerService projectManagerService;

    @GetMapping("/project-managers")
    public ResponseEntity<List<ProjectManager>> getAllClients() {
        List<ProjectManager> projectManagers = projectManagerService.getAllProjectManagers();
        return new ResponseEntity<>(projectManagers, HttpStatus.OK);
    }

    @GetMapping("/project-manager/{id}")
    public ResponseEntity<ProjectManager> getProjectManagerById(@PathVariable Long id) {
        ProjectManager projectManager = projectManagerService.getProjectManagerById(id);
        if (projectManager != null) {
            return new ResponseEntity<>(projectManager, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/saveProject-manager")
    public ResponseEntity<ProjectManager> createProjectManager(@RequestBody ProjectManager projectManager) {
        ProjectManager createdProjectManager = projectManagerService.createProjectManager(projectManager);
        return new ResponseEntity<>(createdProjectManager, HttpStatus.CREATED);
    }

    @PutMapping("/updateProject-manager/{id}")
    public ResponseEntity<ProjectManager> updateProjectManager(@PathVariable Long id, @RequestBody ProjectManager projectManager) {
        ProjectManager updatedProjectManager = projectManagerService.updateProjectManager(id, projectManager);
        return new ResponseEntity<>(updatedProjectManager, HttpStatus.OK);
    }

    @DeleteMapping("/deleteProject-manager/{id}")
    public ResponseEntity<?> deleteProjectManager(@PathVariable Long id) {
        projectManagerService.deleteProjectManager(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
