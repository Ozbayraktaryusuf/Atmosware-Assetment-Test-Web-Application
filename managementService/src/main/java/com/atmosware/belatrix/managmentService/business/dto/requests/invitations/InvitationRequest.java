package com.atmosware.belatrix.managmentService.business.dto.requests.invitations;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record InvitationRequest(
        @NotNull
        Long examId,
        @NotNull
        @Valid
        List<CandidateDto> candidates
) {
}
