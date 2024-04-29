package com.ironhack.locmgmt.validation.validators;

import com.ironhack.locmgmt.validation.annotations.ValidDTPTechnology;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.ProjectType;

public class DTPTechnologyValidator implements ConstraintValidator<ValidDTPTechnology, Task> {
    @Override
    public void initialize(ValidDTPTechnology constraintAnnotation) {
    }

    @Override
    public boolean isValid(Task task, ConstraintValidatorContext context) {
        if (task.getProjectType() == ProjectType.DTP) {
            return task.getDtpTechnology() != null;
        }
        return true;  //No validation needed for other project types
    }
}