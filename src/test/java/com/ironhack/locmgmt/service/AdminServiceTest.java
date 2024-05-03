package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.enums.Department;
import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.users.Admin;
import com.ironhack.locmgmt.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    private Admin referenceAdmin;

    @Test
    void setUp() {
        referenceAdmin = new Admin("admin1", "password", "Admin One", "admin1@example.com", UserType.ADMIN, Department.IT);
        adminRepository.save(referenceAdmin);
    }

    @Test
    void createAdmin_Success() {
        Admin newAdmin = new Admin("admin2", "password", "Admin Two", "admin2@example.com", UserType.ADMIN, Department.ADMINISTRATION);

        Admin createdAdmin = adminService.createAdmin(newAdmin);

        assertNotNull(createdAdmin);
        assertNotNull(createdAdmin.getId());
        assertEquals(newAdmin.getUsername(), createdAdmin.getUsername());
    }

    @Test
    void createAdmin_NullDepartment_ExceptionThrown() {
        Admin newAdmin = new Admin("admin2", "password", "Admin Two", "admin2@example.com", UserType.ADMIN, null);

        assertThrows(ConstraintViolationException.class, () -> adminService.createAdmin(newAdmin));
    }

    @Test
    void updateAdmin_Success() {
        referenceAdmin = adminService.createAdmin(new Admin("admin1", "password", "Admin One", "admin1@example.com", UserType.ADMIN, Department.IT));
        Admin updatedAdmin = new Admin("admin1_updated", "password_updated", "Admin One Updated", "admin1_updated@example.com", UserType.ADMIN, Department.ADMINISTRATION);

        Admin result = adminService.updateAdmin(referenceAdmin.getId(), updatedAdmin);

        assertNotNull(result);
        assertEquals(updatedAdmin.getUsername(), result.getUsername());
        assertEquals(updatedAdmin.getEmail(), result.getEmail());
        assertEquals(updatedAdmin.getName(), result.getName());
        assertEquals(updatedAdmin.getDepartment(), result.getDepartment());
    }

    @Test
    void deleteAdmin_Success() {
        Admin newAdmin = new Admin("adminToDelete", "password", "Admin To Delete", "adminToDelete@example.com", UserType.ADMIN, Department.ADMINISTRATION);
        Admin savedAdmin = adminRepository.save(newAdmin);

        adminService.deleteAdmin(savedAdmin.getId());

        assertThrows(EntityNotFoundException.class, () -> adminRepository.findById(savedAdmin.getId()).orElseThrow(EntityNotFoundException::new));
    }

    @Test
    void deleteAdmin_EntityNotFoundException() {
        Long nonExistingAdminId = Long.MAX_VALUE;

        assertThrows(EntityNotFoundException.class, () -> adminService.deleteAdmin(nonExistingAdminId));
    }

    @Test
    void getAllAdmins_Success() {
        Admin newAdmin = new Admin("newAdmin", "password", "New Admin", "newAdmin@example.com", UserType.ADMIN, Department.ADMINISTRATION);
        adminService.createAdmin(newAdmin);

        assertDoesNotThrow(() -> {
            List<Admin> adminsAfterCreation = adminService.getAllAdmins();
            assertEquals(1, adminsAfterCreation.size());
        });
    }

    @Test
    void getAllAdmins_EmptyListException() {
        assertThrows(EmptyListException.class, () -> adminService.getAllAdmins());
    }

}
