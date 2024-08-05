package com.atmosware.belatrix.examSercvice.core.exceptions.types;

import java.util.Map;

public class ValidationException extends RuntimeException {
    public ValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    private Map<String, String> errors;

    public Map<String, String> getErrors() {
        return errors;
    }
}
