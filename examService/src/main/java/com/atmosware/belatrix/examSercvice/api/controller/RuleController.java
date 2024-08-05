package com.atmosware.belatrix.examSercvice.api.controller;

import com.atmosware.belatrix.examSercvice.business.abstracts.RuleService;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.rule.CreateRuleRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.rule.CreateRuleResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.rule.GetAllRuleResponse;
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
    public CreateRuleResponse createRule(@Valid @RequestBody CreateRuleRequest createRuleRequest){
        return this.ruleService.createRule(createRuleRequest);
    }
    @GetMapping
    public Page<GetAllRuleResponse> getAll(@RequestParam int page, @RequestParam int size){
        return this.ruleService.getAll(page,size);
    }
}
