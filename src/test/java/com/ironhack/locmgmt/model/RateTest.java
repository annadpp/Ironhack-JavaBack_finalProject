package com.ironhack.locmgmt;

import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RateTest {
    static Rate rateTest;

    @BeforeEach
    void setUp(){
        rateTest = new Rate(BigDecimal.valueOf(50.0), Languages.ENGLISH, Languages.SPANISH, ProjectType.TRANSLATION);
    }

    @Test
    public void createEmptyRate(){
        Rate emptyRate = new Rate();
        assertNotNull(emptyRate);
        assertEquals(null, emptyRate.getSourceLanguage());
    }

    @Test
    public void checkRateIsCorrect(){
        assertEquals(BigDecimal.valueOf(50.0), rateTest.getWordPrice());
        assertEquals(Languages.ENGLISH, rateTest.getSourceLanguage());
        assertEquals(Languages.SPANISH, rateTest.getTargetLanguage());
        assertEquals(ProjectType.TRANSLATION, rateTest.getProjectType());
    }

    @Test
    public void priceSetterTest(){
        rateTest.setWordPrice(BigDecimal.valueOf(60.0));
        assertEquals(BigDecimal.valueOf(60.0), rateTest.getWordPrice());
    }

    @Test
    public void languageGetterTest(){
        assertEquals(Languages.ENGLISH, rateTest.getSourceLanguage());
    }
}
