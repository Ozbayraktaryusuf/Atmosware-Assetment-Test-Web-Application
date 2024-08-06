package com.atmosware.belatrix.examSercvice.business.dtos.responses.rule;

import java.time.LocalDateTime;

public record GetByIdRuleResponse(
        Long id,
        String description,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
