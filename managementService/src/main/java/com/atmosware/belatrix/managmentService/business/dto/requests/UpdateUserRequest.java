package com.atmosware.belatrix.managmentService.business.dto.requests;

import jakarta.validation.constraints.NotNull;

public record UpdateUserRequest(
        @NotNull
        String password
) {
}
