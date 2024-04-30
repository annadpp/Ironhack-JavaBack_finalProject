package com.ironhack.locmgmt.projects;

import com.ironhack.locmgmt.model.enums.DTPTechnology;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.TaskStatus;
import com.ironhack.locmgmt.model.projects.DTPProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DTPProjectTest {
    private DTPProject dtpProject;

    @BeforeEach
    void setUp() {
        Client client = new Client();
        dtpProject = new DTPProject("Project Name", "Project Description", new Date(), new Date(), BigDecimal.TEN, TaskStatus.STARTED, ProjectType.TRANSLATION, DTPTechnology.AFTER_EFFECTS, 120);
    }

    @Test
    void createEmptyDTPProject() {
        DTPProject emptyDTPProject = new DTPProject();
        assertNotNull(emptyDTPProject);
        assertEquals(null, emptyDTPProject.getName());
    }

    @Test
    void checkDTPProjectIsCorrect() {
        assertEquals("Project Name", dtpProject.getName());
        assertEquals("Project Description", dtpProject.getDescription());
    }

    @Test
    void nameSetterTest() {
        dtpProject.setName("New DTP Project Name");
        assertEquals("New DTP Project Name", dtpProject.getName());
    }

    @Test
    void descriptionGetterTest() {
        assertEquals("Project Description", dtpProject.getDescription());
    }
}

