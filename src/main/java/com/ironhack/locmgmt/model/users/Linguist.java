package com.ironhack.locmgmt.model.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.*;

import com.ironhack.locmgmt.model.projects.Project;
import jakarta.validation.constraints.NotNull;
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
public class Linguist extends User {
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.LINGUIST;

    @NotNull(message = "Source languages cannot be empty")
    @Enumerated(EnumType.STRING)
    private List<Languages> sourceLanguages;

    @NotNull(message = "Target languages cannot be empty")
    @Enumerated(EnumType.STRING)
    private List<Languages> targetLanguages;

    @NotNull(message = "Project types cannot be empty")
    @Enumerated(EnumType.STRING)
    private List<ProjectType> projectTypes;

    @NotNull(message = "DTP technologies cannot be empty")
    @Enumerated(EnumType.STRING)
    private List<DTPTechnology> dtpTechnologies;

    @NotNull(message = "Linguistic technologies cannot be empty")
    @Enumerated(EnumType.STRING)
    private List<LinguisticTechnology> linguisticTechnologies;

    @OneToMany(mappedBy = "linguist")
    @JsonIgnoreProperties("linguist")
    private List<Rate> rates;

    @OneToMany(mappedBy = "linguist")
    @JsonIgnoreProperties({"linguist", "tasks", "projectManager", "project"})
    private List<Task> tasks;

    //GETS PROJECTS FROM TASKS ASSIGNED TO THE PROJECT
    @Transient
    @JsonIgnoreProperties({"projectManager", "linguists", "tasks", "client", "totalBudget", "projectType", "dtpTechnolgoy", "pages", "targetLanguages", "sourceLanguage"})
    public List<Project> getProjects() {
        if (tasks == null || tasks.isEmpty()) {
            return Collections.emptyList();
        }

        List<Project> projectsList = new ArrayList<>();
        List<Long> projectIds = new ArrayList<>();

        for (Task task : tasks) {
            Project project = task.getProject();
            if (project != null && !projectIds.contains(project.getId())) {
                projectsList.add(project);
                projectIds.add(project.getId());
            }
        }
        return projectsList;
    }

    /*//Constructor for testing
    public Linguist(String username, String password, String name, String email, UserType userType, List<Languages> languages, List<ProjectType> projectTypes, List<DTPTechnology> dtpTechnologies, List<LinguisticTechnology> linguisticTechnologies) {
        super(username, password, name, email, userType);
        this.languages = languages;
        this.projectTypes = projectTypes;
        this.dtpTechnologies = dtpTechnologies;
        this.linguisticTechnologies = linguisticTechnologies;
    }*/
}
