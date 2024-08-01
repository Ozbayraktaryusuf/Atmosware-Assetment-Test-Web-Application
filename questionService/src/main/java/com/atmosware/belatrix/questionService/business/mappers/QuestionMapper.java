package com.atmosware.belatrix.questionService.business.mappers;

import com.atmosware.belatrix.questionService.business.dto.requests.question.CreateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.question.UpdateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.question.CreatedQuestionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.DeleteQuestionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.GetAllQuestionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.UpdatedQuestionResponse;
import com.atmosware.belatrix.questionService.core.mappping.MapstructService;
import com.atmosware.belatrix.questionService.entities.concretes.Question;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(config = MapstructService.class)
public interface QuestionMapper {
    //@Mapping(source = "organizationId",target = "organizationId")
    Question toQuestion(CreateQuestionRequest createQuestionRequest);

    CreatedQuestionResponse toCreatedQuestionResponse(Question question);

    GetAllQuestionResponse toGetAllQuestionResponse  (Question question);

    List<GetAllQuestionResponse> toGetAllQuestionResponse(List<Question> question);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateQuestionFromRequest(UpdateQuestionRequest updateQuestionRequest, @MappingTarget Question question);

    UpdatedQuestionResponse toUpdateQuestionResponse(Question question);

    DeleteQuestionResponse toDeleteQuestionResponse(Question question);
}
