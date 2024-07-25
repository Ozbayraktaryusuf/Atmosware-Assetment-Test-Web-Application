package com.atmosware.belatrix.managmentService.business.dto.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RegisterUserDto(
        @NotNull
        @Email
        String email,
        @NotNull
        @Size(min = 6,max = 16)
        String password
) {
}
