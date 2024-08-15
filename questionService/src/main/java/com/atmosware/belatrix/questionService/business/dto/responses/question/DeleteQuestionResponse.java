package com.atmosware.belatrix.questionService.business.dto.responses.question;


import java.time.LocalDateTime;
import java.util.UUID;
public record DeleteQuestionResponse(
        Long id,
        UUID organizationId,
        LocalDateTime createdDate,
        LocalDateTime updatedDate,
        LocalDateTime deletedDate
){
}
