package com.atmosware.belatrix.examSercvice.business.concretes;

import com.atmosware.belatrix.examSercvice.business.abstracts.RuleService;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.rule.CreateRuleRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.rule.UpdateRuleRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.rule.*;
import com.atmosware.belatrix.examSercvice.business.mappers.RuleMapper;
import com.atmosware.belatrix.examSercvice.business.rules.RuleManagerBusinessRules;
import com.atmosware.belatrix.examSercvice.dataAccess.RuleRepository;
import com.atmosware.belatrix.examSercvice.entities.concretes.Rule;
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
public class RuleManager implements RuleService{
    private final RuleMapper ruleMapper;
    private final RuleRepository ruleRepository;
    private final RuleManagerBusinessRules ruleManagerBusinessRules;
    @Override
    @Transactional
    public CreateRuleResponse createRule(CreateRuleRequest createRuleRequest) {
        log.info("Create rule method started.");

        Rule rule = this.ruleMapper.toRule(createRuleRequest);

        return this.ruleMapper.toCreateRuleResponse(this.ruleRepository.save(rule));
    }

    @Override
    @Transactional
    public Page<GetAllRuleResponse> getAll(int page, int size) {
        log.info("Get all rule method started");

        Pageable pageable = PageRequest.of(page,size, Sort.by("id"));

        Page<Rule> rules = this.ruleRepository.findAll(pageable);

        return rules.map(this.ruleMapper::toGetAllRuleResponse);
    }

    @Override
    @Transactional
    public GetByIdRuleResponse getById(Long id) {
        log.info("Get rule by id method started.");

        Optional<Rule> optionalRule = this.ruleRepository.findById(id);

        this.ruleManagerBusinessRules.testShouldBeExists(optionalRule);

        return this.ruleMapper.toGetByIdRuleResponse(optionalRule.get());
    }

    @Override
    @Transactional
    public UpdatedRuleResponse update(Long id, UpdateRuleRequest updateRuleRequest) {
        log.info("Update rule method started.");

        Optional<Rule> optionalRule = this.ruleRepository.findById(id);

        this.ruleManagerBusinessRules.testShouldBeExists(optionalRule);

        Rule rule = optionalRule.get();

        this.ruleMapper.updateRuleFromRequest(updateRuleRequest,rule);

        return this.ruleMapper.toUpdatedRuleResponse(this.ruleRepository.save(rule));
    }

    @Override
    @Transactional
    public DeletedRuleResponse delete(Long id) {
        log.info("Delete Rule method started.");

        Optional<Rule> optionalRule = this.ruleRepository.findById(id);

        this.ruleManagerBusinessRules.testShouldBeExists(optionalRule);

        Rule rule = optionalRule.get();
        rule.setDeletedDate(LocalDateTime.now());

        return this.ruleMapper.toDeletedRuleResponse(this.ruleRepository.save(rule));
    }

    @Override
    @Transactional
    public Rule findByIdForTestRule(Long id) {
        log.info("Find rule method started for create rule method.");
        Optional<Rule> optionalRule = this.ruleRepository.findById(id);

        this.ruleManagerBusinessRules.testShouldBeExists(optionalRule);

        return optionalRule.get();
    }
}
