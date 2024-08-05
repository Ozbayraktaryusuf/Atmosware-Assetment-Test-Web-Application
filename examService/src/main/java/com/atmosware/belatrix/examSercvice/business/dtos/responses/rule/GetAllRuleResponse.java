package com.atmosware.belatrix.examSercvice.business.dtos.responses.rule;

import java.time.LocalDateTime;

public record GetAllRuleResponse(
        Long id,
        String description,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
