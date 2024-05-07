package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.enums.Department;
import com.ironhack.locmgmt.model.enums.Role;
import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.users.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    void findByDepartment() {
        // Create a sample admin
        Admin admin = new Admin("adminUser", "adminPassword", "Admin", "admin@example.com", Role.ADMIN, Department.ADMINISTRATION);
        adminRepository.save(admin);

        // Retrieve admins by department
        List<Admin> admins = adminRepository.findByDepartment(Department.ADMINISTRATION);

        // Assert that the retrieved list contains the admin
        assertEquals(1, admins.size());
        assertEquals("adminUser", admins.get(0).getUsername());
    }
}