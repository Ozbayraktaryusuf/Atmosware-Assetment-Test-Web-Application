package com.atmosware.belatrix.examSercvice.business.concretes;

import com.atmosware.belatrix.examSercvice.business.abstracts.RuleService;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.rule.CreateRuleRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.rule.CreateRuleResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.rule.GetAllRuleResponse;
import com.atmosware.belatrix.examSercvice.business.mappers.RuleMapper;
import com.atmosware.belatrix.examSercvice.dataAccess.RuleRepository;
import com.atmosware.belatrix.examSercvice.entities.concretes.Rule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleManager implements RuleService{
    private final RuleMapper ruleMapper;
    private final RuleRepository ruleRepository;
    @Override
    public CreateRuleResponse createRule(CreateRuleRequest createRuleRequest) {
        Rule rule = this.ruleMapper.toRule(createRuleRequest);

        return this.ruleMapper.toCreateRuleResponse(this.ruleRepository.save(rule));
    }

    @Override
    public Page<GetAllRuleResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("id"));

        Page<Rule> rules = this.ruleRepository.findAll(pageable);

        Page<GetAllRuleResponse> getAllRuleResponse = rules.map(this.ruleMapper::toGetAllRuleResponse);

        return getAllRuleResponse;
    }
}
