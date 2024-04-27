package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ironhack.locmgmt.service.RateService;


import java.util.List;

@RestController
@RequestMapping("/api")
public class RateController {
    @Autowired
    private RateService RateService;

    @GetMapping("/rates")
    public ResponseEntity<List<Rate>> getAllRates() {
        List<Rate> Rates = RateService.getAllRates();
        return new ResponseEntity<>(Rates, HttpStatus.OK);
    }

    @GetMapping("/rate/{id}")
    public ResponseEntity<Rate> getRateById(@PathVariable Long id) {
        Rate Rate = RateService.getRateById(id);
        if (Rate != null) {
            return new ResponseEntity<>(Rate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/saveRate")
    public ResponseEntity<Rate> createRate(@RequestBody Rate Rate) {
        Rate createdRate = RateService.createRate(Rate);
        return new ResponseEntity<>(createdRate, HttpStatus.CREATED);
    }

    @PutMapping("/updateRate/{id}")
    public ResponseEntity<Rate> updateRate(@PathVariable Long id, @RequestBody Rate Rate) {
        Rate updatedRate = RateService.updateRate(id, Rate);
        return new ResponseEntity<>(updatedRate, HttpStatus.OK);
    }

    @DeleteMapping("/deleteRate/{id}")
    public ResponseEntity<?> deleteRate(@PathVariable Long id) {
        RateService.deleteRate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}