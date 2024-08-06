package com.atmosware.belatrix.examSercvice.api.controller;

import com.atmosware.belatrix.examSercvice.business.abstracts.RuleService;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.rule.CreateRuleRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.rule.UpdateRuleRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.rule.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("exam-service/api/v1/rules")
public class RuleController {
    private final RuleService ruleService;

    @PostMapping
    public CreateRuleResponse createRule(@Valid @RequestBody CreateRuleRequest createRuleRequest) {
        return this.ruleService.createRule(createRuleRequest);
    }

    @GetMapping()
    //localhost:5002/exam-service/api/v1/rules?page=0&size=1
    public Page<GetAllRuleResponse> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "10") int size) {
        return this.ruleService.getAll(page, size);
    }

    @GetMapping("{id}")
    public GetByIdRuleResponse getById(@PathVariable Long id) {
        return this.ruleService.getById(id);
    }

    @PutMapping("{id}")
    public UpdatedRuleResponse update(@PathVariable Long id, @Valid @RequestBody UpdateRuleRequest updateRuleRequest) {
        return this.ruleService.update(id, updateRuleRequest);
    }

    @DeleteMapping("{id}")
    public DeletedRuleResponse deletedRuleResponse(@PathVariable Long id){
        return this.ruleService.delete(id);
    }

}
