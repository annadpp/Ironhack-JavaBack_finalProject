package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateService {
    @Autowired
    private RateRepository rateRepository;

    public List<Rate> getAllRates() {
        return rateRepository.findAll();
    }

    public Rate getRateById(Long id) {
        return rateRepository.findById(id).orElse(null);
    }

    public Rate createRate(Rate Rate) {
        return rateRepository.save(Rate);
    }

    public Rate updateRate(Long rateId, Rate rateDetails) {
        Rate existingRate = rateRepository.findById(rateId)
                .orElseThrow(() -> new RuntimeException("Rate not found with id: " + rateId));

        existingRate.setPrice(rateDetails.getPrice());
        existingRate.setSourceLanguage(rateDetails.getSourceLanguage());
        existingRate.setTargetLanguage(rateDetails.getTargetLanguage());
        existingRate.setProjectType(rateDetails.getProjectType());

        return rateRepository.save(existingRate);
    }

    public void deleteRate(Long RateId) {
        rateRepository.deleteById(RateId);
    }
}