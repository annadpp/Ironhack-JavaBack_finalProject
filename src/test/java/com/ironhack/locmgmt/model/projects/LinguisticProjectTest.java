package com.ironhack.locmgmt.projects;

import com.ironhack.locmgmt.model.Client;
import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.enums.Status;

import com.ironhack.locmgmt.model.projects.LinguisticProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LinguisticProjectTest {
    private LinguisticProject linguisticProject;

    @BeforeEach
    void setUp() {
        Client client = new Client();
        linguisticProject = new LinguisticProject(
                "Linguistic Project Name",
                "Linguistic Project Description",
                new Date(),
                new Date(),
                BigDecimal.TEN,
                Status.STARTED,
                200,
                100,
                LinguisticTechnology.TRADOS_STUDIO
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
