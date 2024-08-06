package com.atmosware.belatrix.examSercvice.business.rules;

import com.atmosware.belatrix.examSercvice.business.constants.Messages;
import com.atmosware.belatrix.examSercvice.core.business.abstracts.MessageService;
import com.atmosware.belatrix.examSercvice.core.exceptions.types.BusinessException;
import com.atmosware.belatrix.examSercvice.core.exceptions.types.NotFoundException;
import com.atmosware.belatrix.examSercvice.dataAccess.TestRepository;
import com.atmosware.belatrix.examSercvice.entities.concretes.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestBusinessRules {
    private final TestRepository testRepository;
    private final MessageService messageService;

    public void testShouldBeExists(Optional<Test> optionalTest) {
        if (optionalTest.isEmpty()) {
            throw new NotFoundException(messageService.getMessage(Messages.TestMessages.TEST_NOT_FOUND));
        }
    }

    public void testShouldBelongToSameOrganization(Test test, UUID organizationId) {
        if (test.getOrganizationId() == null) {
            throw new BusinessException(messageService.getMessage(Messages.TestMessages.TEST_SHOULD_BE_BELONGED_TO_ORGANIZATION));
        } else if (!test.getOrganizationId().equals(organizationId)) {
            throw new BusinessException(messageService.getMessage(Messages.TestMessages.TEST_SHOULD_BE_BELONGED_TO_ORGANIZATION));
        }
    }

    public void testShouldNotBeStarted(Test test) {
        if (test.getStartDate().isBefore(LocalDateTime.now())) {
            throw new BusinessException(messageService.getMessage(Messages.TestMessages.TEST_SHOULD_NOT_BE_STARTED));
        }
    }

    public void testShouldBeStartedButNotEnded(Test test) {
        if (test.getStartDate().isBefore(LocalDateTime.now()) && test.getEndDate().isAfter(LocalDateTime.now())) {
            throw new BusinessException(messageService.getMessage(Messages.TestMessages.TEST_SHOULD_BE_STARTED_BUT_NOT_ENDED));
        }
    }
}
