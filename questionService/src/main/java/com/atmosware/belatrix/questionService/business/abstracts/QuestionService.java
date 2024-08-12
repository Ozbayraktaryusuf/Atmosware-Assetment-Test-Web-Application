package com.atmosware.belatrix.questionService.business.abstracts;

import com.atmosware.belatrix.questionService.business.dto.requests.option.AddOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.option.DeleteOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.question.CreateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.question.UpdateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.option.AddOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.option.DeletedOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.*;
import com.atmosware.belatrix.questionService.entities.concretes.Question;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface QuestionService {
    CreatedQuestionResponse add(CreateQuestionRequest createQuestionRequest, HttpServletRequest request);

    Page<GetAllQuestionResponse> getAll(int page ,int size);

    Page<GetAllQuestionResponse> getAll(int page,int size,HttpServletRequest request);

    UpdatedQuestionResponse update(UpdateQuestionRequest updateQuestionRequest, Long id);

    UpdatedQuestionResponse update(UpdateQuestionRequest updateQuestionRequest, Long id, HttpServletRequest request);

    DeleteQuestionResponse delete(Long id);

    DeleteQuestionResponse delete(Long id, HttpServletRequest request);

    AddOptionResponse addOptionToQuestion(AddOptionRequest request, Long id);

    AddOptionResponse addOptionToQuestion(AddOptionRequest addOptionRequest, Long id, HttpServletRequest httpServletRequest);

    DeletedOptionResponse deleteOptionOfQuestion(DeleteOptionRequest deleteOptionRequest, Long id);

    DeletedOptionResponse deleteOptionOfQuestion(DeleteOptionRequest deleteOptionRequest, Long id, HttpServletRequest httpServletRequest);
    GetByIdQuestionResponse getById(Long id);
    GetByIdQuestionResponse getById(Long id,HttpServletRequest request);
    List<Question> getAllQuestionsWithSpecificOrganizationIdForGrpc(UUID organizationId);
}
