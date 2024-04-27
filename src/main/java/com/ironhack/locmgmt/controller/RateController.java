package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.service.RateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/rates")
public class RateController {
    @Autowired
    private RateService rateService;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<Rate> getAllRates() {
        return rateService.getAllRates();
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Rate getRateById(@PathVariable Long id) {
        Rate rate = rateService.getRateById(id);
        if (rate == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rate not found");
        }
        return rate;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Rate createRate(@RequestBody Rate rate) {
        return rateService.createRate(rate);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Rate updateRate(@PathVariable Long id, @RequestBody Rate rate) {
        return rateService.updateRate(id, rate);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRate(@PathVariable Long id) {
        rateService.deleteRate(id);
    }

    @GetMapping("/get/sourceLanguage/{sourceLanguage}")
    @ResponseStatus(HttpStatus.OK)
    public List<Rate> getRateBySourceLanguage(@PathVariable Languages sourceLanguage) {
        return rateService.getRateBySourceLanguage(sourceLanguage);
    }

    @GetMapping("/get/targetLanguage/{targetLanguage}")
    @ResponseStatus(HttpStatus.OK)
    public List<Rate> getRateByTargetLanguage(@PathVariable Languages targetLanguage) {
        return rateService.getRateByTargetLanguage(targetLanguage);
    }

    @GetMapping("/get/sl-tl")
    @ResponseStatus(HttpStatus.OK)
    public List<Rate> getRateBySourceAndTargetLanguage(
            @RequestParam Languages sourceLanguage,
            @RequestParam Languages targetLanguage) {
        return rateService.findRateBySourceLanguageAndTargetLanguage(sourceLanguage, targetLanguage);
    }

    @GetMapping("/get/sl-tl-pt")
    @ResponseStatus(HttpStatus.OK)
    public List<Rate> getRateBySourceLanguageAndTargetLanguageAndProjectType(
            @RequestParam Languages sourceLanguage,
            @RequestParam Languages targetLanguage,
            @RequestParam ProjectType projectType) {
        return rateService.findRateBySourceLanguageAndTargetLanguageAndProjectType(sourceLanguage, targetLanguage, projectType);
    }
}