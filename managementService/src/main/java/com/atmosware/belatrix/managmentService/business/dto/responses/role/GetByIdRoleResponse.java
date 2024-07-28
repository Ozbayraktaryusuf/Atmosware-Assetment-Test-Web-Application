package com.atmosware.belatrix.managmentService.business.dto.responses.role;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetByIdRoleResponse(
        UUID id,
        String name,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
