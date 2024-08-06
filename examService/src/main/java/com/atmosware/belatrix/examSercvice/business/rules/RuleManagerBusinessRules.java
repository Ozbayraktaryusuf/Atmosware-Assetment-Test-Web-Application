package com.atmosware.belatrix.examSercvice.business.rules;

import com.atmosware.belatrix.examSercvice.business.constants.Messages;
import com.atmosware.belatrix.examSercvice.core.business.abstracts.MessageService;
import com.atmosware.belatrix.examSercvice.core.exceptions.types.NotFoundException;
import com.atmosware.belatrix.examSercvice.dataAccess.RuleRepository;
import com.atmosware.belatrix.examSercvice.entities.concretes.Rule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RuleManagerBusinessRules {
    private final MessageService messageService;
    private final RuleRepository ruleRepository;
    public void testShouldBeExists(Optional<Rule> optionalRule) {
        if (optionalRule.isEmpty()) {
            throw new NotFoundException(messageService.getMessage(Messages.RuleMessages.RULE_NOT_FOUND));
        }
    }
}
