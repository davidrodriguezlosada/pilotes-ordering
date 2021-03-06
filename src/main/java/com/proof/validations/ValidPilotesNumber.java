package com.proof.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation to check the correct number of pilotes of an order. The validation is done in the
 * {@link PilotesNumberValidator} class.
 */
@Documented
@Constraint(validatedBy = PilotesNumberValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPilotesNumber {

    String message() default "Invalid pilotes number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
