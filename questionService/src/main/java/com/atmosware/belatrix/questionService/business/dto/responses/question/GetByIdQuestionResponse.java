package com.atmosware.belatrix.questionService.business.dto.responses.question;

import com.atmosware.belatrix.questionService.business.dto.dtos.OptionDto;

import com.atmosware.belatrix.questionService.entities.enums.Updatable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class GetByIdQuestionResponse {
    Long id;
    UUID organizationId;
    LocalDateTime createdDate;
    Updatable updatable;
    List<OptionDto> optionDto;
}
