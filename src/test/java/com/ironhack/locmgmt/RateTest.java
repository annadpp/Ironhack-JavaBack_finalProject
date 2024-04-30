/*
package com.ironhack.locmgmt;


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
        rateTest = new Rate(BigDecimal.valueOf(50.0), Languages.ENGLISH, ProjectType.TRANSLATION);
    }

    @Test
    public void createEmptyRate(){
        Rate emptyRate = new Rate();
        assertNotNull(emptyRate);
        assertEquals(null, emptyRate.getLanguage());
    }

    @Test
    public void checkRateIsCorrect(){
        assertEquals(BigDecimal.valueOf(50.0), rateTest.getPrice());
        assertEquals(Languages.ENGLISH, rateTest.getLanguage());
    }

    @Test
    public void priceSetterTest(){
        rateTest.setPrice(BigDecimal.valueOf(60.0));
        assertEquals(BigDecimal.valueOf(60.0), rateTest.getPrice());
    }

    @Test
    public void languageGetterTest(){
        assertEquals(Languages.ENGLISH, rateTest.getLanguage());
    }
}

*/
