package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.enums.DTPTechnology;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.service.LinguistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/linguists")
public class LinguistController {
    @Autowired
    private LinguistService linguistService;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<Linguist> getAllClients() {
        return linguistService.getAllLinguists();
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Linguist getLinguistById(@PathVariable Long id) {
        Linguist linguist = linguistService.getLinguistById(id);
        if (linguist == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Linguist not found");
        }
        return linguist;
    }

    /*@PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Linguist createLinguist(@RequestBody Linguist linguist) {
        return linguistService.createLinguist(linguist);
    }
*/
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Linguist updateLinguist(@PathVariable Long id, @RequestBody Linguist linguist) {
        return linguistService.updateLinguist(id, linguist);
    }

    /*Fix error*/
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLinguist(@PathVariable Long id) {
        linguistService.deleteLinguist(id);
    }

    @GetMapping("/get/sourceLanguage/{sourceLanguage}")
    public List<Linguist> getBySourceLanguage(@PathVariable Languages sourceLanguage) {
        return linguistService.findBySourceLanguages(sourceLanguage);
    }

    @GetMapping("/get/targetLanguage/{targetLanguage}")
    public List<Linguist> getByTargetLanguage(@PathVariable Languages targetLanguage) {
        return linguistService.findByTargetLanguages(targetLanguage);
    }

    @GetMapping("/get/projectType/{projectType}")
    public List<Linguist> getByProjectType(@PathVariable ProjectType projectType) {
        return linguistService.findByProjectTypes(projectType);
    }

    @GetMapping("/get/linguisticTechnology/{linguisticTechnology}")
    public List<Linguist> getByLinguisticTechnology(@PathVariable LinguisticTechnology linguisticTechnology) {
        return linguistService.findByLinguisticTechnologies(linguisticTechnology);
    }

    @GetMapping("/get/dtpTechnology/{dtpTechnology}")
    public List<Linguist> getByDtpTechnology(@PathVariable DTPTechnology dtpTechnology) {
        return linguistService.findByDtpTechnologies(dtpTechnology);
    }

    @GetMapping("/get/bySL-TL")
    public List<Linguist> getBySourceAndTargetLanguage(@RequestParam("sourceLanguage") Languages sourceLanguage, @RequestParam("targetLanguage") Languages targetLanguage) {
        return linguistService.findBySourceLanguagesAndTargetLanguages(sourceLanguage, targetLanguage);
    }

    @GetMapping("/get/bySL-TL-PT")
    public List<Linguist> getBySourceAndTargetLanguageAndProjectType(@RequestParam("sourceLanguage") Languages sourceLanguage, @RequestParam("targetLanguage") Languages targetLanguage, @RequestParam("projectType") ProjectType projectType) {
        return linguistService.findBySourceLanguagesAndTargetLanguagesAndProjectTypes(sourceLanguage, targetLanguage, projectType);
    }

    @GetMapping("/get/bySL-TL-LT")
    public List<Linguist> getBySourceAndTargetLanguageAndLinguisticTechnology(@RequestParam("sourceLanguage") Languages sourceLanguage, @RequestParam("targetLanguage") Languages targetLanguage, @RequestParam("linguisticTechnology") LinguisticTechnology linguisticTechnology) {
        return linguistService.findBySourceLanguagesAndTargetLanguagesAndLinguisticTechnologies(sourceLanguage, targetLanguage, linguisticTechnology);
    }

    @GetMapping("/get/bySL-TL-DTPT")
    public List<Linguist> getBySourceAndTargetLanguageAndDtpTechnology(@RequestParam("sourceLanguage") Languages sourceLanguage, @RequestParam("targetLanguage") Languages targetLanguage, @RequestParam("dtpTechnology") DTPTechnology dtpTechnology) {
        return linguistService.findBySourceLanguagesAndTargetLanguagesAndDtpTechnologies(sourceLanguage, targetLanguage, dtpTechnology);
    }

    @GetMapping("/get/byLT-PT")
    public List<Linguist> getByLinguisticTechnologyAndProjectType(@RequestParam("linguisticTechnology") LinguisticTechnology linguisticTechnology, @RequestParam("projectType") ProjectType projectType) {
        return linguistService.findByLinguisticTechnologiesAndProjectTypes(linguisticTechnology, projectType);
    }
}
