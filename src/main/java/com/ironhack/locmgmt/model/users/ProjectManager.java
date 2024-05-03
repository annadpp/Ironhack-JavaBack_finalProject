package com.ironhack.locmgmt.model.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.projects.Project;
import jakarta.validation.constraints.*;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProjectManager extends User {
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.PROJECT_MANAGER;

    @NotNull(message = "Spoken languages cannot be empty")
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Languages> spokenLanguages;

    @NotNull(message = "Project types cannot be empty")
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<ProjectType> projectTypes;

    @OneToMany(mappedBy = "projectManager")
    @JsonIgnoreProperties({"projectManager", "projects", "linguists", "tasks", "client"})
    private List<Project> projects;

    //GETS TASKS FROM PROJECTS ASSIGNED TO THE PROJECT MANAGER
    @Transient
    @JsonIgnoreProperties({"projectManager", "linguist", "project"})
    public List<Task> getTasks() {
        if (projects == null || projects.isEmpty()) {
            return Collections.emptyList();
        }

        List<Task> allTasks = new ArrayList<>();
        for (Project project : projects) {
            if (project.getTasks() != null) {
                allTasks.addAll(project.getTasks());
            }
        }
        return allTasks;
    }

    //Constructor for testing
    public ProjectManager(String username, String password, String name, String email, UserType userType, List<Languages> spokenLanguages, List<ProjectType> projectTypes) {
        super(username, password, name, email, userType);
        this.spokenLanguages = spokenLanguages;
        this.projectTypes = projectTypes;
    }
}

