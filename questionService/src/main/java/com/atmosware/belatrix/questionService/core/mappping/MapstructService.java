package com.atmosware.belatrix.questionService.core.mappping;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MapstructService {
}
