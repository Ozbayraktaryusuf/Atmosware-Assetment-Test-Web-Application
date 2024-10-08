package com.atmosware.belatrix.managmentService.business.dto.responses.organization;

import java.time.LocalDate;
import java.util.UUID;

public record CreateOrganizationResponse(
        UUID id,
        String organizationName,
        LocalDate createdDate
) {
}
