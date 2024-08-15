package com.atmosware.belatrix.questionService.business.dto.responses.question;

import com.atmosware.belatrix.questionService.business.dto.responses.option.CreatedOptionResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class CreatedQuestionResponse {
    Long id;
    UUID organizationId;
    LocalDateTime createdDate;
    List<CreatedOptionResponse> createOptionResponse;
}
