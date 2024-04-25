package com.ironhack.locmgmt.model;

import com.ironhack.locmgmt.model.enums.*;
import com.ironhack.locmgmt.model.projects.Project;
import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.model.users.ProjectManager;
import com.ironhack.locmgmt.model.users.User;
import lombok.*;
import jakarta.persistence.*;
import java.time.Duration;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    private String description;

    private Date deadline;

    private Duration timeRemaining;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private BillingStatus billingStatus;

    @Enumerated(EnumType.STRING)
    private Languages sourceLanguage;

    @Enumerated(EnumType.STRING)
    private Languages targetLanguage;

    @Enumerated(EnumType.STRING)
    private LinguisticTechnology linguisticTechnology;

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
