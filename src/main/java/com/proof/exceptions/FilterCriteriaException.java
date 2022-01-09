package com.proof.exceptions;

/**
 * Exception thrown when converting API filtering {@link com.proof.api.filtering.expressions.Expression} into hibernate
 * {@link org.hibernate.Criteria}
 */
public class FilterCriteriaException extends BaseUncheckedException {

    public FilterCriteriaException(String message) {
        super(message);
    }
}
