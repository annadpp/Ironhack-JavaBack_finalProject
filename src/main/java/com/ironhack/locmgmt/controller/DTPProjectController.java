package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.enums.DTPTechnology;
import com.ironhack.locmgmt.model.projects.DTPProject;
import com.ironhack.locmgmt.service.DTPProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/dtp-projects")
public class DTPProjectController {

    @Autowired
    private DTPProjectService dtpProjectService;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<DTPProject> getAllDTPProjects() {
        return dtpProjectService.getAllDTPProjects();
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DTPProject getLinguisticProjectById(@PathVariable Long id) {
        DTPProject dtpProject = dtpProjectService.getDTPProjectById(id);
        if (dtpProject == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DTP project not found");
        }
        return dtpProject;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public DTPProject createLinguisticProject(@RequestBody DTPProject dtpProject) {
        return dtpProjectService.createDTPProject(dtpProject);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DTPProject updateDTPProject(@PathVariable Long id, @RequestBody DTPProject DTPProject) {
        return dtpProjectService.updateDTPProject(id, DTPProject);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDTPProject(@PathVariable Long id) {
        dtpProjectService.deleteDTPProject(id);
    }

    @GetMapping("/get/byDTPTechnology/{dtpTechnology}")
    @ResponseStatus(HttpStatus.OK)
    public List<DTPProject> getProjectsByDTPTechnology(@PathVariable DTPTechnology dtpTechnology) {
        return dtpProjectService.findByDtpTechnology(dtpTechnology);
    }
}

