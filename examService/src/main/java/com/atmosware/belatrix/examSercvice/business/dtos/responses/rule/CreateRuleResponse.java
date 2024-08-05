package com.atmosware.belatrix.examSercvice.business.dtos.responses.rule;

import java.time.LocalDateTime;

public record CreateRuleResponse(
        Long id,
        String description,
        LocalDateTime createdDate
) {
}
