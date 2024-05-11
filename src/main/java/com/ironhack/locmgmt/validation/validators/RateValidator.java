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
        // Verificar si al menos uno de los tipos de proyecto del lingüista coincide con el tipo de proyecto de la tarifa
        if (rate.getLinguist() != null) {
            if (!rate.getLinguist().getProjectTypes().contains(rate.getProjectType())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Linguist cannot handle this kind of project type").addConstraintViolation();
                return false;
            }
        }

        // Verificar si DTP Project tiene pagePrice o Linguistic Project tiene wordPrice (otro caso es inválido)
        if (rate.getProjectType() == ProjectType.DTP) {
            return rate.getPagePrice() != null && rate.getWordPrice() == null;
        } else if (rate.getProjectType() == ProjectType.TRANSLATION || rate.getProjectType() == ProjectType.REVIEW || rate.getProjectType() == ProjectType.POSTEDITING
        ) {
            return rate.getWordPrice() != null && rate.getPagePrice() == null;
        }

        return true;
    }
}