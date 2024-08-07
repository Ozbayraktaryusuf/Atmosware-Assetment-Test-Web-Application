package com.atmosware.belatrix.examSercvice.business.abstracts;

import com.atmosware.belatrix.examSercvice.business.dtos.requests.rule.CreateRuleRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.rule.UpdateRuleRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.rule.*;
import com.atmosware.belatrix.examSercvice.entities.concretes.Rule;
import org.springframework.data.domain.Page;

public interface RuleService {
    CreateRuleResponse createRule(CreateRuleRequest createRuleRequest);

    Page<GetAllRuleResponse> getAll(int page, int size);

    GetByIdRuleResponse getById(Long id);

    UpdatedRuleResponse update(Long id, UpdateRuleRequest updateRuleRequest);

    DeletedRuleResponse delete(Long id);
    Rule findByIdForTestRule(Long id);
}
