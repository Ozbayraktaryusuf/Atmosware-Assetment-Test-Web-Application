package com.atmosware.belatrix.managmentService.business.dto.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetByIdUserResponse(
        String email,
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime updatedDate,
        String organizationName
) {
}
