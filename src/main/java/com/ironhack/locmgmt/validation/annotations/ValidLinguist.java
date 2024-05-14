package com.ironhack.locmgmt.validation.annotations;

import com.ironhack.locmgmt.validation.validators.LinguistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = LinguistValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLinguist {
    String message() default "Invalid linguist info.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}