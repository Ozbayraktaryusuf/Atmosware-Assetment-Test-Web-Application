package com.atmosware.belatrix.examSercvice.business.mappers;

import com.atmosware.belatrix.examSercvice.business.dtos.requests.rule.CreateRuleRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.rule.UpdateRuleRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.rule.*;
import com.atmosware.belatrix.examSercvice.core.mappping.MapstructService;
import com.atmosware.belatrix.examSercvice.entities.concretes.Rule;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapstructService.class)
public interface RuleMapper {
    Rule toRule(CreateRuleRequest createRuleRequest);

    CreateRuleResponse toCreateRuleResponse(Rule rule);

    GetAllRuleResponse toGetAllRuleResponse(Rule rule);

    DeletedRuleResponse toDeletedRuleResponse(Rule rule);

    GetByIdRuleResponse toGetByIdRuleResponse(Rule rule);

    UpdatedRuleResponse toUpdatedRuleResponse(Rule rule);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRuleFromRequest(UpdateRuleRequest updateRuleRequest, @MappingTarget Rule rule);
}
