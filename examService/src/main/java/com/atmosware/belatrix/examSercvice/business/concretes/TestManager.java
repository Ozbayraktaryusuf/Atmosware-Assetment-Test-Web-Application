package com.atmosware.belatrix.examSercvice.business.concretes;

import com.atmosware.belatrix.core.services.JwtService;
import com.atmosware.belatrix.examSercvice.business.abstracts.TestQuestionService;
import com.atmosware.belatrix.examSercvice.business.abstracts.TestService;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.test.CreateTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.test.CreatedTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.CreatedTestQuestionResponse;
import com.atmosware.belatrix.examSercvice.business.mappers.TestMapper;
import com.atmosware.belatrix.examSercvice.dataAccess.TestRepository;
import com.atmosware.belatrix.examSercvice.entities.concretes.Test;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestManager implements TestService {
    private final TestQuestionService testQuestionService;
    private final TestMapper testMapper;
    private final TestRepository testRepository;
    private final JwtService jwtService;

    @Override
    public CreatedTestResponse add(CreateTestRequest createTestRequest, HttpServletRequest httpServletRequest) {
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
}
