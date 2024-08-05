package com.atmosware.belatrix.examSercvice.api.controller;

import com.atmosware.belatrix.examSercvice.business.abstracts.TestService;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.test.CreateTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.test.CreatedTestResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("exam-service/api/v1/tests")
public class TestController {
    private final TestService testService;

    @PostMapping
    public CreatedTestResponse add(@Valid @RequestBody CreateTestRequest createTestRequest, HttpServletRequest httpServletRequest){
        return this.testService.add(createTestRequest,httpServletRequest);
    }
}
