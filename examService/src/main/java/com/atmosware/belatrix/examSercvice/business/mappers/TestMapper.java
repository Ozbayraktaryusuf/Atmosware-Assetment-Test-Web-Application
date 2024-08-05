package com.atmosware.belatrix.examSercvice.business.mappers;

import com.atmosware.belatrix.examSercvice.business.dtos.requests.test.CreateTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.test.CreatedTestResponse;
import com.atmosware.belatrix.examSercvice.core.mappping.MapstructService;
import com.atmosware.belatrix.examSercvice.entities.concretes.Test;
import org.mapstruct.Mapper;

@Mapper(config = MapstructService.class)
public interface TestMapper {
    Test toTest(CreateTestRequest createTestRequest);
    CreatedTestResponse toCreatedTestResponse(Test test);
}
