package com.proof.exceptions;

/**
 * Base unchecked exception for our application. All other custom exceptions should inherit from this one.
 */
public abstract class BaseUncheckedException extends RuntimeException {

    BaseUncheckedException(String message) {
        super(message);
    }
}
