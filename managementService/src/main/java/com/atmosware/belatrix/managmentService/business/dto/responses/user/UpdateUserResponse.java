package com.atmosware.belatrix.managmentService.business.dto.responses.user;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateUserResponse(
        String email,
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime updatedDate,
        String organizationName
) {
}
