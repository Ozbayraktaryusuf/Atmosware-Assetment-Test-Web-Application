package com.atmosware.belatrix.questionService.core.business.abstracts;

public interface MessageService {
    String getMessage(String key);

    String getMessage(String key, Object[] args);
}