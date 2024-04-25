package com.ironhack.locmgmt.model.users;

import com.ironhack.locmgmt.model.enums.Department;

import com.ironhack.locmgmt.model.enums.UserType;
import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin extends User {
    @Enumerated(EnumType.STRING)
    private Department department;

    /*//Constructor for testing
    public Admin(String username, String password, String name, String email, UserType userType, Department department) {
        super(username, password, name, email, userType);
        this.department = department;
    }*/
}
