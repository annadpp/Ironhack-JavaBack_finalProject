package com.ironhack.locmgmt.validation.validators;

import com.ironhack.locmgmt.validation.annotations.ValidLinguisticTechnology;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.ProjectType;

public class LinguisticTechnologyValidator implements ConstraintValidator<ValidLinguisticTechnology, Task> {
    @Override
    public void initialize(ValidLinguisticTechnology constraintAnnotation) {
    }

    @Override
    public boolean isValid(Task task, ConstraintValidatorContext context) {
        if (task.getProjectType() == ProjectType.TRANSLATION || task.getProjectType() == ProjectType.REVIEW || task.getProjectType() == ProjectType.POSTEDITING) {
            return task.getLinguisticTechnology() != null;
        }
        return true;  //No validation needed for other project types
    }
}