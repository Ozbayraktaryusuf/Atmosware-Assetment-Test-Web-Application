package com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule;

import java.time.LocalDateTime;

public record AddRuleToTestResponse(
        Long id,
        Long testId,
        Long ruleId,
        LocalDateTime createdDate
) {
}
