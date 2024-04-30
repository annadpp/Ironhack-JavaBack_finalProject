package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.enums.Department;
import com.ironhack.locmgmt.model.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    List<Admin> findByDepartment(Department department);
}
