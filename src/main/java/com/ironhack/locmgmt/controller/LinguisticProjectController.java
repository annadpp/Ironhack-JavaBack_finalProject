package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.projects.LinguisticProject;
import com.ironhack.locmgmt.service.LinguisticProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/linguistic-projects")
public class LinguisticProjectController {

    @Autowired
    private LinguisticProjectService linguisticProjectService;

    @GetMapping("/get")
    public List<LinguisticProject> getAllClients() {
        return linguisticProjectService.getAllLinguisticProjects();
    }

    @GetMapping("/get/{id}")
    public LinguisticProject getLinguisticProjectById(@PathVariable Long id) {
        LinguisticProject linguisticProject = linguisticProjectService.getLinguisticProjectById(id);
        if (linguisticProject == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Linguistic project not found");
        }
        return linguisticProject;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public LinguisticProject createLinguisticProject(@RequestBody LinguisticProject linguisticProject) {
        return linguisticProjectService.createLinguisticProject(linguisticProject);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LinguisticProject updateLinguisticProject(@PathVariable Long id, @RequestBody LinguisticProject linguisticProject) {
        return linguisticProjectService.updateLinguisticProject(id, linguisticProject);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLinguisticProject(@PathVariable Long id) {
        linguisticProjectService.deleteLinguisticProject(id);
    }
}

