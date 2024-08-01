package com.atmosware.belatrix.questionService.business.dto.requests.option;

import jakarta.validation.constraints.NotNull;

public record DeleteOptionRequest(
        @NotNull
        Long id
) {
}
