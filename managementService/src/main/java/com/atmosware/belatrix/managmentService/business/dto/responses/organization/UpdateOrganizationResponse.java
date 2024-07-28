package com.atmosware.belatrix.managmentService.business.dto.responses.organization;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateOrganizationResponse(
        UUID id,
        String organizationName,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
