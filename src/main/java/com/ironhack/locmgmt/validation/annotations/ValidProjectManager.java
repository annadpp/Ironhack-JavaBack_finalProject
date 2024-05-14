package com.ironhack.locmgmt.validation.annotations;

import com.ironhack.locmgmt.validation.validators.ProjectManagerValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ProjectManagerValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidProjectManager {
    String message() default "Invalid project manager assignment for the project type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
