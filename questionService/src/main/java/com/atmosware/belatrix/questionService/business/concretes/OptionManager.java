package com.atmosware.belatrix.questionService.business.concretes;

import com.atmosware.belatrix.questionService.business.abstracts.OptionService;
import com.atmosware.belatrix.questionService.business.dto.requests.option.CreateOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.option.CreatedOptionResponse;
import com.atmosware.belatrix.questionService.business.mappers.OptionMapper;
import com.atmosware.belatrix.questionService.business.rules.OptionsBusinessRules;
import com.atmosware.belatrix.questionService.dataAccess.OptionRepository;
import com.atmosware.belatrix.questionService.entities.concretes.Option;
import com.atmosware.belatrix.questionService.entities.concretes.Question;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionManager implements OptionService {
    private final OptionMapper optionMapper;
    private final OptionRepository optionRepository;
    private final OptionsBusinessRules optionsBusinessRules;
    @Override
    @Transactional
    public List<CreatedOptionResponse> add(List<CreateOptionRequest> createOptionRequest, Question question) {
        List<Option> options = this.optionMapper.toOption( createOptionRequest);
        this.optionsBusinessRules.oneAnswerShouldBeTrue(options);

        for(Option option:options){
            option.setQuestion(question);
        }

        List<CreatedOptionResponse> createOptionResponses = this.optionMapper.toCreateOptionResponse(this.optionRepository.saveAll(options));

        return createOptionResponses;
    }
}
