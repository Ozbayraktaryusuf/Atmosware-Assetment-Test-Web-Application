package com.atmosware.belatrix.examSercvice.business.mappers;

import com.atmosware.belatrix.examSercvice.business.dtos.requests.rule.CreateRuleRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.rule.CreateRuleResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.rule.GetAllRuleResponse;
import com.atmosware.belatrix.examSercvice.core.mappping.MapstructService;
import com.atmosware.belatrix.examSercvice.entities.concretes.Rule;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(config = MapstructService.class)
public interface RuleMapper {
    Rule toRule(CreateRuleRequest createRuleRequest);

    CreateRuleResponse toCreateRuleResponse(Rule rule);

    GetAllRuleResponse toGetAllRuleResponse(Rule rule);

}
