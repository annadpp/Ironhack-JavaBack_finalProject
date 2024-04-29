package com.ironhack.locmgmt.validation.annotations;

import com.ironhack.locmgmt.validation.validators.LinguisticTechnologyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LinguisticTechnologyValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLinguisticTechnology {
    String message() default "Linguistic technology must be provided for translation, review and postediting tasks. No DTP technology must be provided for translation, review and postediting tasks.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
