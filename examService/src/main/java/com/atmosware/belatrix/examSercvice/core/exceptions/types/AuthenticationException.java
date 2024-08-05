package com.atmosware.belatrix.examSercvice.core.exceptions.types;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
