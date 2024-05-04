package com.ironhack.locmgmt.model.users;

import com.ironhack.locmgmt.model.enums.Department;

import com.ironhack.locmgmt.model.enums.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin extends UserEntity {
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.ADMIN;

    @NotNull(message = "Department cannot be empty")
    @Enumerated(EnumType.STRING)
    private Department department;

    //Constructor for testing
    /*public Admin(String username, String password, String name, String email, UserType userType, Role role, Department department) {
        super(username, password, name, email, userType, role);
        this.department = department;
    }*/
}
