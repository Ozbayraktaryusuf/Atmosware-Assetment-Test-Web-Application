package com.atmosware.belatrix.examSercvice.business.abstracts;

import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.AddQuestionToTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.CreateTestQuestionRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.AddedQuestionToTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.CreatedTestQuestionResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.DeletedQuestionFromTestResponse;
import com.atmosware.belatrix.examSercvice.entities.concretes.Test;
import com.atmosware.belatrix.examSercvice.entities.concretes.TestQuestion;

import java.util.List;

public interface TestQuestionService {
    List<CreatedTestQuestionResponse> createTestQuestion(List<CreateTestQuestionRequest> createTestQuestionRequests, Test test);
    AddedQuestionToTestResponse addQuestionToTest(Long questionId,Test test);
    DeletedQuestionFromTestResponse deleteQuestionFromTest(Long questionId, Test test);
    void deleteAll(List<TestQuestion> testQuestions);
}
