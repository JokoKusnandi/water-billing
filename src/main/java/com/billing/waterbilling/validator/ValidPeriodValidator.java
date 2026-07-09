package com.billing.waterbilling.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class ValidPeriodValidator
        implements ConstraintValidator<ValidPeriod, String> {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMM");

    @Override
    public boolean isValid(String value,
                           ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return true; // gunakan @NotBlank jika wajib diisi
        }

        try {
            YearMonth.parse(value, FORMATTER);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
