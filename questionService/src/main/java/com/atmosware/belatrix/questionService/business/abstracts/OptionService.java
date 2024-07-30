package com.atmosware.belatrix.questionService.business.abstracts;

import com.atmosware.belatrix.questionService.business.dto.requests.option.CreateOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.option.CreatedOptionResponse;
import com.atmosware.belatrix.questionService.entities.concretes.Question;

import java.util.List;

public interface OptionService {
    List<CreatedOptionResponse> add(List<CreateOptionRequest> createOptionRequest, Question question);
}
