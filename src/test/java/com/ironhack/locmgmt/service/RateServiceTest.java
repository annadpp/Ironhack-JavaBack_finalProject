package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.repository.RateRepository;
import com.ironhack.locmgmt.service.RateService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RateServiceTest {

    @Autowired
    private RateService rateService;

    @Autowired
    private RateRepository rateRepository;

    private Rate referenceRate;

    @Test
    void setUp() {
        referenceRate = new Rate(BigDecimal.valueOf(10), Languages.ENGLISH, Languages.SPANISH, ProjectType.TRANSLATION);
        rateRepository.save(referenceRate);
    }

    @Test
    void createRate_Success() {
        Rate newRate = new Rate(BigDecimal.valueOf(20), Languages.FRENCH, Languages.GERMAN, ProjectType.LINGUISTIC);

        Rate createdRate = rateService.createRate(newRate);

        assertNotNull(createdRate);
        assertNotNull(createdRate.getId());
        assertEquals(newRate.getWordPrice(), createdRate.getWordPrice());
    }

    @Test
    void createRate_NullDepartment_ExceptionThrown() {
        Rate newRate = new Rate(BigDecimal.valueOf(20), Languages.FRENCH, Languages.GERMAN, null);

        assertThrows(ConstraintViolationException.class, () -> rateService.createRate(newRate));
    }

    @Test
    void updateRate_Success() {
        Rate rate = rateRepository.save(new Rate(BigDecimal.TEN, Languages.SPANISH, Languages.GERMAN, ProjectType.DTP));
        Rate updatedRate = new Rate(BigDecimal.valueOf(15), Languages.SPANISH, Languages.GERMAN, ProjectType.DTP);

        Rate result = rateService.updateRate(rate.getId(), updatedRate);

        assertNotNull(result);
        assertEquals(updatedRate.getWordPrice(), result.getWordPrice());
    }

    @Test
    void deleteRate_Success() {
        Rate rate = rateRepository.save(new Rate(BigDecimal.TEN, Languages.SPANISH, Languages.FRENCH, ProjectType.DTP));

        rateService.deleteRate(rate.getId());

        assertThrows(EntityNotFoundException.class, () -> rateRepository.findById(rate.getId()).orElseThrow(EntityNotFoundException::new));
    }

    @Test
    void getRateBySourceLanguage_Success() {
        Rate newRate = new Rate(BigDecimal.valueOf(50), Languages.ENGLISH, Languages.GERMAN, ProjectType.TRANSLATION);
        rateRepository.save(newRate);

        List<Rate> rates = rateService.getRateBySourceLanguage(Languages.ENGLISH);

        assertFalse(rates.isEmpty());
        assertTrue(rates.stream().allMatch(rate -> rate.getSourceLanguage() == Languages.ENGLISH));
    }

    @Test
    void getAllRates_Success() {
        Rate newRate = new Rate(BigDecimal.valueOf(50), Languages.ENGLISH, Languages.GERMAN, ProjectType.TRANSLATION);
        rateRepository.save(newRate);

        List<Rate> rates = rateService.getAllRates();

        assertFalse(rates.isEmpty());
    }

    @Test
    void getRateById_Success() {
        Rate rate = rateRepository.save(new Rate(BigDecimal.TEN, Languages.ENGLISH, Languages.FRENCH, ProjectType.TRANSLATION));

        Rate retrievedRate = rateService.getRateById(rate.getId());

        assertNotNull(retrievedRate);
        assertEquals(rate.getId(), retrievedRate.getId());
    }

    @Test
    void getAllRates_EmptyListException() {
        assertThrows(EmptyListException.class, () -> rateService.getAllRates());
    }

    @Test
    void findRateBySourceAndTargetLanguageAndProjectType_Success() {
        Rate newRate = new Rate(BigDecimal.valueOf(50), Languages.SPANISH, Languages.GERMAN, ProjectType.TRANSLATION);
        rateRepository.save(newRate);

        List<Rate> rates = rateService.findRateBySourceLanguageAndTargetLanguageAndProjectType(
                Languages.SPANISH, Languages.GERMAN, ProjectType.TRANSLATION);

        assertFalse(rates.isEmpty());
        assertTrue(rates.stream().allMatch(rate -> rate.getSourceLanguage() == Languages.SPANISH
                && rate.getTargetLanguage() == Languages.GERMAN
                && rate.getProjectType() == ProjectType.TRANSLATION));
    }
}
