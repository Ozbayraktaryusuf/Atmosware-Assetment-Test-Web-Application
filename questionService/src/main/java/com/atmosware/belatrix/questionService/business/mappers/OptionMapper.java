package com.atmosware.belatrix.questionService.business.mappers;

import com.atmosware.belatrix.questionService.business.dto.dtos.OptionDto;
import com.atmosware.belatrix.questionService.business.dto.requests.option.AddOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.option.CreateOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.option.UpdateOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.option.AddOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.option.CreatedOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.option.DeletedOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.option.UpdatedOptionResponse;
import com.atmosware.belatrix.questionService.core.mappping.MapstructService;
import com.atmosware.belatrix.questionService.entities.concretes.Option;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MapstructService.class)
public interface OptionMapper {
    Option toOption(CreateOptionRequest createOptionRequest);

    List<Option> toOption(List<CreateOptionRequest> requests);
    @Mapping(target = "correct",source = "correct")
    CreatedOptionResponse toCreateOptionResponse(Option option);

    List<CreatedOptionResponse> toCreateOptionResponse(List<Option> options);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOptionFromRequest(UpdateOptionRequest updateOptionRequests, @MappingTarget Option option);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOptionFromRequest(List<UpdateOptionRequest> updateOptionRequests, @MappingTarget List<Option> option);

    UpdatedOptionResponse toUpdatedOptionResponse(Option option);

    List<UpdatedOptionResponse> toUpdatedOptionResponse(List<Option> options);

    Option toOption(AddOptionRequest addOptionRequest);
    @Mapping(target = "questionId",source = "question.id")
    AddOptionResponse toAddOptionResponse(Option option);

    @Mapping(target = "questionId",source = "question.id")
    DeletedOptionResponse toDeleteOptionsResponse(Option option);
    OptionDto toOptionDto(Option option);
}
