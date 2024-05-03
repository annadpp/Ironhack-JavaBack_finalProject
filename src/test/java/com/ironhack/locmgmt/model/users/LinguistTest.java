package com.ironhack.locmgmt.users;

import com.ironhack.locmgmt.model.enums.*;
import com.ironhack.locmgmt.model.users.Linguist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LinguistTest {
    private Linguist linguist;

    @BeforeEach
    void setUp() {
        List<Languages> sourceLanguages = Arrays.asList(Languages.ENGLISH, Languages.SPANISH);
        List<Languages> targetLanguages = Arrays.asList(Languages.GERMAN, Languages.FRENCH);
        List<ProjectType> projectTypes = Arrays.asList(ProjectType.TRANSLATION, ProjectType.REVIEW);
        List<DTPTechnology> dtpTechnologies = Arrays.asList(DTPTechnology.FIGMA, DTPTechnology.INDESIGN);
        List<LinguisticTechnology> linguisticTechnologies = Arrays.asList(LinguisticTechnology.TRADOS_STUDIO, LinguisticTechnology.MEMO_Q);
        linguist = new Linguist("linguistUser", "linguistPassword", "Linguist", "linguist@example.com", UserType.LINGUIST, sourceLanguages, targetLanguages, projectTypes, dtpTechnologies, linguisticTechnologies);
    }

    @Test
    void createLinguist() {
        assertNotNull(linguist);
        assertEquals("linguistUser", linguist.getUsername());
    }

    @Test
    void testSourceLanguages() {
        assertEquals(2, linguist.getSourceLanguages().size());
    }

    @Test
    void testTargetLanguages() {
        assertEquals(2, linguist.getTargetLanguages().size());
    }

    @Test
    void testProjectTypes() {
        assertEquals(2, linguist.getProjectTypes().size());
    }

    @Test
    void testDtpTechnologies() {
        assertEquals(2, linguist.getDtpTechnologies().size());
    }

    @Test
    void testLinguisticTechnologies() {
        assertEquals(2, linguist.getLinguisticTechnologies().size());
    }
}
