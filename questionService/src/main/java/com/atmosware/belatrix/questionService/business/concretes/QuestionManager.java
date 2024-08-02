package com.atmosware.belatrix.questionService.business.concretes;

import com.atmosware.belatrix.core.services.JwtService;
import com.atmosware.belatrix.questionService.business.abstracts.OptionService;
import com.atmosware.belatrix.questionService.business.abstracts.QuestionService;
import com.atmosware.belatrix.questionService.business.dto.dtos.OptionDto;
import com.atmosware.belatrix.questionService.business.dto.requests.option.AddOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.option.DeleteOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.question.CreateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.question.UpdateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.option.AddOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.option.CreatedOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.option.DeletedOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.option.UpdatedOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.*;
import com.atmosware.belatrix.questionService.business.mappers.QuestionMapper;
import com.atmosware.belatrix.questionService.business.rules.QuestionBusinessRules;
import com.atmosware.belatrix.questionService.dataAccess.QuestionRepository;
import com.atmosware.belatrix.questionService.entities.concretes.Option;
import com.atmosware.belatrix.questionService.entities.concretes.Question;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionManager implements QuestionService {
    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;
    private final OptionService optionService;
    private final JwtService jwtService;
    private final QuestionBusinessRules questionBusinessRules;

    @Override
    @Transactional
    public CreatedQuestionResponse add(CreateQuestionRequest createQuestionRequest, HttpServletRequest request) {
        Question question = this.questionMapper.toQuestion(createQuestionRequest);

        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);

        List<String> roles = this.jwtService.extractRoles(token);

        if (roles.get(0).equals("organization")) {
            UUID organizationId = UUID.fromString(this.jwtService.getClaims(token).get("organizationId").toString());
            question.setOrganizationId(organizationId);
        }

        Question createdQuestion = this.questionRepository.save(question);

        List<CreatedOptionResponse> createdOptionResponse = this.optionService.add(createQuestionRequest.createOptionRequests(), question);

        CreatedQuestionResponse createdQuestionResponse = this.questionMapper.toCreatedQuestionResponse(question);
        createdQuestionResponse.setCreateOptionResponse(createdOptionResponse);

        return createdQuestionResponse;
    }

    @Override
    public List<GetAllQuestionResponse> getAll() {
        //TODO: paging yapmayı unutma
        List<Question> questionList = this.questionRepository.findAll();

        return this.questionMapper.toGetAllQuestionResponse(questionList);
    }

    @Override
    public List<GetAllQuestionResponse> getAll(HttpServletRequest request) {
        UUID id = this.extractOrganizationIdFromToken(request);
        List<Question> questionList = this.questionRepository.findByOrganizationIdOrOrganizationIdIsNull(id);

        return this.questionMapper.toGetAllQuestionResponse(questionList);
    }

    @Override
    @Transactional
    public UpdatedQuestionResponse update(UpdateQuestionRequest updateQuestionRequest, Long id) {
        this.questionBusinessRules.questionShouldBeExists(id);
        Question question = this.questionRepository.findById(id).get();
        this.questionBusinessRules.questionShouldBeUpdatable(question);

        List<UpdatedOptionResponse> updatedOptionResponses = this.optionService.update(updateQuestionRequest.updateOptionRequests(), question);

        this.questionMapper.updateQuestionFromRequest(updateQuestionRequest, question);
        this.questionRepository.save(question);

        UpdatedQuestionResponse updatedQuestionResponse = this.questionMapper.toUpdateQuestionResponse(question);
        updatedQuestionResponse.setUpdatedOptionResponses(updatedOptionResponses);

        return updatedQuestionResponse;
    }

    @Override
    public UpdatedQuestionResponse update(UpdateQuestionRequest updateQuestionRequest, Long id, HttpServletRequest request) {
        this.questionBusinessRules.questionShouldBeExists(id);

        UUID organizationId = this.extractOrganizationIdFromToken(request);

        this.questionBusinessRules.questionShouldBelongToSameOrganization(id, organizationId);
        Question question = this.questionRepository.findById(id).get();
        this.questionBusinessRules.questionShouldBeUpdatable(question);

        List<UpdatedOptionResponse> updatedOptionResponses = this.optionService.update(updateQuestionRequest.updateOptionRequests(), question);

        this.questionMapper.updateQuestionFromRequest(updateQuestionRequest, question);
        this.questionRepository.save(question);

        UpdatedQuestionResponse updatedQuestionResponse = this.questionMapper.toUpdateQuestionResponse(question);
        updatedQuestionResponse.setUpdatedOptionResponses(updatedOptionResponses);

        return updatedQuestionResponse;
    }

    @Override
    public DeleteQuestionResponse delete(Long id) {
        this.questionBusinessRules.questionShouldBeExists(id);

        Question question = this.questionRepository.findById(id).get();
        question.setDeletedDate(LocalDateTime.now());
        this.optionService.delete(question);

        return this.questionMapper.toDeleteQuestionResponse(this.questionRepository.save(question));
    }

    @Override
    @Transactional
    public DeleteQuestionResponse delete(Long id, HttpServletRequest request) {
        this.questionBusinessRules.questionShouldBeExists(id);

        UUID organizationId = this.extractOrganizationIdFromToken(request);

        this.questionBusinessRules.questionShouldBelongToSameOrganization(id, organizationId);

        Question question = this.questionRepository.findByIdAndOrganizationId(id, organizationId).get();
        question.setDeletedDate(LocalDateTime.now());
        this.optionService.delete(question);

        return this.questionMapper.toDeleteQuestionResponse(question);
    }

    @Override
    public AddOptionResponse addOptionToQuestion(AddOptionRequest request, Long id) {
        this.questionBusinessRules.questionShouldBeExists(id);
        Question question = this.questionRepository.findById(id).get();

        return this.optionService.addOption(request, question);
    }

    @Override
    public AddOptionResponse addOptionToQuestion(AddOptionRequest addOptionRequest, Long id, HttpServletRequest httpServletRequest) {
        this.questionBusinessRules.questionShouldBeExists(id);

        UUID organizationId = this.extractOrganizationIdFromToken(httpServletRequest);

        this.questionBusinessRules.questionShouldBelongToSameOrganization(id, organizationId);
        Question question = this.questionRepository.findById(id).get();

        return this.optionService.addOption(addOptionRequest, question);
    }

    @Override
    public DeletedOptionResponse deleteOptionOfQuestion(DeleteOptionRequest deleteOptionRequest, Long id) {
        this.questionBusinessRules.questionShouldBeExists(id);

        Question question = this.questionRepository.findById(id).get();
        return this.optionService.deleteOption(deleteOptionRequest, question);
    }

    @Override
    public DeletedOptionResponse deleteOptionOfQuestion(DeleteOptionRequest deleteOptionRequest, Long id, HttpServletRequest httpServletRequest) {
        this.questionBusinessRules.questionShouldBeExists(id);

        UUID organizationId = this.extractOrganizationIdFromToken(httpServletRequest);

        this.questionBusinessRules.questionShouldBelongToSameOrganization(id, organizationId);
        Question question = this.questionRepository.findById(id).get();

        return this.optionService.deleteOption(deleteOptionRequest, question);
    }

    @Override
    public GetByIdQuestionResponse getById(Long id) {
        this.questionBusinessRules.questionShouldBeExists(id);

        Question question = this.questionRepository.findById(id).get();

        List<OptionDto> optionDtos = this.optionService.optionDtoForQuestionGetById(question);

        GetByIdQuestionResponse getByIdQuestionResponse = this.questionMapper.toGetByIdQuestionResponse(question);
        getByIdQuestionResponse.setOptionDto(optionDtos);

        return getByIdQuestionResponse;
    }

    @Override
    public GetByIdQuestionResponse getById(Long id, HttpServletRequest request) {
        this.questionBusinessRules.questionShouldBeExists(id);

        UUID organizationId = this.extractOrganizationIdFromToken(request);
        this.questionBusinessRules.questionShouldBelongToSameOrganization(id, organizationId);

        Question question = this.questionRepository.findById(id).get();

        List<OptionDto> optionDtos = this.optionService.optionDtoForQuestionGetById(question);

        GetByIdQuestionResponse getByIdQuestionResponse = this.questionMapper.toGetByIdQuestionResponse(question);
        getByIdQuestionResponse.setOptionDto(optionDtos);

        return getByIdQuestionResponse;
    }

    private UUID extractOrganizationIdFromToken(HttpServletRequest httpServletRequest) {
        return UUID.fromString(jwtService
                .getClaims(
                        httpServletRequest
                                .getHeader(HttpHeaders.AUTHORIZATION)
                                .substring(7))
                .get("organizationId")
                .toString());
    }
}
