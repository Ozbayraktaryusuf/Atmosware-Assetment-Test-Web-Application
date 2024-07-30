package com.atmosware.belatrix.questionService.business.dto.responses.option;

public record CreatedOptionResponse(
        Long id,
        String text,
        boolean correct
) {
}
