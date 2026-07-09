package com.billing.waterbilling.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidPeriodValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPeriod {

    String message() default "Period must be in yyyyMM format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
