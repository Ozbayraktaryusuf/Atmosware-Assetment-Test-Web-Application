package com.atmosware.belatrix.examSercvice.business.dtos.requests.test;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ExtendEndDateRequest(
        @NotNull
        LocalDateTime endDate
) {
}
