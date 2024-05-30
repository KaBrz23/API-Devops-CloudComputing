package com.globalsolutions.aquaguard.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TipoSexoValidator.class)
public @interface StatusSaude {
    String message() default "{tilapia.statussaude.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
