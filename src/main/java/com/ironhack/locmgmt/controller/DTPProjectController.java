package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.projects.DTPProject;
import com.ironhack.locmgmt.service.DTPProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DTPProjectController {

    @Autowired
    private DTPProjectService dtpProjectService;

    @GetMapping("/dtp-projects")
    public ResponseEntity<List<DTPProject>> getAllDTPProjects() {
        List<DTPProject> dtpProjects = dtpProjectService.getAllDTPProjects();
        return new ResponseEntity<>(dtpProjects, HttpStatus.OK);
    }

    @GetMapping("/dtp-project/{id}")
    public ResponseEntity<DTPProject> getLinguisticProjectById(@PathVariable Long id) {
        DTPProject dtpProject = dtpProjectService.getDTPProjectById(id);
        if (dtpProject != null) {
            return new ResponseEntity<>(dtpProject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/saveDTP-project")
    public ResponseEntity<DTPProject> createLinguisticProject(@RequestBody DTPProject dtpProject) {
        DTPProject createdDTPProject = dtpProjectService.createDTPProject(dtpProject);
        return new ResponseEntity<>(createdDTPProject, HttpStatus.CREATED);
    }

    @PutMapping("/updateDTP-project/{id}")
    public ResponseEntity<DTPProject> updateDTPProject(@PathVariable Long id, @RequestBody DTPProject DTPProject) {
        DTPProject updatedDtpProject = dtpProjectService.updateDTPProject(id, DTPProject);
        return new ResponseEntity<>(updatedDtpProject, HttpStatus.OK);
    }

    @DeleteMapping("/deleteDTP-project/{id}")
    public ResponseEntity<?> deleteDTPProject(@PathVariable Long id) {
        dtpProjectService.deleteDTPProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

