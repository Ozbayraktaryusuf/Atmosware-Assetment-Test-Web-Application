package com.atmosware.belatrix.questionService.core.exceptions.types;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super(message);
    }
}
