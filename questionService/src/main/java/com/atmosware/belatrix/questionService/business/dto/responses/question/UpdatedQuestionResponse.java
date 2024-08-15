package com.atmosware.belatrix.questionService.business.dto.responses.question;

import com.atmosware.belatrix.questionService.business.dto.responses.option.UpdatedOptionResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class UpdatedQuestionResponse {
    Long id;
    UUID organizationId;
    LocalDateTime createdDate;
    List<UpdatedOptionResponse> updatedOptionResponses;
}
