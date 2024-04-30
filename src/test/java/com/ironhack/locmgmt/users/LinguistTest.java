/*
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
        List<Languages> languages = Arrays.asList(Languages.ENGLISH, Languages.SPANISH);
        List<ProjectType> projectTypes = Arrays.asList(ProjectType.TRANSLATION, ProjectType.REVIEW);
        List<DTPTechnology> dtpTechnologies = Arrays.asList(DTPTechnology.FIGMA, DTPTechnology.INDESIGN);
        List<LinguisticTechnology> linguisticTechnologies = Arrays.asList(LinguisticTechnology.TRADOS_STUDIO, LinguisticTechnology.MEMO_Q);
        linguist = new Linguist("linguistUser", "linguistPassword", "Linguist", "linguist@example.com", UserType.LINGUIST, languages, projectTypes, dtpTechnologies, linguisticTechnologies);
    }

    @Test
    void createLinguist() {
        assertNotNull(linguist);
        assertEquals("linguistUser", linguist.getUsername());
    }

    @Test
    void testLanguages() {
        assertEquals(2, linguist.getLanguages().size());
    }

    @Test
    void testLinguisticTechnologies() {
        assertEquals(2, linguist.getLinguisticTechnologies().size());
    }
}*/
