package com.atmosware.belatrix.examSercvice.business.abstracts;

import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.AddRuleToTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.GetAllTestRuleForTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.RemoveRuleFromTestResponse;
import com.atmosware.belatrix.examSercvice.entities.concretes.Test;
import com.atmosware.belatrix.examSercvice.entities.concretes.TestRule;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TestRuleService {
    AddRuleToTestResponse addRuleToTest(Long ruleId, Test test);

    void deleteAll(List<TestRule> testRules);
    RemoveRuleFromTestResponse removeRuleFromTest(Long ruleId, Test test);
    Page<GetAllTestRuleForTestResponse> getAll(int page, int size,Long testId);
}
