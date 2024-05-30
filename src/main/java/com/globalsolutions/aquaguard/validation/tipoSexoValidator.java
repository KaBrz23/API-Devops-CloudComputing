package com.globalsolutions.aquaguard.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class tipoSexoValidator implements ConstraintValidator<tipoSexo, String>{
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.equals("Masculino") || value.equals("Feminino") || value.equals("Outro");
    }
}
