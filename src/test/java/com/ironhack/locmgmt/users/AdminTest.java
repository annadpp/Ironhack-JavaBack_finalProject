package com.ironhack.locmgmt.users;

import com.ironhack.locmgmt.model.enums.Department;
import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.users.Admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AdminTest {
    private Admin admin;

    @BeforeEach
    void setUp() {
        admin = new Admin("adminUser", "adminPassword", "Admin", "admin@example.com", UserType.ADMIN, Department.ADMINISTRATION);
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