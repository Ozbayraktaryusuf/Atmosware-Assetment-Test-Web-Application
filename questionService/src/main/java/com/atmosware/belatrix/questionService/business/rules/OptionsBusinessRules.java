package com.atmosware.belatrix.questionService.business.rules;

import com.atmosware.belatrix.questionService.business.constants.Messages;
import com.atmosware.belatrix.questionService.business.dto.requests.option.UpdateOptionRequest;
import com.atmosware.belatrix.questionService.core.business.abstracts.MessageService;
import com.atmosware.belatrix.questionService.core.exceptions.types.BusinessException;
import com.atmosware.belatrix.questionService.core.exceptions.types.NotFoundException;
import com.atmosware.belatrix.questionService.dataAccess.OptionRepository;
import com.atmosware.belatrix.questionService.entities.concretes.Option;
import com.atmosware.belatrix.questionService.entities.concretes.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OptionsBusinessRules {
    private final OptionRepository optionRepository;
    private final MessageService messageService;

    public void onlyRightOptionCanNotBeDeleted(Question question,Long id){
        Option foundOption = this.optionRepository.findById(id).get();
        if (foundOption.isCorrect()){
            List<Option> options = question.getOptions();
            int numberOfTrueOptions=0;
            for (Option option: options){
                if (option.isCorrect()){
                    numberOfTrueOptions+=1;
                }
            }
            if (numberOfTrueOptions<=1){
                throw new BusinessException(messageService.getMessage(Messages.OptionMessages.ONLY_RIGHT_OPTION_CAN_NOT_BE_DELETED));
            }
        }

    }
    public void oneAnswerShouldBeTrue(List<Option> options){
        for (Option option:options){
            if (option.isCorrect()){
                return;
            }
        }
        throw new BusinessException(messageService.getMessage(Messages.OptionMessages.AT_LEAST_ONE_OPTION_HAS_TO_BE_TRUE));
    }
    public void optionsAndRequestSizeShouldMatch(List<Option> option, List<UpdateOptionRequest> updateOptionRequest){
        if (option.size()!= updateOptionRequest.size()){
            throw new BusinessException(messageService.getMessage(Messages.OptionMessages.OPTIONS_AND_REQUEST_SIZE_SHOULD_MATCH));
        }
    }
    public void questionCanNotObtainOptionsMoreThanFive(List<Option> optionList){
        if (optionList.size()>4){
            throw new BusinessException(messageService.getMessage(Messages.OptionMessages.QUESTION_CAN_NOT_OBTAIN_OPTIONS_MORE_THAN_FIVE));
        }
    }
    public void questionCanNotObtainOptionsLessThanTwo(List<Option> optionList){
        if (optionList.size()<=2){
            throw new BusinessException(messageService.getMessage(Messages.OptionMessages.QUESTION_CAN_NOT_OBTAIN_OPTIONS_LESS_THAN_TWO));
        }
    }
    public void optionShouldBeExists(Long id){
        Optional<Option> optionalOption = this.optionRepository.findById(id);
        if (optionalOption.isEmpty()){
            throw new NotFoundException(messageService.getMessage(Messages.OptionMessages.OPTION_NOT_FOUND));
        }
    }
    public void optionAndQuestionIdShouldMatch(Long optionId, Question question){
        Option option = this.optionRepository.findById(optionId).get();
        if (!option.getQuestion().getId().equals(question.getId())){
            throw new BusinessException(messageService.getMessage(Messages.OptionMessages.OPTION_AND_QUESTION_SHOULD_BELONG_TO_EACH_OTHER));
        }
    }
}
