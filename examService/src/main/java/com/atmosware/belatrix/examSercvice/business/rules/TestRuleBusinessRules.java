package com.atmosware.belatrix.examSercvice.business.rules;

import com.atmosware.belatrix.examSercvice.business.constants.Messages;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.CreateTestQuestionRequest;
import com.atmosware.belatrix.examSercvice.core.business.abstracts.MessageService;
import com.atmosware.belatrix.examSercvice.core.exceptions.types.BusinessException;
import com.atmosware.belatrix.examSercvice.core.exceptions.types.NotFoundException;
import com.atmosware.belatrix.examSercvice.dataAccess.TestRuleRepository;
import com.atmosware.belatrix.examSercvice.entities.concretes.TestQuestion;
import com.atmosware.belatrix.examSercvice.entities.concretes.TestRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestRuleBusinessRules {
    private final TestRuleRepository testRuleRepository;
    private final MessageService messageService;
    public void testRuleShouldBeExists(Optional<TestRule> optionalTestRule){
        if (optionalTestRule.isEmpty()){
            throw new NotFoundException(messageService.getMessage(Messages.TestRuleMessages.TEST_RULE_NOT_FOUND));
        }
    }
    public void testRuleShouldNotBeExists(Long testId, Long ruleId){
        Optional<TestRule> optionalTestRule = this.testRuleRepository.findByTestIdAndRuleId(testId,ruleId);
        if (optionalTestRule.isPresent()){
            throw new BusinessException(messageService.getMessage(Messages.TestRuleMessages.TEST_RULE_ALREADY_EXISTS));
        }
    }

}
