package com.atmosware.belatrix.examSercvice.business.abstracts;

import com.atmosware.belatrix.examSercvice.business.dtos.requests.test.CreateTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.test.ExtendEndDateRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.test.UpdateTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.AddQuestionToTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.DeleteQuestionFromTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.testRule.AddRuleToTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.testRule.RemoveRuleFromTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.test.*;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.AddedQuestionToTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.DeletedQuestionFromTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.AddRuleToTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.GetAllTestRuleForTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.RemoveRuleFromTestResponse;
import com.atmosware.belatrix.examSercvice.entities.concretes.Test;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface TestService {
    CreatedTestResponse add(CreateTestRequest createTestRequest, HttpServletRequest httpServletRequest);

    Page<GetAllTestResponse> getAll(int size, int page);

    Page<GetAllTestResponse> getAllOrganization(int size, int page, HttpServletRequest httpServletRequest);

    GetByIdTestResponse getById(Long id);

    GetByIdTestResponse getByIdOrganization(Long id, HttpServletRequest httpServletRequest);

    DeleteTestResponse delete(Long id);

    DeleteTestResponse deleteOrganization(Long id, HttpServletRequest httpServletRequest);

    AddedQuestionToTestResponse addQuestionToTest(AddQuestionToTestRequest addQuestionToTestRequest);

    AddedQuestionToTestResponse addQuestionToTestOrganization(AddQuestionToTestRequest addQuestionToTestRequest, HttpServletRequest httpServletRequest);

    DeletedQuestionFromTestResponse deleteQuestionFromTest(DeleteQuestionFromTestRequest deleteQuestionFromTestRequest);

    DeletedQuestionFromTestResponse deleteQuestionFromTestOrganization(DeleteQuestionFromTestRequest deleteQuestionFromTestRequest, HttpServletRequest httpServletRequest);

    UpdatedTestResponse update(Long id, UpdateTestRequest updateTestRequest);

    UpdatedTestResponse updateOrganization(Long id, UpdateTestRequest updateTestRequest, HttpServletRequest httpServletRequest);

    ExtendedEndDateResponse extendEndDate(Long id, ExtendEndDateRequest extendEndDateRequest);

    ExtendedEndDateResponse extendEndDateOrganization(Long id, ExtendEndDateRequest extendEndDateRequest, HttpServletRequest httpServletRequest);

    AddRuleToTestResponse addRuleToTest(AddRuleToTestRequest addRuleToTestRequest);

    AddRuleToTestResponse addRuleToTestOrganization(AddRuleToTestRequest addRuleToTestRequest,HttpServletRequest httpServletRequest);
    RemoveRuleFromTestResponse removeRuleFromTest(RemoveRuleFromTestRequest removeRuleFromTestRequest);
    RemoveRuleFromTestResponse removeRuleFromTestOrganization(RemoveRuleFromTestRequest removeRuleFromTestRequest,HttpServletRequest httpServletRequest);
    Page<GetAllTestRuleForTestResponse> getAllTestRuleForTest(Long testId, int page,int size);
    Page<GetAllTestRuleForTestResponse> getAllTestRuleForTestOrganization(Long testId, int page,int size,HttpServletRequest httpServletRequest);
    Test getTextForInvitation(Long id);
}
