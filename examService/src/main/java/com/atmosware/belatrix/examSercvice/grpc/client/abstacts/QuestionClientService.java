package com.atmosware.belatrix.examSercvice.grpc.client.abstacts;

import java.util.List;
import java.util.UUID;

public interface QuestionClientService {
    List<Long> getQuestionsId(UUID organizationId);
    void controlQuestions(List<Long> questionIds);
}
