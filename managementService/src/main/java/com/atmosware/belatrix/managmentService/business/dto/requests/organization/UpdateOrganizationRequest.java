package com.atmosware.belatrix.managmentService.business.dto.requests.organization;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateOrganizationRequest(
        @NotNull
        @Size(min = 2,max = 50)
        String organizationName
) {
}
