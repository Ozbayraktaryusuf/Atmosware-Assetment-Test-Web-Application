package com.atmosware.belatrix.questionService.business.rules;

import com.atmosware.belatrix.questionService.business.constants.Messages;
import com.atmosware.belatrix.questionService.core.business.abstracts.MessageService;
import com.atmosware.belatrix.questionService.core.exceptions.types.BusinessException;
import com.atmosware.belatrix.questionService.core.exceptions.types.NotFoundException;
import com.atmosware.belatrix.questionService.dataAccess.QuestionRepository;
import com.atmosware.belatrix.questionService.entities.concretes.Question;
import com.atmosware.belatrix.questionService.entities.enums.Updatable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionBusinessRules {
    private final QuestionRepository questionRepository;
    private final MessageService messageService;
    public void questionShouldBeExists(Long id){
        Optional<Question> optionalQuestion = this.questionRepository.findById(id);
        if (optionalQuestion.isEmpty()){
            throw new NotFoundException(messageService.getMessage(Messages.QuestionMessages.QUESTION_SHOULD_BE_EXISTS));
        }
    }
    public void questionShouldBelongToSameOrganization(Long id, UUID organizationId){
        Question question = this.questionRepository.findById(id).get();
        if(question.getOrganizationId()==null){
            throw new BusinessException(messageService.getMessage(Messages.QuestionMessages.QUESTION_SHOULD_BE_BELONGED_TO_ORGANIZATION));
        }
        else if (!question.getOrganizationId().equals(organizationId)){
            throw new BusinessException(messageService.getMessage(Messages.QuestionMessages.QUESTION_SHOULD_BE_BELONGED_TO_ORGANIZATION));
        }
    }
    public void questionShouldBeUpdatable(Question question){
        if (!question.getUpdatable().equals(Updatable.UPDATABLE)){
            throw new BusinessException(messageService.getMessage(Messages.QuestionMessages.QUESTION_IS_NOT_UPDATABLE));
        }
    }
}
