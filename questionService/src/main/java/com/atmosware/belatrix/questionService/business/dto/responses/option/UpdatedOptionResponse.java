package com.atmosware.belatrix.questionService.business.dto.responses.option;

import java.time.LocalDateTime;

public record UpdatedOptionResponse(
        Long id,
        String text,
        LocalDateTime updatedDate,
        boolean correct
) {
}
