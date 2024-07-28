package com.atmosware.belatrix.managmentService.business.dto.requests.userRole;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateUserRoleRequest(
        @NotNull
        UUID userId,
        @NotNull
        UUID roleId

) {
}
