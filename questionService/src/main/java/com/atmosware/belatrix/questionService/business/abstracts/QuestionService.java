package com.atmosware.belatrix.questionService.business.abstracts;

import com.atmosware.belatrix.questionService.business.dto.requests.question.CreateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.question.CreatedQuestionResponse;

public interface QuestionService {
    CreatedQuestionResponse add(CreateQuestionRequest createQuestionRequest);
}
