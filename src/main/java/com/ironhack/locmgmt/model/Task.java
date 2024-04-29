package com.ironhack.locmgmt.model;

import com.ironhack.locmgmt.model.enums.*;
import com.ironhack.locmgmt.model.projects.Project;
import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.model.users.ProjectManager;
import com.ironhack.locmgmt.model.users.User;
import com.ironhack.locmgmt.util.TaskUtil;
import com.ironhack.locmgmt.validation.annotations.ValidDTPTechnology;
import com.ironhack.locmgmt.validation.annotations.ValidLinguisticTechnology;
import jakarta.validation.constraints.*;
import lombok.*;
import jakarta.persistence.*;

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

    @NotBlank
    private String name;

    private String description;

    @Future
    private Date deadline;

    //Set with difference between startDate and deadline
    private Duration timeRemaining;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    //Set with date when taskStatus == started
    private Date startDate;

    //Set with date when taskStatus == finished
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private BillingStatus billingStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Languages sourceLanguage;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Languages targetLanguage;

    //ValidLinguisticTechnology validation depending on projectType
    @Enumerated(EnumType.STRING)
    private LinguisticTechnology linguisticTechnology;

    //ValidDTPTechnology validation depending on projectType
    @Enumerated(EnumType.STRING)
    private DTPTechnology dtpTechnology;

    @ManyToOne
    @JoinColumn(name = "project_manager_id")
    private ProjectManager projectManager;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "linguist_id")
    private Linguist linguist;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void updateDatesAndTimeRemaining(TaskStatus newStatus) {
        TaskUtil taskUtil = new TaskUtil();
        taskUtil.updateDatesAndTimeRemaining(this, newStatus);
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
