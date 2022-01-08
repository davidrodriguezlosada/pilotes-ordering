package com.proof.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends BaseUncheckedException {

    private List<String> errors = new ArrayList<>();

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, List<String> errors) {
        super(message);

        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
