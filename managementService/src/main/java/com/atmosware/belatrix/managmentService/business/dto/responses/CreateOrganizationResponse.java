package com.atmosware.belatrix.managmentService.business.dto.responses;

import java.time.LocalDate;
import java.util.UUID;

public record CreateOrganizationResponse(
        UUID id,
        String organizationName,
        String email,
        String password,
        LocalDate createdDate
) {
}
