package com.atmosware.belatrix.questionService.grpc.client.concretes;

import com.atmosware.belatrix.questionService.grpc.client.abstracts.ExamClientService;
import net.devh.boot.grpc.client.inject.GrpcClient;

import org.springframework.stereotype.Service;
import tr.com.atmosware.gyt.grpc.ControlExamIsStartedRequest;
import tr.com.atmosware.gyt.grpc.ExamServiceGrpc;

@Service
public class ExamClientServiceImpl implements ExamClientService {
    @GrpcClient("examService")
    ExamServiceGrpc.ExamServiceBlockingStub examServiceBlockingStub ;
    @Override
    public Boolean controlQuestionIsUsed(Long questionId) {
        final var request = ControlExamIsStartedRequest.newBuilder().setQuestionId(questionId).build();
        final var response = examServiceBlockingStub.controlExamIsStarted(request);

        return response.getIsStarted();
    }
}
