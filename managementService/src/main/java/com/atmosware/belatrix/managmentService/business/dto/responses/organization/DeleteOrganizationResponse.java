package com.atmosware.belatrix.managmentService.business.dto.responses.organization;

import java.time.LocalDateTime;
import java.util.UUID;

public record DeleteOrganizationResponse(
        UUID id,
        String organizationName,
        LocalDateTime createdDate,
        LocalDateTime updatedDate,
        LocalDateTime deletedDate
) {
}
