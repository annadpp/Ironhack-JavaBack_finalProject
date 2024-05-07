package com.ironhack.locmgmt.validation.annotations;

import com.ironhack.locmgmt.validation.validators.RateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RateValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRate {
    String message() default "Invalid rate for the project type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}