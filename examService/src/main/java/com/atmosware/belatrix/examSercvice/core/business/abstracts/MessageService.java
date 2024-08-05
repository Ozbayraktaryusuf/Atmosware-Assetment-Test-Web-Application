package com.atmosware.belatrix.examSercvice.core.business.abstracts;

public interface MessageService {
    String getMessage(String key);

    String getMessage(String key, Object[] args);
}