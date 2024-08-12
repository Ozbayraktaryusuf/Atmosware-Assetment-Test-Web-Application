package com.atmosware.belatrix.examSercvice.business.dtos.requests.test;

import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.CreateTestQuestionRequest;
import com.atmosware.belatrix.examSercvice.core.utils.annotations.UniqueQuestionIds;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
//TODO: Uygun mu Sor. Bir de mesaj dönmüyor.
@UniqueQuestionIds()
public record CreateTestRequest(
        @NotNull
        @Size(min = 5,max = 100)
        String examName,
        @NotNull
        LocalDateTime startDate,
        @NotNull
        LocalDateTime endDate,
        @NotNull
        @Max(value = 120,message = "Exam can't be longer than 120 minutes.")
        Long duration,
        @Valid
        @NotEmpty
        List<CreateTestQuestionRequest> createTestQuestionRequests
) {
}
