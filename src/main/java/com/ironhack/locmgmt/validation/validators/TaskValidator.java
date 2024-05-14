package com.ironhack.locmgmt.validation.validators;

import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.validation.annotations.ValidTask;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TaskValidator implements ConstraintValidator<ValidTask, Task> {
    @Override
    public void initialize(ValidTask constraintAnnotation) {
    }

    @Override
    public boolean isValid(Task task, ConstraintValidatorContext context) {
        // Verifica si el tipo de proyecto de la tarea es compatible con el tipo de proyecto del linguista
        if (task.getLinguist() != null && !task.getLinguist().getProjectTypes().contains(task.getProjectType())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Linguist cannot take care of this task's project type").addConstraintViolation();
            return false;
        }

        // Si el tipo de proyecto de la tarea es DTP pero el tipo de proyecto del proyecto no es DTP
        if (task.getProjectType() == ProjectType.DTP && task.getProject().getProjectType() != ProjectType.DTP) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Cannot assign DTP project type to non-DTP task.").addConstraintViolation();
            return false;
        }

        // Si el tipo de proyecto de la tarea no es DTP pero el tipo de proyecto del proyecto es DTP
        if (task.getProjectType() != ProjectType.DTP && task.getProject().getProjectType() == ProjectType.DTP) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Cannot assign non-DTP project type to DTP task.").addConstraintViolation();
            return false;
        }

        return true;
    }


}

