package com.atmosware.belatrix.managmentService.business.dto.requests;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateUserRoleRequest(
        @NotNull
        UUID roleId
) {

}
