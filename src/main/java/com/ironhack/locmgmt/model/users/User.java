package com.ironhack.locmgmt.model.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.projects.Project;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email address")
    private String email;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    //Constructors/getters/setters for testing
    public User(String username, String password, String name, String email, UserType userType) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }
}
