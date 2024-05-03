package com.ironhack.locmgmt;

import com.ironhack.locmgmt.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertEquals("client@example.com", clientTest.getEmail());
        assertEquals("123456789", clientTest.getVATNumber());
        assertEquals("Client Address", clientTest.getAddress());
    }

    @Test
    public void nameSetterTest(){
        clientTest.setName("New Client Name");
        assertEquals("New Client Name", clientTest.getName());
    }

    @Test
    public void emailGetterTest(){
        assertEquals("client@example.com", clientTest.getEmail());
    }
}

