package com.atmosware.belatrix.examSercvice.business.dtos.responses.test;

import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.CreatedTestQuestionResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Setter
@Getter
public class CreatedTestResponse {
    private Long id;
    private UUID organizationId;
    private String examName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long duration;
    private List<CreatedTestQuestionResponse> createdTestQuestionResponses;
}
