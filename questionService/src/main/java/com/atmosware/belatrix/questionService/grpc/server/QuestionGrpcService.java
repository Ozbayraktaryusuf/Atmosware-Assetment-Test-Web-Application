package com.atmosware.belatrix.questionService.grpc.server;

import com.atmosware.belatrix.questionService.business.abstracts.QuestionService;
import com.atmosware.belatrix.questionService.business.rules.QuestionBusinessRules;
import com.atmosware.belatrix.questionService.core.exceptions.types.NotFoundException;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;
import tr.com.atmosware.gyt.grpc.*;

import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class QuestionGrpcService extends QuestionServiceGrpc.QuestionServiceImplBase {
    private final QuestionService questionService;
    private final QuestionBusinessRules questionBusinessRules;

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

    @Override
    public void controlQuestion(ControlQuestionRequest request, StreamObserver<Empty> responseObserver) {
        try {
            final var ids= request.getQuestionIdList();
            ids.forEach(this.questionBusinessRules::questionShouldBeExists);
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (NotFoundException e) {
            // Özel hatayı gRPC Status olarak dönüştür ve fırlat
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).augmentDescription("Question not found in QuestionService").asRuntimeException());
        } catch (Exception e) {
            // Diğer hatalar için genel bir gRPC Status döndürün
            responseObserver.onError(Status.INTERNAL.withDescription("An unexpected error occurred").withCause(e).asRuntimeException());
        }
    }
}
