package com.atmosware.belatrix.questionService.business.dto.requests.option;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateOptionRequest(
        @NotNull
        @Size(max = 500)
        String text,
        @NotNull
        boolean correct
) {
}
