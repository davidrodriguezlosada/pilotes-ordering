package com.proof.exceptions;

public abstract class BaseUncheckedException extends RuntimeException {

    public BaseUncheckedException(String message) {
        super(message);
    }
}
