package com.ironhack.locmgmt.model.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.*;

import com.ironhack.locmgmt.model.projects.Project;
import com.ironhack.locmgmt.validation.annotations.ValidLinguist;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ValidLinguist
public class Linguist extends User {
    @NotNull(message = "Source languages cannot be empty")
    @NotEmpty(message = "Source languages cannot be empty")
    @Enumerated(EnumType.STRING)
    private List<Languages> sourceLanguages;

    @NotNull(message = "Target languages cannot be empty")
    @NotEmpty(message = "Target languages cannot be empty")
    @Enumerated(EnumType.STRING)
    private List<Languages> targetLanguages;

    @NotNull(message = "Project types cannot be empty")
    @NotEmpty(message = "Project types cannot be empty")
    @Enumerated(EnumType.STRING)
    private List<ProjectType> projectTypes;

    @Enumerated(EnumType.STRING)
    private List<DTPTechnology> dtpTechnologies;

    @Enumerated(EnumType.STRING)
    private List<LinguisticTechnology> linguisticTechnologies;

    @OneToMany(mappedBy = "linguist", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonIgnoreProperties("linguist")
    private List<Rate> rates;

    @OneToMany(mappedBy = "linguist", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonIgnoreProperties({"linguist", "tasks", "projectManager", "project", "description", "deadline", "timeRemaining", "taskStatus", "startDate", "endDate", "newWords", "fuzzyWords", "totalWords", "pages"})
    private List<Task> tasks;

    //GETS PROJECTS FROM TASKS ASSIGNED TO THE PROJECT
    @Transient
    @JsonIgnoreProperties({"projectManager", "linguists", "tasks", "client", "startDate", "endDate", "deadline", "timeRemaining", "margin", "marginPercentage", "dtpTechnology", "totalBudget", "projectType", "dtpTechnolgoy", "pages", "targetLanguages", "sourceLanguage"})
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

    //Constructor for testing
    public Linguist(String username, String password, String name, String email, Role role, List<Languages> sourceLanguages, List<Languages> targetLanguages, List<ProjectType> projectTypes, List<DTPTechnology> dtpTechnologies, List<LinguisticTechnology> linguisticTechnologies) {
        super(username, password, name, email, role);
        this.sourceLanguages = sourceLanguages;
        this.targetLanguages = targetLanguages;
        this.projectTypes = projectTypes;
        this.dtpTechnologies = dtpTechnologies;
        this.linguisticTechnologies = linguisticTechnologies;
    }
}