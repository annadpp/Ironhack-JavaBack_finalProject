package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.enums.Department;
import com.ironhack.locmgmt.model.enums.Role;
import com.ironhack.locmgmt.model.users.Admin;
import com.ironhack.locmgmt.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    private Admin referenceAdmin;

    void setUp() {
        referenceAdmin = new Admin("admin1", "password", "Admin One", "admin1@example.com", Role.ADMIN, Department.IT);
        adminRepository.save(referenceAdmin);
    }

    @Test
    void deleteAdmin_Success() {
        Admin newAdmin = new Admin("adminToDelete", "password", "Admin To Delete", "adminToDelete@example.com", Role.ADMIN, Department.ADMINISTRATION);
        Admin savedAdmin = adminRepository.save(newAdmin);

        adminService.deleteAdmin(savedAdmin.getId());

        assertThrows(EntityNotFoundException.class, () -> adminRepository.findById(savedAdmin.getId()).orElseThrow(EntityNotFoundException::new));
    }

    @Test
    void getAllAdmins_EmptyListException() {
        assertThrows(EmptyListException.class, () -> adminService.getAllAdmins());
    }


    @Test
    void testGetAllAdmins_WhenNoAdminsExist() {
        assertThrows(EmptyListException.class, () -> adminService.getAllAdmins());
    }


    @Test
    void testGetAdminById_NonExistingId() {
        Long nonExistingAdminId = 100L;
        assertThrows(EntityNotFoundException.class, () -> adminService.getAdminById(nonExistingAdminId));
    }

}