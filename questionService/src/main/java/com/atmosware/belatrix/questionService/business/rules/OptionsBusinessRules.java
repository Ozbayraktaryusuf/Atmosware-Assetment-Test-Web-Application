package com.atmosware.belatrix.questionService.business.rules;

import com.atmosware.belatrix.questionService.business.constants.Messages;
import com.atmosware.belatrix.questionService.core.business.abstracts.MessageService;
import com.atmosware.belatrix.questionService.core.exceptions.types.BusinessException;
import com.atmosware.belatrix.questionService.dataAccess.OptionRepository;
import com.atmosware.belatrix.questionService.entities.concretes.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OptionsBusinessRules {
    private final OptionRepository optionRepository;
    private final MessageService messageService;

    public void oneAnswerShouldBeTrue(List<Option> options){
        for (Option option:options){
            if (option.isCorrect()){
                return;
            }
        }
        throw new BusinessException(messageService.getMessage(Messages.OptionMessages.AT_LEAST_ONE_OPTION_HAS_TO_BE_TRUE));
    }
}
