package com.ironhack.locmgmt.dto;

import com.ironhack.locmgmt.model.enums.*;
import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.model.users.ProjectManager;
import lombok.*;

import java.util.Date;
import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private Date deadline;
    private Duration timeRemaining;
    private Status taskStatus;
    private ProjectType projectType;
    private Date startDate;
    private Date endDate;
    private BillingStatus billingStatus;
    private Languages sourceLanguage;
    private Languages targetLanguage;
    private LinguisticTechnology linguisticTechnology;
    private DTPTechnology dtpTechnology;
    private Linguist linguist;
    private ProjectManager projectManager;
}