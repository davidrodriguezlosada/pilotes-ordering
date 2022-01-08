package com.proof.validations;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PilotesNumberValidator implements ConstraintValidator<ValidPilotesNumber, Integer> {

    private final List<Integer> validPilotesNumber = Arrays.asList(5, 10, 15);

    @Override
    public void initialize(ValidPilotesNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return validPilotesNumber.contains(value);
    }
}
