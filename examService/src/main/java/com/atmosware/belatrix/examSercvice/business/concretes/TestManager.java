package com.atmosware.belatrix.examSercvice.business.concretes;

import com.atmosware.belatrix.core.services.JwtService;
import com.atmosware.belatrix.examSercvice.business.abstracts.TestQuestionService;
import com.atmosware.belatrix.examSercvice.business.abstracts.TestRuleService;
import com.atmosware.belatrix.examSercvice.business.abstracts.TestService;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.test.CreateTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.test.ExtendEndDateRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.test.UpdateTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.AddQuestionToTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.DeleteQuestionFromTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.testRule.AddRuleToTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.testRule.RemoveRuleFromTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.test.*;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.AddedQuestionToTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.CreatedTestQuestionResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.DeletedQuestionFromTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.AddRuleToTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.GetAllTestRuleForTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.RemoveRuleFromTestResponse;
import com.atmosware.belatrix.examSercvice.business.mappers.TestMapper;
import com.atmosware.belatrix.examSercvice.business.rules.TestBusinessRules;
import com.atmosware.belatrix.examSercvice.dataAccess.TestRepository;
import com.atmosware.belatrix.examSercvice.entities.concretes.Test;
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
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestManager implements TestService {
    private final TestQuestionService testQuestionService;
    private final TestMapper testMapper;
    private final TestRepository testRepository;
    private final JwtService jwtService;
    private final TestBusinessRules testBusinessRules;
    private final TestRuleService testRuleService;

    @Override
    @Transactional
    public CreatedTestResponse add(CreateTestRequest createTestRequest, HttpServletRequest httpServletRequest) {
        //TODO: organization sadece kendi sorularını ekleyebilir.
        Test test = this.testMapper.toTest(createTestRequest);
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7);

        List<String> roles = this.jwtService.extractRoles(token);

        if (roles.get(0).equals("organization")) {
            UUID organizationId = UUID.fromString(this.jwtService.getClaims(token).get("organizationId").toString());
            test.setOrganizationId(organizationId);
        }
        this.testRepository.save(test);
        List<CreatedTestQuestionResponse> createdTestQuestionResponses =
                this.testQuestionService.createTestQuestion(createTestRequest.createTestQuestionRequests(), test);

        CreatedTestResponse createdTestResponse = this.testMapper.toCreatedTestResponse(test);
        createdTestResponse.setCreatedTestQuestionResponses(createdTestQuestionResponses);

        return createdTestResponse;
    }

    @Override
    @Transactional
    public Page<GetAllTestResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));

        Page<Test> tests = this.testRepository.findAll(pageable);

        return tests.map(this.testMapper::toGetAllTestResponse);
    }

    @Override
    @Transactional
    public Page<GetAllTestResponse> getAllOrganization(int page, int size, HttpServletRequest httpServletRequest) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));

        UUID organizationId = extractOrganizationIdFromToken(httpServletRequest);

        Page<Test> tests = this.testRepository.findByOrganizationIdOrOrganizationIdIsNull(organizationId, pageable);

        return tests.map(this.testMapper::toGetAllTestResponse);
    }

    @Override
    @Transactional
    public GetByIdTestResponse getById(Long id) {
        //TODO: Tüm sorularıda döndürmeli miyim?
        Optional<Test> optionalTest = this.testRepository.findById(id);

        this.testBusinessRules.testShouldBeExists(optionalTest);

        return this.testMapper.toGetByIdResponse(optionalTest.get());
    }

    @Override
    @Transactional
    public GetByIdTestResponse getByIdOrganization(Long id, HttpServletRequest httpServletRequest) {
        Optional<Test> optionalTest = this.testRepository.findById(id);

        this.testBusinessRules.testShouldBeExists(optionalTest);

        UUID organizationId = extractOrganizationIdFromToken(httpServletRequest);
        this.testBusinessRules.testShouldBelongToSameOrganization(optionalTest.get(), organizationId);

        return this.testMapper.toGetByIdResponse(optionalTest.get());
    }

    @Override
    @Transactional
    public DeleteTestResponse delete(Long id) {
        Optional<Test> optionalTest = this.testRepository.findById(id);

        this.testBusinessRules.testShouldBeExists(optionalTest);

        this.testBusinessRules.testShouldNotBeStarted(optionalTest.get());

        Test test = optionalTest.get();

        test.setDeletedDate(LocalDateTime.now());
        this.testQuestionService.deleteAll(test.getTestQuestions());
        this.testRuleService.deleteAll(test.getTestRules());

        return this.testMapper.toDeletedTestResponse(this.testRepository.save(test));
    }

    @Override
    @Transactional
    public DeleteTestResponse deleteOrganization(Long id, HttpServletRequest httpServletRequest) {
        Optional<Test> optionalTest = this.testRepository.findById(id);

        this.testBusinessRules.testShouldBeExists(optionalTest);

        UUID organizationId = extractOrganizationIdFromToken(httpServletRequest);
        this.testBusinessRules.testShouldBelongToSameOrganization(optionalTest.get(), organizationId);

        this.testBusinessRules.testShouldNotBeStarted(optionalTest.get());

        Test test = optionalTest.get();

        test.setDeletedDate(LocalDateTime.now());
        this.testQuestionService.deleteAll(test.getTestQuestions());
        this.testRuleService.deleteAll(test.getTestRules());

        return this.testMapper.toDeletedTestResponse(this.testRepository.save(test));
    }

    @Override
    @Transactional
    //TODO: test id'yi PathVariable olarak almak mı daha iyi yoksa request içinde mi?
    public AddedQuestionToTestResponse addQuestionToResponse(AddQuestionToTestRequest addQuestionToTestRequest) {
        Optional<Test> optionalTest = this.testRepository.findById(addQuestionToTestRequest.testId());

        this.testBusinessRules.testShouldBeExists(optionalTest);
        this.testBusinessRules.testShouldNotBeStarted(optionalTest.get());

        return this.testQuestionService.addQuestionToTest(addQuestionToTestRequest.questionId(), optionalTest.get());
    }

    @Override
    @Transactional
    public AddedQuestionToTestResponse addQuestionToResponseOrganization(AddQuestionToTestRequest addQuestionToTestRequest, HttpServletRequest httpServletRequest) {
        Optional<Test> optionalTest = this.testRepository.findById(addQuestionToTestRequest.testId());

        this.testBusinessRules.testShouldBeExists(optionalTest);

        UUID organizationId = extractOrganizationIdFromToken(httpServletRequest);
        this.testBusinessRules.testShouldBelongToSameOrganization(optionalTest.get(), organizationId);

        this.testBusinessRules.testShouldNotBeStarted(optionalTest.get());

        return this.testQuestionService.addQuestionToTest(addQuestionToTestRequest.questionId(), optionalTest.get());
    }

    @Override
    @Transactional
    public DeletedQuestionFromTestResponse deleteQuestionFromTest(DeleteQuestionFromTestRequest deleteQuestionFromTestRequest) {
        Optional<Test> optionalTest = this.testRepository.findById(deleteQuestionFromTestRequest.testId());

        this.testBusinessRules.testShouldBeExists(optionalTest);
        this.testBusinessRules.testShouldNotBeStarted(optionalTest.get());

        return this.testQuestionService.deleteQuestionFromTest(deleteQuestionFromTestRequest.questionId(), optionalTest.get());
    }

    @Override
    @Transactional
    public DeletedQuestionFromTestResponse deleteQuestionFromTestOrganization(DeleteQuestionFromTestRequest deleteQuestionFromTestRequest, HttpServletRequest httpServletRequest) {
        Optional<Test> optionalTest = this.testRepository.findById(deleteQuestionFromTestRequest.testId());

        this.testBusinessRules.testShouldBeExists(optionalTest);

        UUID organizationId = extractOrganizationIdFromToken(httpServletRequest);
        this.testBusinessRules.testShouldBelongToSameOrganization(optionalTest.get(), organizationId);

        this.testBusinessRules.testShouldNotBeStarted(optionalTest.get());

        return this.testQuestionService.deleteQuestionFromTest(deleteQuestionFromTestRequest.questionId(), optionalTest.get());
    }

    @Override
    @Transactional
    public UpdatedTestResponse update(Long id, UpdateTestRequest updateTestRequest) {
        Optional<Test> optionalTest = this.testRepository.findById(id);

        this.testBusinessRules.testShouldBeExists(optionalTest);
        this.testBusinessRules.testShouldNotBeStarted(optionalTest.get());

        Test test = optionalTest.get();

        this.testMapper.updateTestFromRequest(updateTestRequest, test);

        return this.testMapper.toUpdatedTestResponse(this.testRepository.save(test));
    }

    @Override
    @Transactional
    public UpdatedTestResponse updateOrganization(Long id, UpdateTestRequest updateTestRequest, HttpServletRequest httpServletRequest) {
        Optional<Test> optionalTest = this.testRepository.findById(id);

        this.testBusinessRules.testShouldBeExists(optionalTest);

        UUID organizationId = extractOrganizationIdFromToken(httpServletRequest);
        this.testBusinessRules.testShouldBelongToSameOrganization(optionalTest.get(), organizationId);

        this.testBusinessRules.testShouldNotBeStarted(optionalTest.get());

        Test test = optionalTest.get();

        this.testMapper.updateTestFromRequest(updateTestRequest, test);

        return this.testMapper.toUpdatedTestResponse(this.testRepository.save(test));
    }

    @Override
    @Transactional
    public ExtendedEndDateResponse extendEndDate(Long id, ExtendEndDateRequest extendEndDateRequest) {
        Optional<Test> optionalTest = this.testRepository.findById(id);

        this.testBusinessRules.testShouldBeExists(optionalTest);
        this.testBusinessRules.testShouldBeStartedButNotEnded(optionalTest.get());

        Test test = optionalTest.get();
        test.setEndDate(extendEndDateRequest.endDate());

        return this.testMapper.toExtendedEndDateResponse(this.testRepository.save(test));
    }

    @Override
    @Transactional
    public ExtendedEndDateResponse extendEndDateOrganization(Long id, ExtendEndDateRequest extendEndDateRequest, HttpServletRequest httpServletRequest) {
        Optional<Test> optionalTest = this.testRepository.findById(id);

        this.testBusinessRules.testShouldBeExists(optionalTest);

        UUID organizationId = extractOrganizationIdFromToken(httpServletRequest);
        this.testBusinessRules.testShouldBelongToSameOrganization(optionalTest.get(), organizationId);

        this.testBusinessRules.testShouldBeStartedButNotEnded(optionalTest.get());

        Test test = optionalTest.get();
        test.setEndDate(extendEndDateRequest.endDate());

        return this.testMapper.toExtendedEndDateResponse(this.testRepository.save(test));
    }

    @Override
    @Transactional
    public AddRuleToTestResponse addRuleToTest(AddRuleToTestRequest addRuleToTestRequest) {
        Optional<Test> optionalTest = this.testRepository.findById(addRuleToTestRequest.testId());

        this.testBusinessRules.testShouldBeExists(optionalTest);
        this.testBusinessRules.testShouldNotBeStarted(optionalTest.get());

        return this.testRuleService.addRuleToTest(addRuleToTestRequest.ruleId(), optionalTest.get());
    }

    @Override
    @Transactional
    public AddRuleToTestResponse addRuleToTestOrganization(AddRuleToTestRequest addRuleToTestRequest, HttpServletRequest httpServletRequest) {
        Optional<Test> optionalTest = this.testRepository.findById(addRuleToTestRequest.testId());

        this.testBusinessRules.testShouldBeExists(optionalTest);

        UUID organizationId = extractOrganizationIdFromToken(httpServletRequest);
        this.testBusinessRules.testShouldBelongToSameOrganization(optionalTest.get(), organizationId);

        this.testBusinessRules.testShouldNotBeStarted(optionalTest.get());

        return this.testRuleService.addRuleToTest(addRuleToTestRequest.ruleId(), optionalTest.get());
    }

    @Override
    @Transactional
    public RemoveRuleFromTestResponse removeRuleFromTest(RemoveRuleFromTestRequest removeRuleFromTestRequest) {
        Optional<Test> optionalTest = this.testRepository.findById(removeRuleFromTestRequest.testId());

        this.testBusinessRules.testShouldBeExists(optionalTest);
        this.testBusinessRules.testShouldNotBeStarted(optionalTest.get());

        return this.testRuleService.removeRuleFromTest(removeRuleFromTestRequest.ruleId(),optionalTest.get());
    }

    @Override
    @Transactional
    public RemoveRuleFromTestResponse removeRuleFromTestOrganization(RemoveRuleFromTestRequest removeRuleFromTestRequest, HttpServletRequest httpServletRequest) {
        Optional<Test> optionalTest = this.testRepository.findById(removeRuleFromTestRequest.testId());

        this.testBusinessRules.testShouldBeExists(optionalTest);

        UUID organizationId = extractOrganizationIdFromToken(httpServletRequest);
        this.testBusinessRules.testShouldBelongToSameOrganization(optionalTest.get(), organizationId);

        this.testBusinessRules.testShouldNotBeStarted(optionalTest.get());

        return this.testRuleService.removeRuleFromTest(removeRuleFromTestRequest.ruleId(),optionalTest.get());
    }

    @Override
    public Page<GetAllTestRuleForTestResponse> getAllTestRuleForTest(Long testId, int page, int size) {
        Optional<Test> optionalTest = this.testRepository.findById(testId);

        this.testBusinessRules.testShouldBeExists(optionalTest);

        return this.testRuleService.getAll(page, size, testId);
    }

    @Override
    public Page<GetAllTestRuleForTestResponse> getAllTestRuleForTestOrganization(Long testId, int page, int size, HttpServletRequest httpServletRequest) {
        Optional<Test> optionalTest = this.testRepository.findById(testId);

        this.testBusinessRules.testShouldBeExists(optionalTest);

        UUID organizationId = extractOrganizationIdFromToken(httpServletRequest);
        this.testBusinessRules.testShouldBelongToSameOrganization(optionalTest.get(), organizationId);

        return this.testRuleService.getAll(page, size, testId);
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
    //TODO:tekrar edilenleri bir method altında topla.
}
