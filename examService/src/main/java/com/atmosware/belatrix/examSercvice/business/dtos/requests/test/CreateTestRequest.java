package com.atmosware.belatrix.examSercvice.business.dtos.requests.test;

import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.CreateTestQuestionRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record CreateTestRequest(
        @NotNull
        @Size(min = 5,max = 100)
        String examName,
        @NotNull
        LocalDateTime startDate,
        @NotNull
        LocalDateTime endDate,
        @NotNull
        @Size(max = 60)
        Long duration,
        @Valid
        @NotEmpty
        List<CreateTestQuestionRequest> createTestQuestionRequests
) {
}
