package com.atmosware.belatrix.managmentService.business.dto.responses.userRole;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateUserRoleResponse(
        UUID id,
        UUID userId,
        UUID roleId,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
){
}
