package com.atmosware.belatrix.examSercvice.business.dtos.requests.test;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UpdateTestRequest(
        @NotNull
        @Size(min = 5,max = 100)
        String examName,
        @NotNull
        LocalDateTime startDate,
        @NotNull
        LocalDateTime endDate,
        @NotNull
        @Max(value = 120,message = "Exam can't be longer than 120 minutes.")
        Long duration
) {
}
