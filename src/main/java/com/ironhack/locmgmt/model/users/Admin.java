package com.ironhack.locmgmt.model.users;

import com.ironhack.locmgmt.model.enums.Department;
import com.ironhack.locmgmt.model.enums.Role;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin extends User {
    @NotNull(message = "Department cannot be empty")
    @Enumerated(EnumType.STRING)
    private Department department;

    //Constructor for testing
    public Admin(String username, String password, String name, String email, Role role, Department department) {
        super(username, password, name, email, role);
        this.department = department;
    }
}