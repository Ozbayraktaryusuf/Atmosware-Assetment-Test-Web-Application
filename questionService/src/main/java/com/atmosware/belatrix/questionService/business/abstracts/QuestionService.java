package com.atmosware.belatrix.questionService.business.abstracts;

import com.atmosware.belatrix.questionService.business.dto.requests.option.AddOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.option.DeleteOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.question.CreateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.question.UpdateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.option.AddOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.option.DeletedOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.CreatedQuestionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.DeleteQuestionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.GetAllQuestionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.UpdatedQuestionResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface QuestionService {
    CreatedQuestionResponse add(CreateQuestionRequest createQuestionRequest, HttpServletRequest request);

    List<GetAllQuestionResponse> getAll();

    List<GetAllQuestionResponse> getAll(HttpServletRequest request);

    UpdatedQuestionResponse update(UpdateQuestionRequest updateQuestionRequest, Long id);

    UpdatedQuestionResponse update(UpdateQuestionRequest updateQuestionRequest, Long id, HttpServletRequest request);

    DeleteQuestionResponse delete(Long id);

    DeleteQuestionResponse delete(Long id, HttpServletRequest request);

    AddOptionResponse addOptionToQuestion(AddOptionRequest request, Long id);

    AddOptionResponse addOptionToQuestion(AddOptionRequest addOptionRequest, Long id, HttpServletRequest httpServletRequest);

    DeletedOptionResponse deleteOptionOfQuestion(DeleteOptionRequest deleteOptionRequest, Long id);

    DeletedOptionResponse deleteOptionOfQuestion(DeleteOptionRequest deleteOptionRequest, Long id, HttpServletRequest httpServletRequest);
}
