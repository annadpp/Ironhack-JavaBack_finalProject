package com.ironhack.locmgmt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ironhack.locmgmt.model.enums.*;
import com.ironhack.locmgmt.model.projects.Project;
import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.model.users.ProjectManager;

import com.ironhack.locmgmt.validation.annotations.ValidDTPTechnology;
import com.ironhack.locmgmt.validation.annotations.ValidLinguisticTechnology;

import jakarta.validation.constraints.*;
import lombok.*;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
@ValidLinguisticTechnology
@ValidDTPTechnology
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "Task name cannot be empty")
    private String name;

    private String description;

    @Future(message = "The 'deadline' field must be a future date in the format 'yyyy-MM-dd'")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    //Set with difference between startDate and deadline
    private Duration timeRemaining;

    @Enumerated(EnumType.STRING)
    private Status taskStatus;

    @NotNull(message = "Project type cannot be empty")
    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    //Set with date when taskStatus == started
    private Date startDate;

    //Set with date when taskStatus == finished
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private BillingStatus billingStatus;

    @NotNull(message = "Source language cannot be empty")
    @Enumerated(EnumType.STRING)
    private Languages sourceLanguage;

    @NotNull(message = "Target language cannot be empty")
    @Enumerated(EnumType.STRING)
    private Languages targetLanguage;

    //ValidLinguisticTechnology validation depending on projectType
    @Enumerated(EnumType.STRING)
    private LinguisticTechnology linguisticTechnology;

    //ValidDTPTechnology validation depending on projectType
    @Enumerated(EnumType.STRING)
    private DTPTechnology dtpTechnology;

    //CHECK -> gets project manager from project
    @ManyToOne
    @JoinColumn(name = "project_manager_id")
    @JsonIgnoreProperties({"tasks", "password", "userType", "projects"})
    private ProjectManager projectManager;

    @ManyToOne
    @JoinColumn(name = "linguist_id")
    @JsonIgnoreProperties({"tasks", "password", "userType", "projects", "rates"})
    private Linguist linguist;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties({"tasks", "linguists", "client", "projectManager"})
    private Project project;

    //GETS PROJECT MANAGER FROM PROJECT ASSIGNED TO THE TASK
    @Transient
    @JsonIgnoreProperties({"projects"})
    public ProjectManager getProjectManager() {
        if (project != null) {
            return project.getProjectManager();
        }
        return null;
    }


    /*//Constructor for testing
    public Task(String name, String description, Date deadline, Duration timeRemaining, TaskStatus taskStatus, Role role, Date startDate, Date endDate, BillingStatus billingStatus, Languages sourceLanguage, Languages targetLanguage, LinguisticTechnology linguisticTechnology) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.timeRemaining = timeRemaining;
        this.taskStatus = taskStatus;
        this.role = role;
        this.startDate = startDate;
        this.endDate = endDate;
        this.billingStatus = billingStatus;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.linguisticTechnology = linguisticTechnology;
    }*/
}
