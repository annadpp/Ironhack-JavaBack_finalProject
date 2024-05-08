package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.repository.RateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class RateService {
    @Autowired
    private RateRepository rateRepository;

    public List<Rate> getAllRates() {
        try {
            List<Rate> rates = rateRepository.findAll();
            if (rates.isEmpty()) {
                throw new EmptyListException("No rates were found");
            }
            return rates;
        } catch (DataAccessException e) {
            throw new DataRetrievalFailureException("Error while retrieving all rates", e);
        }
    }

    public Rate getRateById(Long id) {
        return rateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rate not found with id: " + id));
    }

    public Rate createRate(Rate rate) {
        ProjectType projectType = rate.getProjectType();
        if (projectType != ProjectType.LINGUISTIC && projectType != ProjectType.DTP) {
            throw new IllegalArgumentException("Invalid project type. Project type can only be LINGUISTIC or DTP.");
        }
        try {
            return rateRepository.save(rate);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error while creating the rate", e);
        }
    }

    public Rate updateRate(Long rateId, Rate rateDetails) {
        Rate existingRate = rateRepository.findById(rateId)
                .orElseThrow(() -> new EntityNotFoundException("Rate not found with id: " + rateId));

        //Only updates fields provided in rateDetails
        if (rateDetails.getWordPrice() != null) {
            existingRate.setWordPrice(rateDetails.getWordPrice());
        }
        if (rateDetails.getSourceLanguage() != null) {
            existingRate.setSourceLanguage(rateDetails.getSourceLanguage());
        }
        if (rateDetails.getTargetLanguage() != null) {
            existingRate.setTargetLanguage(rateDetails.getTargetLanguage());
        }
        if (rateDetails.getProjectType() != null) {
                if (rateDetails.getProjectType() != ProjectType.LINGUISTIC && rateDetails.getProjectType() != ProjectType.DTP) {
            throw new IllegalArgumentException("Invalid project type. Project type can only be LINGUISTIC or DTP.");
        } else existingRate.setProjectType(rateDetails.getProjectType());
        }
        //Add linguist when creating rate
        if (rateDetails.getLinguist() != null) {
            existingRate.setLinguist(rateDetails.getLinguist());
        }

        return rateRepository.save(existingRate);
    }

    public void deleteRate(Long rateId) {
        try {
            rateRepository.deleteById(rateId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Rate not found with id: " + rateId);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error deleting rate with id: " + rateId);
        }
    }

    public List<Rate> getRateBySourceLanguage(Languages sourceLanguage) {
        try {
            List<Rate> rates = rateRepository.getRateBySourceLanguage(sourceLanguage);
            if (rates.isEmpty()) {
                throw new EmptyListException("No rates were found");
            }
            return rates;
        } catch (DataRetrievalFailureException e) {
            throw new DataRetrievalFailureException("Error while retrieving rates by source language", e);
        }
    }

    public List<Rate> getRateByTargetLanguage(Languages targetLanguage) {
        try {
            List<Rate> rates = rateRepository.getRateByTargetLanguage(targetLanguage);
            if (rates.isEmpty()) {
                throw new EmptyListException("No rates were found");
            }
            return rates;
        } catch (DataRetrievalFailureException e) {
            throw new DataRetrievalFailureException("Error while retrieving rates by target language", e);
        }
    }

    public List<Rate> findRateBySourceLanguageAndTargetLanguage(Languages sourceLanguage, Languages targetLanguage) {
        try {
            List<Rate> rates = rateRepository.findBySourceLanguageAndTargetLanguage(sourceLanguage, targetLanguage);
            if (rates.isEmpty()) {
                throw new EmptyListException("No rates were found");
            }
            return rates;
        } catch (DataRetrievalFailureException e) {
            throw new DataRetrievalFailureException("Error while finding rates by source and target language", e);
        }
    }

    public List<Rate> findRateBySourceLanguageAndTargetLanguageAndProjectType(Languages sourceLanguage, Languages targetLanguage, ProjectType projectType) {
        try {
            List<Rate> rates = rateRepository.findBySourceLanguageAndTargetLanguageAndProjectType(sourceLanguage, targetLanguage, projectType);
            if (rates.isEmpty()) {
                throw new EmptyListException("No rates were found");
            }
            return rates;
        } catch (DataRetrievalFailureException e) {
            throw new DataRetrievalFailureException("Error while finding rates by source, target language, and project type", e);
        }
    }
}