package com.atmosware.belatrix.managmentService.business.dto.responses;

import java.util.UUID;

public record CreateUserRoleResponse(
        UUID id,
        UUID userId,
        UUID roleId
) {
}
