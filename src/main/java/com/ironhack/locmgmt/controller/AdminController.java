package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.enums.Department;
import com.ironhack.locmgmt.model.users.Admin;
import com.ironhack.locmgmt.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<Admin> getAllClients() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Admin getAdminById(@PathVariable Long id) {
        Admin admin = adminService.getAdminById(id);
        if (admin == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rate not found");
        }
        return admin;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.createAdmin(admin);
    }

    /*@PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Admin updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        return adminService.updateAdmin(id, admin);
    }*/

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
    }

    @GetMapping("/get/byDepartment")
    public List<Admin> getAdminsByDepartment(@RequestParam Department department) {
        return adminService.findAdminsByDepartment(department);
    }
}
