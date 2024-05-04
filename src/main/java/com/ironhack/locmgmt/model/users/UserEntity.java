package com.ironhack.locmgmt.model.users;

import com.ironhack.locmgmt.model.enums.Role;
import com.ironhack.locmgmt.model.enums.UserType;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

/*
    @NotEmpty(message = "Name cannot be empty")
*/
    private String name;

/*
    @NotEmpty(message = "Email cannot be empty")
*/
    @Email(message = "Invalid email address")
    private String email;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private Role role;

    public UserEntity(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    //Constructors/getters/setters for testing
    /*public User(String username, String password, String name, String email, UserType userType) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }*/
}
