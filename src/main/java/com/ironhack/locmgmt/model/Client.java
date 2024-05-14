package com.ironhack.locmgmt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ironhack.locmgmt.model.projects.Project;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email address")
    private String email;

    private String VATNumber;

    private String address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonIgnoreProperties({"client", "tasks", "timeRemaining", "projectManager", "linguists"})
    private List<Project> projects;

    //Constructor for testing
    public Client(String name, String email, String VATNumber, String address) {
        this.name = name;
        this.email = email;
        this.VATNumber = VATNumber;
        this.address = address;
    }

    //Constructor for testing controller
    public Client(String alice, String mail) {
    }
}
