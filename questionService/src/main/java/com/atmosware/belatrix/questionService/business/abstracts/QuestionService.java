package com.atmosware.belatrix.questionService.business.abstracts;

import com.atmosware.belatrix.questionService.business.dto.requests.question.CreateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.question.CreatedQuestionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.GetAllQuestionResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface QuestionService {
    CreatedQuestionResponse add(CreateQuestionRequest createQuestionRequest, HttpServletRequest request);

    List<GetAllQuestionResponse> getAll();
}
