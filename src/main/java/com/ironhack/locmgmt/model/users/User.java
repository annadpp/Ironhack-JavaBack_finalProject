package com.ironhack.locmgmt.model.users;

import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.projects.Project;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    @ManyToMany(mappedBy = "users")
    private List<Project> projects;
}
