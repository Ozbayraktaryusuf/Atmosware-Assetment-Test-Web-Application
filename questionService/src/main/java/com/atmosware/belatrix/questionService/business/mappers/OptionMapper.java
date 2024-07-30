package com.atmosware.belatrix.questionService.business.mappers;

import com.atmosware.belatrix.questionService.business.dto.requests.option.CreateOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.option.CreatedOptionResponse;
import com.atmosware.belatrix.questionService.core.mappping.MapstructService;
import com.atmosware.belatrix.questionService.entities.concretes.Option;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructService.class)
public interface OptionMapper {
    Option toOption(CreateOptionRequest createOptionRequest);

    List<Option> toOption(List<CreateOptionRequest> requests);
    @Mapping(target = "correct",source = "correct")
    CreatedOptionResponse toCreateOptionResponse(Option option);

    List<CreatedOptionResponse> toCreateOptionResponse(List<Option> options);
}
