package com.atmosware.belatrix.examSercvice.business.dtos.responses.test;

import java.time.LocalDateTime;
import java.util.UUID;

public record ExtendedEndDateResponse(
        Long id,
        UUID organizationId,
        String examName,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Long duration,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
