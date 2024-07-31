package com.atmosware.belatrix.questionService.business.dto.responses.question;

import com.atmosware.belatrix.questionService.entities.enums.Updatable;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetAllQuestionResponse(
        Long id,
        UUID organizationId,
        LocalDateTime createdDate,
        LocalDateTime updatedDate,
        Updatable updatable
) {
}
