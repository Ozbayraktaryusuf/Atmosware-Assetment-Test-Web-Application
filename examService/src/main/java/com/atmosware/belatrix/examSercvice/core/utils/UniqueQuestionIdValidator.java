package com.atmosware.belatrix.examSercvice.core.utils;

import com.atmosware.belatrix.examSercvice.business.dtos.requests.test.CreateTestRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.CreateTestQuestionRequest;
import com.atmosware.belatrix.examSercvice.core.utils.annotations.UniqueQuestionIds;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueQuestionIdValidator implements ConstraintValidator<UniqueQuestionIds, CreateTestRequest> {

    @Override
    public boolean isValid(CreateTestRequest createTestRequest, ConstraintValidatorContext context) {
        List<CreateTestQuestionRequest> questionRequests = createTestRequest.createTestQuestionRequests();

        Set<Long> uniqueIds = new HashSet<>();
        for (CreateTestQuestionRequest questionRequest : questionRequests) {
            if (!uniqueIds.add(questionRequest.questionId())) {
                return false; // Aynı ID tekrar ederse, validasyon başarısız olur
            }
        }
        return true;
    }
}