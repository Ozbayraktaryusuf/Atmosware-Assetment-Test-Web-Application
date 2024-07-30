package com.atmosware.belatrix.questionService.core.exceptions.types;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super(message);
    }
}
