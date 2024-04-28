package com.ironhack.locmgmt.model.users;

import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.UserType;
import jakarta.validation.constraints.*;
import lombok.*;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProjectManager extends User {
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.PROJECT_MANAGER;

    @NotNull
    @Size(min = 1)
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Languages> spokenLanguages;

    @NotNull
    @Size(min = 1)
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<ProjectType> projectTypes;

    /*//Constructor for testing
    public ProjectManager(String username, String password, String name, String email, UserType userType, List<Languages> languages, List<ProjectType> projectTypes) {
        super(username, password, name, email, userType);
        this.languages = languages;
        this.projectTypes = projectTypes;
    }*/
}

