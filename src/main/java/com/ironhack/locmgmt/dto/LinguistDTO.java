package com.ironhack.locmgmt.dto;

import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.DTPTechnology;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.enums.ProjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinguistDTO {
    private Long id;
    private String name;
    private String email;
    private String username;
    private List<Languages> sourceLanguages;
    private List<Languages> targetLanguages;
    private List<ProjectType> projectTypes;
    private List<DTPTechnology> dtpTechnologies;
    private List<LinguisticTechnology> linguisticTechnologies;
    private List<Rate> rates;
    private List<Task> tasks;

    // Constructores, getters y setters
}