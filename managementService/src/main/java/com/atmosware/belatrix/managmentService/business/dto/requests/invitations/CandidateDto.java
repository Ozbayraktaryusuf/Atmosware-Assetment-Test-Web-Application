package com.atmosware.belatrix.managmentService.business.dto.requests.invitations;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CandidateDto(
        @NotNull
        @Email
        String email
) {
}
