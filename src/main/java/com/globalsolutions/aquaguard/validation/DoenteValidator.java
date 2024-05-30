package com.globalsolutions.aquaguard.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DoenteValidator implements ConstraintValidator<Doente, String>{
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.equals("Saudável") || value.equals("Doente");
    }
}
