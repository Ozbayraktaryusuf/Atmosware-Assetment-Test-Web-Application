package com.atmosware.belatrix.managmentService.business.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotNull
        @Email
        String email,

        @NotNull
        @Size(min = 6, max = 16)
        String password
) {
}

