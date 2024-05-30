package com.globalsolutions.aquaguard.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FissuraValidator implements ConstraintValidator<Fissura, String>{
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.equals("Detectado") || value.equals("Não Detectado");
    }
}
