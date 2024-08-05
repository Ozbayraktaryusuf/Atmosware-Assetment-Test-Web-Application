package com.atmosware.belatrix.examSercvice.business.abstracts;

import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.CreateTestQuestionRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.CreatedTestQuestionResponse;
import com.atmosware.belatrix.examSercvice.entities.concretes.Test;

import java.util.List;

public interface TestQuestionService {
    List<CreatedTestQuestionResponse> createTestQuestion(List<CreateTestQuestionRequest> createTestQuestionRequests, Test test);
}
