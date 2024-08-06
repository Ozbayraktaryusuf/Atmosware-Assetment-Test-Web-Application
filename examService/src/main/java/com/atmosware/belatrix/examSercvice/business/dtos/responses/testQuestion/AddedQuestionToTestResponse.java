package com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion;

import java.time.LocalDateTime;

public record AddedQuestionToTestResponse(
        Long id,
        Long testId,
        Long questionId,
        LocalDateTime createdDate
) {
}
