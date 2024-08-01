package com.atmosware.belatrix.questionService.business.abstracts;

import com.atmosware.belatrix.questionService.business.dto.requests.option.AddOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.option.CreateOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.option.DeleteOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.option.UpdateOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.option.AddOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.option.CreatedOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.option.DeletedOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.option.UpdatedOptionResponse;
import com.atmosware.belatrix.questionService.entities.concretes.Question;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface OptionService {
    List<CreatedOptionResponse> add(List<CreateOptionRequest> createOptionRequest, Question question);
    void delete(Question question);
    List<UpdatedOptionResponse> update(List<UpdateOptionRequest> updateOptionRequests,Question question);
    AddOptionResponse addOption(AddOptionRequest request,Question question);
    DeletedOptionResponse deleteOption(DeleteOptionRequest deleteOptionRequest, Question question);
}
