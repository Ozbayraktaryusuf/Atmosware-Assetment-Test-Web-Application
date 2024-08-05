package com.atmosware.belatrix.examSercvice.business.abstracts;

import com.atmosware.belatrix.examSercvice.business.dtos.requests.rule.CreateRuleRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.rule.CreateRuleResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.rule.GetAllRuleResponse;
import org.springframework.data.domain.Page;

public interface RuleService {
    CreateRuleResponse createRule(CreateRuleRequest createRuleRequest);

    Page<GetAllRuleResponse> getAll(int page,int size);
}
