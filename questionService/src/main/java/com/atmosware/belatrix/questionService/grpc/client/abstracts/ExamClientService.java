package com.atmosware.belatrix.questionService.grpc.client.abstracts;

public interface ExamClientService {
    Boolean controlQuestionIsUsed(Long questionId);
}
