package com.atmosware.belatrix.examSercvice.business.dtos.requests.testRule;

import jakarta.validation.constraints.NotNull;

public record AddRuleToTestRequest(
        @NotNull
        Long testId,
        @NotNull
        Long ruleId
) {
}
