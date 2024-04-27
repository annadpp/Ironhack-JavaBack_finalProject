package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.projects.LinguisticProject;
import com.ironhack.locmgmt.service.LinguisticProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LinguisticProjectController {

    @Autowired
    private LinguisticProjectService linguisticProjectService;

    @GetMapping("/linguistic-projects")
    public ResponseEntity<List<LinguisticProject>> getAllClients() {
        List<LinguisticProject> linguisticProjects = linguisticProjectService.getAllLinguisticProjects();
        return new ResponseEntity<>(linguisticProjects, HttpStatus.OK);
    }

    @GetMapping("/linguistic-project/{id}")
    public ResponseEntity<LinguisticProject> getLinguisticProjectById(@PathVariable Long id) {
        LinguisticProject linguisticProject = linguisticProjectService.getLinguisticProjectById(id);
        if (linguisticProject != null) {
            return new ResponseEntity<>(linguisticProject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/saveLinguistic-project")
    public ResponseEntity<LinguisticProject> createLinguisticProject(@RequestBody LinguisticProject linguisticProject) {
        LinguisticProject createdLinguisticProject = linguisticProjectService.createLinguisticProject(linguisticProject);
        return new ResponseEntity<>(createdLinguisticProject, HttpStatus.CREATED);
    }

    @PutMapping("/updateLinguistic-project/{id}")
    public ResponseEntity<LinguisticProject> updateLinguisticProject(@PathVariable Long id, @RequestBody LinguisticProject linguisticProject) {
        LinguisticProject updatedLinguisticProject = linguisticProjectService.updateLinguisticProject(id, linguisticProject);
        return new ResponseEntity<>(updatedLinguisticProject, HttpStatus.OK);
    }

    @DeleteMapping("/deleteLinguistic-project/{id}")
    public ResponseEntity<?> deleteLinguisticProject(@PathVariable Long id) {
        linguisticProjectService.deleteLinguisticProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

