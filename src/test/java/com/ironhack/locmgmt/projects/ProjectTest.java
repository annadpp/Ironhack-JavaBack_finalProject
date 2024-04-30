/*
package com.ironhack.locmgmt.projects;

import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectTest {
    private Project project;

    @BeforeEach
    void setUp() {
        Client client = new Client();
        project = new Project("Project Name", "Project Description", new Date(), new Date(), BigDecimal.TEN, TaskStatus.STARTED, ProjectType.TRANSLATION);
    }

    @Test
    void createEmptyProject() {
        Project emptyProject = new Project();
        assertNotNull(emptyProject);
        assertEquals(null, emptyProject.getName());
    }

    @Test
    void checkProjectIsCorrect() {
        assertEquals("Project Name", project.getName());
        assertEquals("Project Description", project.getDescription());
    }

    @Test
    void nameSetterTest() {
        project.setName("New Project Name");
        assertEquals("New Project Name", project.getName());
    }

    @Test
    void descriptionGetterTest() {
        assertEquals("Project Description", project.getDescription());
    }
}
*/
