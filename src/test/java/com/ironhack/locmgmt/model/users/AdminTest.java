package com.ironhack.locmgmt.model.users;

import com.ironhack.locmgmt.model.enums.Department;
import com.ironhack.locmgmt.model.enums.Role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AdminTest {
    private Admin admin;

    @BeforeEach
    void setUp() {
        admin = new Admin("adminUser", "adminPassword", "Admin", "admin@example.com", Role.ADMIN, Department.ADMINISTRATION);
    }

    @Test
    void createAdmin() {
        assertNotNull(admin);
        assertEquals("adminUser", admin.getUsername());
    }

    @Test
    void testDepartment() {
        assertEquals(Department.ADMINISTRATION, admin.getDepartment());
    }
}
