package com.atmosware.belatrix.managmentService.business.dto.responses;

import java.util.UUID;

public record CreateUserRoleResponse(
        UUID userId,
        UUID roleId
) {
}
