/*
package com.ironhack.locmgmt.projects;

import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LinguisticProjectTest {
    private LinguisticProject linguisticProject;

    @BeforeEach
    void setUp() {
        Client client = new Client();
        List<Languages> targetLanguages = Arrays.asList(Languages.FRENCH, Languages.GERMAN);
        linguisticProject = new LinguisticProject(
                "Linguistic Project Name",
                "Linguistic Project Description",
                new Date(),
                new Date(),
                BigDecimal.TEN,
                TaskStatus.STARTED,
                ProjectType.TRANSLATION,
                200,
                100,
                LinguisticTechnology.TRADOS_STUDIO,
                Languages.ENGLISH,
                targetLanguages
        );
    }

    @Test
    void createEmptyLinguisticProject() {
        LinguisticProject emptyLinguisticProject = new LinguisticProject();
        assertNotNull(emptyLinguisticProject);
        assertEquals(null, emptyLinguisticProject.getName());
    }

    @Test
    void checkLinguisticProjectIsCorrect() {
        assertEquals("Linguistic Project Name", linguisticProject.getName());
        assertEquals("Linguistic Project Description", linguisticProject.getDescription());
    }

    @Test
    void nameSetterTest() {
        linguisticProject.setName("New Linguistic Project Name");
        assertEquals("New Linguistic Project Name", linguisticProject.getName());
    }

    @Test
    void descriptionGetterTest() {
        assertEquals("Linguistic Project Description", linguisticProject.getDescription());
    }
}
*/
