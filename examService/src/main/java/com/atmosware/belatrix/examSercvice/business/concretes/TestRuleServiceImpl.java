package com.atmosware.belatrix.examSercvice.business.concretes;

import com.atmosware.belatrix.examSercvice.business.abstracts.RuleService;
import com.atmosware.belatrix.examSercvice.business.abstracts.TestRuleService;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.AddRuleToTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.GetAllTestRuleForTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.RemoveRuleFromTestResponse;
import com.atmosware.belatrix.examSercvice.business.mappers.TestRuleMapper;
import com.atmosware.belatrix.examSercvice.business.rules.TestRuleBusinessRules;
import com.atmosware.belatrix.examSercvice.dataAccess.TestRuleRepository;
import com.atmosware.belatrix.examSercvice.entities.concretes.Rule;
import com.atmosware.belatrix.examSercvice.entities.concretes.Test;
import com.atmosware.belatrix.examSercvice.entities.concretes.TestRule;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestRuleServiceImpl implements TestRuleService {
    private final RuleService ruleService;
    private final TestRuleRepository testRuleRepository;
    private final TestRuleMapper testRuleMapper;
    private final TestRuleBusinessRules testRuleBusinessRules;


    @Override
    @Transactional
    public AddRuleToTestResponse addRuleToTest(Long ruleId, Test test) {
        log.info("Add rule to test method summoned.");

        this.testRuleBusinessRules.testRuleShouldNotBeExists(test.getId(),ruleId);
        Rule rule = this.ruleService.findByIdForTestRule(ruleId);

        TestRule testRule = new TestRule(rule,test);

        return this.testRuleMapper.toAddRuleToTestResponse(this.testRuleRepository.save(testRule));
    }

    @Override
    @Transactional
    public void deleteAll(List<TestRule> testRules) {
        log.info("Delete all test's rule method summoned.");

        testRules.forEach(testRule -> testRule.setDeletedDate(LocalDateTime.now()));

        this.testRuleRepository.saveAll(testRules);
    }

    @Override
    @Transactional
    public RemoveRuleFromTestResponse removeRuleFromTest(Long ruleId, Test test) {
        log.info("Remove rule from test method summoned.");

        Optional<TestRule> optionalTestRule = this.testRuleRepository.findByTestIdAndRuleId(test.getId(),ruleId);

        TestRule testRule = optionalTestRule.get();
        testRule.setDeletedDate(LocalDateTime.now());

        return this.testRuleMapper.toRemoveRuleFromTestResponse(this.testRuleRepository.save(testRule));
    }

    @Override
    public Page<GetAllTestRuleForTestResponse> getAll(int page, int size,Long testId) {
        log.info("Get all test's rule method summoned.");

        Pageable pageable = PageRequest.of(page,size, Sort.by("id"));

        Page<TestRule> testRules = this.testRuleRepository.findByTestId(testId,pageable);

        return testRules.map(this.testRuleMapper::toGetAllTestRuleResponse);
    }
}
