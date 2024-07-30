package com.atmosware.belatrix.questionService.business.dto.requests.question;

import com.atmosware.belatrix.questionService.business.dto.requests.option.CreateOptionRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CreateQuestionRequest(
        @NotNull
        @Size(max = 2000,min = 5)
        String text,
        @NotNull
        UUID organizationId,
        @Size(min = 2,max = 5)
        @Valid
        @NotEmpty
        List<CreateOptionRequest> createOptionRequests
) {
}
