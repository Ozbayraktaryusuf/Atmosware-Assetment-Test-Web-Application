package com.atmosware.belatrix.managmentService.business.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateOrganizationRequest(
    @NotNull
    @Size(min = 2,max = 50)
    String organizationName,
    @NotNull
    @Email
    String email,
    @NotNull
    @Size(min = 8,max = 16)
    String password
) {
}
