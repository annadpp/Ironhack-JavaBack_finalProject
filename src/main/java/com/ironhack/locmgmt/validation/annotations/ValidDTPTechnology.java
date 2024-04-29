package com.ironhack.locmgmt.validation.annotations;

import com.ironhack.locmgmt.validation.validators.DTPTechnologyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DTPTechnologyValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDTPTechnology {
    String message() default "DTP technology must be provided for DTP projects. No Linguistic technology must be provided for DTP tasks.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
