package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.projects.LinguisticProject;
import com.ironhack.locmgmt.service.LinguisticProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
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

    /*@GetMapping("/get/byNWGT-FWGT")
    @ResponseStatus(HttpStatus.OK)
    public List<LinguisticProject> findByNewWordsGreaterThanAndFuzzyWordsGreaterThan(@RequestParam Integer newWords, @RequestParam Integer fuzzyWords) {
        return linguisticProjectService.findByNewWordsGreaterThanAndFuzzyWordsGreaterThan(newWords, fuzzyWords);
    }

    @GetMapping("/get/byNWLT-FWLT")
    @ResponseStatus(HttpStatus.OK)
    public List<LinguisticProject> findByNewWordsLessThanAndFuzzyWordsLessThan(@RequestParam Integer newWords, @RequestParam Integer fuzzyWords) {
        return linguisticProjectService.findByNewWordsLessThanAndFuzzyWordsLessThan(newWords, fuzzyWords);
    }

    @GetMapping("/get/byTotalWordsGreaterThan/{totalWords}")
    @ResponseStatus(HttpStatus.OK)
    public List<LinguisticProject> findByTotalWordsGreaterThan(@PathVariable Integer totalWords) {
        return linguisticProjectService.findByTotalWordsGreaterThan(totalWords);
    }

    @GetMapping("/get/byTotalWordsLessThan/{totalWords}")
    @ResponseStatus(HttpStatus.OK)
    public List<LinguisticProject> findByTotalWordsLessThan(@PathVariable Integer totalWords) {
        return linguisticProjectService.findByTotalWordsLessThan(totalWords);
    }

    @GetMapping("/get/byTotalBudgetGreaterThan/{totalBudget}")
    @ResponseStatus(HttpStatus.OK)
    public List<LinguisticProject> findByTotalBudgetGreaterThan(@PathVariable BigDecimal totalBudget) {
        return linguisticProjectService.findByTotalBudgetGreaterThan(totalBudget);
    }

    @GetMapping("/get/byTotalBudgetLessThan/{totalBudget}")
    @ResponseStatus(HttpStatus.OK)
    public List<LinguisticProject> findByTotalBudgetLessThan(@PathVariable BigDecimal totalBudget) {
        return linguisticProjectService.findByTotalBudgetLessThan(totalBudget);
    }
*/
    @GetMapping("/get/byLinguisticTechnology/{linguisticTechnology}")
    @ResponseStatus(HttpStatus.OK)
    public List<LinguisticProject> findByLinguisticTechnology(@PathVariable LinguisticTechnology linguisticTechnology) {
        return linguisticProjectService.findByLinguisticTechnology(linguisticTechnology);
    }
}

