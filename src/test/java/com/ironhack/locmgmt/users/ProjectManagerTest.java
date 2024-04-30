/*
package com.ironhack.locmgmt.users;

import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.users.ProjectManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProjectManagerTest {
    private ProjectManager projectManager;

    @BeforeEach
    void setUp() {
        List<Languages> languages = Arrays.asList(Languages.ENGLISH, Languages.FRENCH);
        List<ProjectType> projectTypes = Arrays.asList(ProjectType.TRANSLATION, ProjectType.POSTEDITING);
        projectManager = new ProjectManager("projectManagerUser", "projectManagerPassword", "Project Manager", "pm@example.com", UserType.PROJECT_MANAGER, languages, projectTypes);
    }

    @Test
    void createProjectManager() {
        assertNotNull(projectManager);
        assertEquals("projectManagerUser", projectManager.getUsername());
    }

    @Test
    void testLanguages() {
        assertEquals(2, projectManager.getLanguages().size());
    }

    @Test
    void testProjectTypes() {
        assertEquals(2, projectManager.getProjectTypes().size());
    }
}*/
