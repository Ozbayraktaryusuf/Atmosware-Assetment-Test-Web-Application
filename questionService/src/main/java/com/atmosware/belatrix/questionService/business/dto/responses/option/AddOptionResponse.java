package com.atmosware.belatrix.questionService.business.dto.responses.option;

public record AddOptionResponse(
        Long id,
        Long questionId,
        String text,
        boolean correct
) {
}
