package com.atmosware.belatrix.examSercvice.business.rules;

import com.atmosware.belatrix.examSercvice.business.constants.Messages;
import com.atmosware.belatrix.examSercvice.core.business.abstracts.MessageService;
import com.atmosware.belatrix.examSercvice.core.exceptions.types.BusinessException;
import com.atmosware.belatrix.examSercvice.core.exceptions.types.NotFoundException;
import com.atmosware.belatrix.examSercvice.dataAccess.TestQuestionRepository;
import com.atmosware.belatrix.examSercvice.entities.concretes.TestQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestQuestionBusinessRules {
    private final TestQuestionRepository testQuestionRepository;
    private final MessageService messageService;
    public void testQuestionShouldBeExists(Optional<TestQuestion> optionalTestQuestion){
        if (optionalTestQuestion.isEmpty()){
            throw new NotFoundException(messageService.getMessage(Messages.TestQuestionMessages.TEST_QUESTION_NOT_FOUND));
        }
    }
    public void questionCanNotObtainOptionsLessThanTwo(List<TestQuestion> testQuestions) {
        if (testQuestions.size() <= 1) {
            throw new BusinessException(messageService.getMessage(Messages.TestQuestionMessages.TEST_CAN_NOT_OBTAIN_QUESTION_LESS_THAN_ONE));
        }
    }
    public void questionCanNotBeDuplicated(Optional<TestQuestion> optionalTestQuestion){
        if (optionalTestQuestion.isPresent()){
            throw new NotFoundException(messageService.getMessage(Messages.TestQuestionMessages.TEST_QUESTION_CAN_NOT_BE_DUPLICATED));
        }
    }
}
