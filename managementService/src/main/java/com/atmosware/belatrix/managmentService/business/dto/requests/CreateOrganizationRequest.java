package com.atmosware.belatrix.managmentService.business.dto.requests;

import com.atmosware.belatrix.managmentService.business.dto.dtos.RegisterUserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateOrganizationRequest(
    @NotNull
    @Size(min = 2,max = 50)
    String organizationName,
    @Valid
    RegisterUserDto registerUserDto
) {
}
