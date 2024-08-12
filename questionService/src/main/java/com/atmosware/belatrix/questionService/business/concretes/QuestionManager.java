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
import com.atmosware.belatrix.questionService.entities.concretes.Question;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        this.questionRepository.save(question);

        List<CreatedOptionResponse> createdOptionResponse = this.optionService.add(createQuestionRequest.createOptionRequests(), question);

        CreatedQuestionResponse createdQuestionResponse = this.questionMapper.toCreatedQuestionResponse(question);
        createdQuestionResponse.setCreateOptionResponse(createdOptionResponse);

        return createdQuestionResponse;
    }

    @Override
    public Page<GetAllQuestionResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Question> questionList = this.questionRepository.findAll(pageable);

        return questionList.map(this.questionMapper::toGetAllQuestionResponse);
    }

    @Override
    public Page<GetAllQuestionResponse> getAll(int size, int page ,HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page,size,Sort.by("id"));
        UUID id = this.extractOrganizationIdFromToken(request);

        Page<Question> questionList = this.questionRepository.findByOrganizationIdOrOrganizationIdIsNull(id,pageable);

        return questionList.map(this.questionMapper::toGetAllQuestionResponse);
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

    @Override
    public List<Question> getAllQuestionsWithSpecificOrganizationIdForGrpc(UUID organizationId) {
        return this.questionRepository.findByOrganizationIdOrOrganizationIdIsNull(organizationId);
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
