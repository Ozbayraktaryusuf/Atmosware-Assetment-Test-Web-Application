package com.atmosware.belatrix.examSercvice.business.mappers;


import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.AddRuleToTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.GetAllTestRuleForTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.RemoveRuleFromTestResponse;
import com.atmosware.belatrix.examSercvice.core.mappping.MapstructService;
import com.atmosware.belatrix.examSercvice.entities.concretes.TestRule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructService.class)
public interface TestRuleMapper {
    @Mapping(target = "ruleId",source = "rule.id")
    @Mapping(target = "testId",source = "test.id")
    AddRuleToTestResponse toAddRuleToTestResponse(TestRule testRule);
    @Mapping(target = "ruleId",source = "rule.id")
    @Mapping(target = "testId",source = "test.id")
    RemoveRuleFromTestResponse toRemoveRuleFromTestResponse(TestRule testRule);
    @Mapping(target = "ruleId",source = "rule.id")
    @Mapping(target = "testId",source = "test.id")
    GetAllTestRuleForTestResponse toGetAllTestRuleResponse(TestRule testRule);
}
