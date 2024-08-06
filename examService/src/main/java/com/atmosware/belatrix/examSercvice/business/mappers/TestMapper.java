package com.atmosware.belatrix.examSercvice.business.mappers;

import com.atmosware.belatrix.examSercvice.business.dtos.requests.test.CreateTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.test.UpdateTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.test.*;
import com.atmosware.belatrix.examSercvice.core.mappping.MapstructService;
import com.atmosware.belatrix.examSercvice.entities.concretes.Test;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapstructService.class)
public interface TestMapper {
    Test toTest(CreateTestRequest createTestRequest);

    CreatedTestResponse toCreatedTestResponse(Test test);

    GetByIdTestResponse toGetByIdResponse(Test test);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTestFromRequest(UpdateTestRequest updateTestRequest, @MappingTarget Test test);

    UpdatedTestResponse toUpdatedTestResponse(Test test);

    ExtendedEndDateResponse toExtendedEndDateResponse(Test test);

    GetAllTestResponse toGetAllTestResponse(Test test);

    DeleteTestResponse toDeletedTestResponse(Test test);
}
