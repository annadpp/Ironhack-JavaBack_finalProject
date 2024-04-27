package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.model.users.Admin;
import com.ironhack.locmgmt.repository.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(Long adminId, Admin adminDetails) {
        Admin existingAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + adminId));

        existingAdmin.setDepartment(adminDetails.getDepartment());

        return adminRepository.save(existingAdmin);
    }

    public void deleteAdmin(Long AdminId) {
        adminRepository.deleteById(AdminId);
    }
}
