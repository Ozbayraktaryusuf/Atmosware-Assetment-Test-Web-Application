package com.atmosware.belatrix.examSercvice.api.controller;

import com.atmosware.belatrix.examSercvice.business.abstracts.TestService;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.test.CreateTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.test.ExtendEndDateRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.test.UpdateTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.AddQuestionToTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.DeleteQuestionFromTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.test.*;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.AddedQuestionToTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.DeletedQuestionFromTestResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("exam-service/api/v1/tests")
public class TestController {
    private final TestService testService;

    @PostMapping
    public CreatedTestResponse add(@Valid @RequestBody CreateTestRequest createTestRequest, HttpServletRequest httpServletRequest) {
        return this.testService.add(createTestRequest, httpServletRequest);
    }

    @GetMapping("{id}")
    public GetByIdTestResponse getById(@PathVariable Long id) {
        return this.testService.getById(id);
    }

    @GetMapping("organization/{id}")
    //TODO:{organizationId} diyerek url'e direk tokendan gelen idyi verebilir miyim?
    public GetByIdTestResponse getById(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return this.testService.getByIdOrganization(id, httpServletRequest);
    }

    @GetMapping()
    public Page<GetAllTestResponse> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "10") int size) {
        return this.testService.getAll(size, page);
    }

    @GetMapping("organization")
    public Page<GetAllTestResponse> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "10") int size,
                                           HttpServletRequest httpServletRequest) {
        return this.testService.getAllOrganization(size, page, httpServletRequest);
    }

    @PutMapping("{id}")
    public UpdatedTestResponse update(@PathVariable Long id, @Valid @RequestBody UpdateTestRequest updateTestRequest) {
        return this.testService.update(id, updateTestRequest);
    }

    @PutMapping("organization/{id}")
    public UpdatedTestResponse update(@PathVariable Long id, @Valid @RequestBody UpdateTestRequest updateTestRequest, HttpServletRequest httpServletRequest) {
        return this.testService.updateOrganization(id, updateTestRequest, httpServletRequest);
    }

    @DeleteMapping("{id}")
    public DeleteTestResponse delete(@PathVariable Long id) {
        return this.testService.delete(id);
    }

    @DeleteMapping("organization/{id}")
    public DeleteTestResponse delete(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return this.testService.deleteOrganization(id, httpServletRequest);
    }

    @PutMapping("extend-end-date/{id}")
    public ExtendedEndDateResponse extendEndDate(@PathVariable Long id, @Valid @RequestBody ExtendEndDateRequest extendEndDateRequest) {
        return this.testService.extendEndDate(id, extendEndDateRequest);
    }

    @PutMapping("organization/extend-end-date/{id}")
    public ExtendedEndDateResponse extendEndDate(@PathVariable Long id, @Valid @RequestBody ExtendEndDateRequest extendEndDateRequest, HttpServletRequest httpServletRequest) {
        return this.testService.extendEndDateOrganization(id, extendEndDateRequest, httpServletRequest);
    }

    @PutMapping
    public AddedQuestionToTestResponse addQuestionToTest(@Valid @RequestBody AddQuestionToTestRequest addQuestionToTestRequest) {
        return this.testService.addQuestionToResponse(addQuestionToTestRequest);
    }

    @PutMapping("organization")
    public AddedQuestionToTestResponse addQuestionToTest(@Valid @RequestBody AddQuestionToTestRequest addQuestionToTestRequest, HttpServletRequest httpServletRequest) {
        return this.testService.addQuestionToResponse(addQuestionToTestRequest);
    }

    @DeleteMapping
    public DeletedQuestionFromTestResponse deleteQuestionFrom(@Valid @RequestBody DeleteQuestionFromTestRequest deleteQuestionFromTestRequest) {
        return this.testService.deleteQuestionFromTest(deleteQuestionFromTestRequest);
    }

    @DeleteMapping("organization")
    public DeletedQuestionFromTestResponse deleteQuestionFrom(@Valid @RequestBody DeleteQuestionFromTestRequest deleteQuestionFromTestRequest, HttpServletRequest httpServletRequest) {
        return this.testService.deleteQuestionFromTestOrganization(deleteQuestionFromTestRequest, httpServletRequest);
    }
}
