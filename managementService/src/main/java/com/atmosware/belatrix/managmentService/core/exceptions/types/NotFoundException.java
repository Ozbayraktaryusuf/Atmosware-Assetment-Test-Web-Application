package com.atmosware.belatrix.managmentService.core.exceptions.types;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
