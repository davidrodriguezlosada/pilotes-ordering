package com.proof.exceptions;

/**
 * Exception thrown when parsing API filtering expressions
 */
public class FilterParseException extends BaseUncheckedException {

    public FilterParseException(String message) {
        super(message);
    }
}
