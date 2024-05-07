package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.enums.Department;
import com.ironhack.locmgmt.model.users.Admin;
import com.ironhack.locmgmt.repository.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.Collections;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        try {List<Admin> admins = adminRepository.findAll();
            if (admins.isEmpty()) {
                throw new EmptyListException("No admins were found");
            }
            return admins;
        } catch (DataAccessException e) {
            throw new DataRetrievalFailureException("Error while retrieving all admins", e);
        }
    }

    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));
    }

    /*public Admin createAdmin(Admin admin) {
        try {
            return adminRepository.save(admin);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error while creating the admin", e);
        }
    }*/

    /*Only Admin*/
    public Admin updateAdmin(Long id, Admin adminDetails) {
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));

        // Update the inherent Admin fields passed
        if (adminDetails.getDepartment() != null) {
            existingAdmin.setDepartment(adminDetails.getDepartment());
        }

        // Update the fields inherited from the User class -> not password (User only)
        if (adminDetails.getUsername() != null) {
            existingAdmin.setUsername(adminDetails.getUsername());
        }
        if (adminDetails.getName() != null) {
            existingAdmin.setName(adminDetails.getName());
        }
        if (adminDetails.getEmail() != null) {
            existingAdmin.setEmail(adminDetails.getEmail());
        }

        return adminRepository.save(existingAdmin);
    }

    public void deleteAdmin(Long id) {
        try {
            adminRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Admin not found with id: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error deleting rate with id: " + id);
        }
    }

    public List<Admin> findAdminsByDepartment(Department department) {
        List<Admin> admins = adminRepository.findByDepartment(department);
        if (admins.isEmpty()) {
            throw new EmptyListException("No admins were found");
        }
        return admins;
    }
}