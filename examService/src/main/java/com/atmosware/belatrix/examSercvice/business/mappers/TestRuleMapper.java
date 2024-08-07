package com.atmosware.belatrix.examSercvice.business.mappers;


import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.AddRuleToTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.GetAllTestRuleForTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testRule.RemoveRuleFromTestResponse;
import com.atmosware.belatrix.examSercvice.core.mappping.MapstructService;
import com.atmosware.belatrix.examSercvice.entities.concretes.TestRule;
import org.mapstruct.Mapper;

@Mapper(config = MapstructService.class)
public interface TestRuleMapper {
    AddRuleToTestResponse toAddRuleToTestResponse(TestRule testRule);
    RemoveRuleFromTestResponse toRemoveRuleFromTestResponse(TestRule testRule);
    GetAllTestRuleForTestResponse toGetAllTestRuleResponse(TestRule testRule);
}
