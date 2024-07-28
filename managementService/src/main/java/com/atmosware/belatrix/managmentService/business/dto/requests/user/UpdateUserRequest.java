package com.atmosware.belatrix.managmentService.business.dto.requests.user;

import jakarta.validation.constraints.NotNull;

public record UpdateUserRequest(
        @NotNull
        String password
) {
}
