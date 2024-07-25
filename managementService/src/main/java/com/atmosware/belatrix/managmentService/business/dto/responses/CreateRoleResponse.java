package com.atmosware.belatrix.managmentService.business.dto.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateRoleResponse(
        UUID id,
        String name,
        LocalDateTime createdDate
) {
}
