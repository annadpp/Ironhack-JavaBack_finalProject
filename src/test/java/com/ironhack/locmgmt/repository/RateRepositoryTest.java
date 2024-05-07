package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.Client;
import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RateRepositoryTest {

    @Autowired
    private RateRepository rateRepository;

    @Test
    void testGetRateBySourceLanguage() {
        Rate rate = new Rate(BigDecimal.TEN, Languages.ENGLISH, Languages.SPANISH, ProjectType.TRANSLATION);
        rateRepository.save(rate);

        List<Rate> rates = rateRepository.getRateBySourceLanguage(Languages.ENGLISH);
        assertEquals(1, rates.size());
    }

    @Test
    void testGetRateByTargetLanguage() {
        Rate rate = new Rate(BigDecimal.TEN, Languages.ENGLISH, Languages.SPANISH, ProjectType.TRANSLATION);
        rateRepository.save(rate);

        List<Rate> rates = rateRepository.getRateByTargetLanguage(Languages.SPANISH);
        assertEquals(1, rates.size());
    }

    @Test
    void testFindBySourceLanguageAndTargetLanguage() {
        Rate rate = new Rate(BigDecimal.TEN, Languages.ENGLISH, Languages.SPANISH, ProjectType.TRANSLATION);
        rateRepository.save(rate);

        List<Rate> rates = rateRepository.findBySourceLanguageAndTargetLanguage(Languages.ENGLISH, Languages.SPANISH);
        assertEquals(1, rates.size());
    }

    @Test
    void testFindBySourceLanguageAndTargetLanguageAndProjectType() {
        List<Rate> rates = rateRepository.findBySourceLanguageAndTargetLanguageAndProjectType(Languages.ENGLISH, Languages.SPANISH, ProjectType.TRANSLATION);
        assertEquals(1, rates.size());
    }
}