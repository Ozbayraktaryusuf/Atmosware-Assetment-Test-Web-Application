package com.atmosware.belatrix.examSercvice.business.mappers;

import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.AddQuestionToTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.CreateTestQuestionRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.AddedQuestionToTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.CreatedTestQuestionResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.DeletedQuestionFromTestResponse;
import com.atmosware.belatrix.examSercvice.core.mappping.MapstructService;
import com.atmosware.belatrix.examSercvice.entities.concretes.TestQuestion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructService.class)
public interface TestQuestionMapper {
    TestQuestion toTestQuestion(CreateTestQuestionRequest createTestQuestionRequest);

    List<TestQuestion> toTestQuestion(List<CreateTestQuestionRequest> createTestQuestionRequests);

    CreatedTestQuestionResponse toCreatedTestQuestionResponse(TestQuestion testQuestion);

    List<CreatedTestQuestionResponse> toCreatedTestQuestionResponse(List<TestQuestion> testQuestions);

    AddedQuestionToTestResponse toAddedQuestionToTestResponse(TestQuestion testQuestion);

    DeletedQuestionFromTestResponse toDeletedQuestionFromTestResponse(TestQuestion testQuestion);
}
