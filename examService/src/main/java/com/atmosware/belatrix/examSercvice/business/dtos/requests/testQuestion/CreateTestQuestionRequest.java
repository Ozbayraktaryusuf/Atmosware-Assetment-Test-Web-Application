package com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion;

import jakarta.validation.constraints.NotNull;

public record CreateTestQuestionRequest(
        @NotNull
        Long questionId
) {
}
