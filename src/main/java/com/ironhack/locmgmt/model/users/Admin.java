package com.ironhack.locmgmt.model.users;

import com.ironhack.locmgmt.model.enums.Department;

import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admins")
public class Admin extends User {
    @Enumerated(EnumType.STRING)
    private Department department;
}
