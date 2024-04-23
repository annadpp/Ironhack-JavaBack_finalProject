package com.ironhack.locmgmt;

import com.ironhack.locmgmt.model.Client;
import com.ironhack.locmgmt.model.projects.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientTest {
    static Client clientTest;

    @BeforeEach
    void setUp(){
        clientTest = new Client("Client Name", "client@example.com", "123456789", "Client Address");
    }

    @Test
    public void createEmptyClient(){
        Client emptyClient = new Client();
        assertNotNull(emptyClient);
        assertEquals(null, emptyClient.getName());
    }

    @Test
    public void checkClientIsCorrect(){
        assertEquals("Client Name", clientTest.getName());
        assertEquals("123456789", clientTest.getVATNumber());
    }

    @Test
    public void nameSetterTest(){
        clientTest.setName("New Client Name");
        assertEquals("New Client Name", clientTest.getName());
    }

    @Test
    public void addressGetterTest(){
        assertEquals("Client Address", clientTest.getAddress());
    }

    @Test
    public void testProjectsRelation(){
        //Client has a one-to-many relationship with Project
        Project project1 = new Project();
        Project project2 = new Project();
        List<Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);

        clientTest.setProjects(projects);

        assertEquals(2, clientTest.getProjects().size());
        assertEquals(project1, clientTest.getProjects().get(0));
        assertEquals(project2, clientTest.getProjects().get(1));
    }
}
