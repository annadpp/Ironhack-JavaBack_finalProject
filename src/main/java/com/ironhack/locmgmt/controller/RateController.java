package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.service.RateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rates")
public class RateController {
    @Autowired
    private RateService rateService;

    @GetMapping("/get")
    public ResponseEntity<List<Rate>> getAllRates() {
        List<Rate> Rates = rateService.getAllRates();
        return new ResponseEntity<>(Rates, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Rate> getRateById(@PathVariable Long id) {
        Rate Rate = rateService.getRateById(id);
        if (Rate != null) {
            return new ResponseEntity<>(Rate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Rate> createRate(@RequestBody Rate Rate) {
        Rate createdRate = rateService.createRate(Rate);
        return new ResponseEntity<>(createdRate, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Rate> updateRate(@PathVariable Long id, @RequestBody Rate Rate) {
        Rate updatedRate = rateService.updateRate(id, Rate);
        return new ResponseEntity<>(updatedRate, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRate(@PathVariable Long id) {
        rateService.deleteRate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get/sourceLanguage/{sourceLanguage}")
    public ResponseEntity<List<Rate>> getRateBySourceLanguage(@PathVariable Languages sourceLanguage) {
        List<Rate> rates = rateService.getRateBySourceLanguage(sourceLanguage);
        return new ResponseEntity<>(rates, HttpStatus.OK);
    }

    @GetMapping("/get/targetLanguage/{targetLanguage}")
    public ResponseEntity<List<Rate>> getRateByTargetLanguage(@PathVariable Languages targetLanguage) {
        List<Rate> rates = rateService.getRateByTargetLanguage(targetLanguage);
        return new ResponseEntity<>(rates, HttpStatus.OK);
    }

    @GetMapping("/get/sl-tl")
    public ResponseEntity<List<Rate>> getRateBySourceAndTargetLanguage(
            @RequestParam Languages sourceLanguage,
            @RequestParam Languages targetLanguage) {
        List<Rate> rates = rateService.findRateBySourceLanguageAndTargetLanguage(sourceLanguage, targetLanguage);
        return new ResponseEntity<>(rates, HttpStatus.OK);
    }

    @GetMapping("/get/sl-tl-pt")
    public ResponseEntity<List<Rate>> getRateBySourceLanguageAndTargetLanguageAndProjectType(
            @RequestParam Languages sourceLanguage,
            @RequestParam Languages targetLanguage,
            @RequestParam ProjectType projectType) {
        List<Rate> rates = rateService.findRateBySourceLanguageAndTargetLanguageAndProjectType(sourceLanguage, targetLanguage, projectType);
        return new ResponseEntity<>(rates, HttpStatus.OK);
    }
}