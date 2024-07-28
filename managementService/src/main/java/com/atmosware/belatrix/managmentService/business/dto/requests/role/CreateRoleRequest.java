package com.atmosware.belatrix.managmentService.business.dto.requests.role;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateRoleRequest(
        @NotNull
        @Size(min = 2,max = 15)
        String name
) {
}
