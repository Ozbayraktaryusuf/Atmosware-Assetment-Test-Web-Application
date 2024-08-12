package com.atmosware.belatrix.managmentService.business.dto.responses.organization;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetAllOrganizationResponse(
        UUID id,
        String organizationName,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
