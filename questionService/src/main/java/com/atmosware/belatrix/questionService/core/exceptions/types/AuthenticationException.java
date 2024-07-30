package com.atmosware.belatrix.questionService.core.exceptions.types;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
