package com.ironhack.locmgmt.validation.validators;

import com.ironhack.locmgmt.model.projects.Project;
import com.ironhack.locmgmt.model.users.ProjectManager;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.validation.annotations.ValidProjectManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ProjectManagerValidator implements ConstraintValidator<ValidProjectManager, Project> {

    @Override
    public void initialize(ValidProjectManager constraintAnnotation) {
    }

    @Override
    public boolean isValid(Project project, ConstraintValidatorContext context) {
        ProjectManager projectManager = project.getProjectManager();
        if (projectManager == null) {
            return true; // No se valida si no hay un project manager asignado
        }

        List<ProjectType> projectTypes = projectManager.getProjectTypes();
        if (projectTypes == null || projectTypes.isEmpty()) {
            return false; // ProjectManager sin tipos de proyecto asignados
        }

        ProjectType projectType = project.getProjectType();
        if (!projectTypes.contains(projectType)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid project manager assignment for the project type").addConstraintViolation();
            return false;
        }

        return true;
    }
}
