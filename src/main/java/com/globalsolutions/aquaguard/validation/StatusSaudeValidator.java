package com.globalsolutions.aquaguard.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusSaudeValidator implements ConstraintValidator<StatusSaude, String>{
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.equals("Doente") || value.equals("Saudável");
    }
}
