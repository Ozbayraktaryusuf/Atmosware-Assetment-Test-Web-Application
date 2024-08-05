package com.atmosware.belatrix.examSercvice.business.abstracts;

import com.atmosware.belatrix.examSercvice.business.dtos.requests.test.CreateTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.test.CreatedTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.CreatedTestQuestionResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface TestService {
    CreatedTestResponse add(CreateTestRequest createTestRequest,HttpServletRequest httpServletRequest);
}
