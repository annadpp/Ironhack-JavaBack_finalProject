package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
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

        //Update only fields provided in rateDetails
        if (rateDetails.getPrice() != null) {
            existingRate.setPrice(rateDetails.getPrice());
        }
        if (rateDetails.getSourceLanguage() != null) {
            existingRate.setSourceLanguage(rateDetails.getSourceLanguage());
        }
        if (rateDetails.getTargetLanguage() != null) {
            existingRate.setTargetLanguage(rateDetails.getTargetLanguage());
        }
        if (rateDetails.getProjectType() != null) {
            existingRate.setProjectType(rateDetails.getProjectType());
        }

        return rateRepository.save(existingRate);
    }


    public void deleteRate(Long RateId) {
        rateRepository.deleteById(RateId);
    }

    public List<Rate> getRateBySourceLanguage(Languages sourceLanguage) {
        return rateRepository.getRateBySourceLanguage(sourceLanguage);
    }

    public List<Rate> getRateByTargetLanguage(Languages targetLanguage) {
        return rateRepository.getRateByTargetLanguage(targetLanguage);
    }

    public List<Rate> findRateBySourceLanguageAndTargetLanguage(Languages sourceLanguage, Languages targetLanguage) {
        return rateRepository.findBySourceLanguageAndTargetLanguage(sourceLanguage, targetLanguage);
    }

    public List<Rate> findRateBySourceLanguageAndTargetLanguageAndProjectType(Languages sourceLanguage, Languages targetLanguage, ProjectType projectType) {
        return rateRepository.findBySourceLanguageAndTargetLanguageAndProjectType(sourceLanguage, targetLanguage, projectType);
    }
}