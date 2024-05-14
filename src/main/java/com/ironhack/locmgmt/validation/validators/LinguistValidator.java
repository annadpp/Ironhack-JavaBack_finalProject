package com.ironhack.locmgmt.validation.validators;

import com.ironhack.locmgmt.model.enums.DTPTechnology;
import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.validation.annotations.ValidLinguist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class LinguistValidator implements ConstraintValidator<ValidLinguist, Linguist> {

    @Override
    public void initialize(ValidLinguist constraintAnnotation) {}

    @Override
    public boolean isValid(Linguist linguist, ConstraintValidatorContext context) {
        if (linguist == null) {
            return true;
        }

        List<ProjectType> projectTypes = linguist.getProjectTypes();

        if (projectTypes == null || projectTypes.isEmpty()) {
            return true;
        }

        List<DTPTechnology> dtpTechnologies = linguist.getDtpTechnologies();
        List<LinguisticTechnology> linguisticTechnologies = linguist.getLinguisticTechnologies();

        boolean isDTP = projectTypes.contains(ProjectType.DTP);
        boolean containsNonDTP = projectTypes.stream().anyMatch(pt -> pt != ProjectType.DTP);

        if (isDTP && containsNonDTP) {
            //If linguistic technologies are DTP and not DTP, both dtpTechnologies and linguisticTechnologies must be assigned
            if (dtpTechnologies != null && linguisticTechnologies != null
                    && !dtpTechnologies.isEmpty() && !linguisticTechnologies.isEmpty()) {
                return true;
            } else {
                throw new RuntimeException("Both DTP and non-DTP project types are present, therefore linguistic and DTP technologies must not be empty.");
            }
        }

        if (isDTP) {
            //If linguistic technology is only DTP, linguisticTechnologies must be null
            if (linguisticTechnologies != null && !linguisticTechnologies.isEmpty()) {
                throw new RuntimeException("Linguistic technologies must be null when DTP project type is assigned.");
            }
        } else if (containsNonDTP) {
            //If linguistic technology is not DTP only, dtpTechnologies must be null
            if (dtpTechnologies != null && !dtpTechnologies.isEmpty()) {
                throw new RuntimeException("DTP technologies must be null when non-DTP project types are assigned.");
            }
        }

        return true;
    }
}
