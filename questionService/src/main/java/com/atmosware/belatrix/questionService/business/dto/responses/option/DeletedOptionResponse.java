package com.atmosware.belatrix.questionService.business.dto.responses.option;

import java.time.LocalDateTime;

public record DeletedOptionResponse(
        Long id,
        Long questionId,
        String text,
        LocalDateTime createdDate,
        LocalDateTime updatedDate,
        LocalDateTime deletedDate,
        boolean correct
) {
}
