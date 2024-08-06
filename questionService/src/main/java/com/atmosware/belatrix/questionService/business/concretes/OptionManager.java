package com.atmosware.belatrix.questionService.business.concretes;

import com.atmosware.belatrix.questionService.business.abstracts.OptionService;
import com.atmosware.belatrix.questionService.business.dto.dtos.OptionDto;
import com.atmosware.belatrix.questionService.business.dto.requests.option.AddOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.option.CreateOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.option.DeleteOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.option.UpdateOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.option.AddOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.option.CreatedOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.option.DeletedOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.option.UpdatedOptionResponse;
import com.atmosware.belatrix.questionService.business.mappers.OptionMapper;
import com.atmosware.belatrix.questionService.business.rules.OptionsBusinessRules;
import com.atmosware.belatrix.questionService.dataAccess.OptionRepository;
import com.atmosware.belatrix.questionService.entities.concretes.Option;
import com.atmosware.belatrix.questionService.entities.concretes.Question;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        List<Option> options = this.optionMapper.toOption(createOptionRequest);
        this.optionsBusinessRules.oneAnswerShouldBeTrue(options);

        for (Option option : options) {
            option.setQuestion(question);
        }

        return this.optionMapper.toCreateOptionResponse(this.optionRepository.saveAll(options));
    }

    @Override
    @Transactional
    public void delete(Question question) {
        List<Option> optionList = this.optionRepository.findByQuestionId(question.getId());

        optionList.forEach(x -> x.setDeletedDate(LocalDateTime.now()));

        this.optionRepository.saveAll(optionList);
    }

    @Override
    @Transactional
    public List<UpdatedOptionResponse> update(List<UpdateOptionRequest> updateOptionRequests, Question question) {
        List<Option> options = this.optionRepository.findByQuestionId(question.getId());
        this.optionsBusinessRules.optionsAndRequestSizeShouldMatch(options, updateOptionRequests);

        for (int i = 0; i < updateOptionRequests.size(); i++) {
            if (i < options.size()) {
                this.optionMapper.updateOptionFromRequest(updateOptionRequests.get(i), options.get(i));
            }
        }
        this.optionsBusinessRules.oneAnswerShouldBeTrue(options);

        for (Option option : options) {
            option.setQuestion(question);
        }

        this.optionRepository.saveAll(options);

        return this.optionMapper.toUpdatedOptionResponse(options);
    }

    @Override
    public AddOptionResponse addOption(AddOptionRequest request, Question question) {
        this.optionsBusinessRules.questionCanNotObtainOptionsMoreThanFive(question.getOptions());

        Option option = this.optionMapper.toOption(request);
        option.setQuestion(question);

        return this.optionMapper.toAddOptionResponse(this.optionRepository.save(option));
    }

    @Override
    public DeletedOptionResponse deleteOption(DeleteOptionRequest deleteOptionRequest, Question question) {
        this.optionsBusinessRules.optionShouldBeExists(deleteOptionRequest.id());
        this.optionsBusinessRules.optionAndQuestionIdShouldMatch(deleteOptionRequest.id(), question);
        this.optionsBusinessRules.onlyRightOptionCanNotBeDeleted(question, deleteOptionRequest.id());
        this.optionsBusinessRules.questionCanNotObtainOptionsLessThanTwo(question.getOptions());

        Option option = this.optionRepository.findById(deleteOptionRequest.id()).get();
        option.setDeletedDate(LocalDateTime.now());

        return this.optionMapper.toDeleteOptionsResponse(this.optionRepository.save(option));
    }

    @Override
    public List<OptionDto> optionDtoForQuestionGetById(Question question) {
        List<Option> options = question.getOptions();

        return options.stream().map(this.optionMapper::toOptionDto).toList();
    }
}
