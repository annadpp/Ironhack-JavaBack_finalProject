package com.ironhack.locmgmt;

import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.*;
import com.ironhack.locmgmt.model.projects.Project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TaskTest {
    static Task taskTest;

 /*   @BeforeEach
    void setUp(){
        taskTest = new Task("Task Name", "Task Description", new Date(), Duration.ofHours(2), TaskStatus.STARTED, Role.LINGUIST, new Date(), new Date(), BillingStatus.INVOICED, Languages.ENGLISH, Languages.SPANISH, LinguisticTechnology.TRADOS_STUDIO);
    }*/

    @Test
    public void createEmptyTask(){
        Task emptyTask = new Task();
        assertNotNull(emptyTask);
        assertEquals(null, emptyTask.getName());
    }

    @Test
    public void checkTaskIsCorrect(){
        assertEquals("Task Name", taskTest.getName());
        assertEquals("Task Description", taskTest.getDescription());
    }

    @Test
    public void nameSetterTest(){
        taskTest.setName("New Task Name");
        assertEquals("New Task Name", taskTest.getName());
    }

    @Test
    public void descriptionGetterTest(){
        assertEquals("Task Description", taskTest.getDescription());
    }

    @Test
    public void testProjectRelation(){
        //Task has a many-to-one relationship with Project
        Project project = new Project();
        taskTest.setProject(project);

        assertEquals(project, taskTest.getProject());
    }
}
