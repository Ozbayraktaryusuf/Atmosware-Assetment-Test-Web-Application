package com.atmosware.belatrix.questionService.business.mappers;

import com.atmosware.belatrix.questionService.business.dto.requests.question.CreateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.question.UpdateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.question.*;
import com.atmosware.belatrix.questionService.core.mappping.MapstructService;
import com.atmosware.belatrix.questionService.entities.concretes.Question;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MapstructService.class)
public interface QuestionMapper {
    //@Mapping(source = "organizationId",target = "organizationId")
    Question toQuestion(CreateQuestionRequest createQuestionRequest);

    CreatedQuestionResponse toCreatedQuestionResponse(Question question);

    GetAllQuestionResponse toGetAllQuestionResponse  (Question question);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateQuestionFromRequest(UpdateQuestionRequest updateQuestionRequest, @MappingTarget Question question);

    UpdatedQuestionResponse toUpdateQuestionResponse(Question question);

    DeleteQuestionResponse toDeleteQuestionResponse(Question question);

    GetByIdQuestionResponse toGetByIdQuestionResponse(Question Question);
}
