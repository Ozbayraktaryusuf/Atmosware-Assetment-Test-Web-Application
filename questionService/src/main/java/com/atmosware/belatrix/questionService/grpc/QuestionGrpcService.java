package com.atmosware.belatrix.questionService.grpc;

import com.atmosware.belatrix.questionService.business.abstracts.QuestionService;
import io.grpc.stub.StreamObserver;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;
import tr.com.atmosware.gyt.grpc.GetQuestionsRequest;
import tr.com.atmosware.gyt.grpc.GetQuestionsResponse;
import tr.com.atmosware.gyt.grpc.QuestionServiceGrpc;

import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class QuestionGrpcService extends QuestionServiceGrpc.QuestionServiceImplBase {
    private final QuestionService questionService;

    @Override
    public void getQuestionsWithOrganizationIdAndNullOrganization(GetQuestionsRequest request, StreamObserver<GetQuestionsResponse> responseObserver) {
        final var id = request.getOrganizationId();
        final var uuid = UUID.fromString(id);
        final var questions = this.questionService.getAllQuestionsWithSpecificOrganizationIdForGrpc(uuid);

        GetQuestionsResponse.Builder responseBuilder = GetQuestionsResponse.newBuilder();

        questions.forEach(question -> responseBuilder.addQuestionId(question.getId()).build());

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
