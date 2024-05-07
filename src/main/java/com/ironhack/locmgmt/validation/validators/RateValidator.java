package com.ironhack.locmgmt.validation.validators;

import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.validation.annotations.ValidRate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RateValidator implements ConstraintValidator<ValidRate, Rate> {
    @Override
    public void initialize(ValidRate constraintAnnotation) {
    }

    @Override
    public boolean isValid(Rate rate, ConstraintValidatorContext context) {
        if (rate.getProjectType() == ProjectType.DTP) {
            return rate.getPagePrice() != null && rate.getWordPrice() == null;
        } else if (rate.getProjectType() == ProjectType.LINGUISTIC) {
            return rate.getWordPrice() != null && rate.getPagePrice() == null;
        }

        return true;
    }
}