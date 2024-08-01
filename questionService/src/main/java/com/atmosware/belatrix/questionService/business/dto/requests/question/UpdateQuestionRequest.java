package com.atmosware.belatrix.questionService.business.dto.requests.question;


import com.atmosware.belatrix.questionService.business.dto.requests.option.UpdateOptionRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateQuestionRequest(
        @NotNull
        @Size(max = 2000,min = 5)
        String text,
        @Size(min = 2,max = 5)
        @Valid
        @NotEmpty
        List<UpdateOptionRequest> updateOptionRequests
) {
}
