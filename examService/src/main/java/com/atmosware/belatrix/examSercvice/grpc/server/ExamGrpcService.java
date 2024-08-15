package com.atmosware.belatrix.examSercvice.grpc.server;

import com.atmosware.belatrix.examSercvice.business.abstracts.TestQuestionService;
import com.atmosware.belatrix.examSercvice.business.abstracts.TestService;
import com.atmosware.belatrix.examSercvice.entities.concretes.TestQuestion;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import tr.com.atmosware.gyt.grpc.*;

import java.time.LocalDateTime;

@GrpcService
@RequiredArgsConstructor
public class ExamGrpcService extends ExamServiceGrpc.ExamServiceImplBase {
    private final TestService testService;
    private final TestQuestionService testQuestionService;

    @Override
    public void getExam(GetExamRequest request, StreamObserver<GetExamResponse> responseObserver) {
        final var id = request.getExamId();
        final var exam = testService.getTextForInvitation(id);
        responseObserver.onNext(GetExamResponse.newBuilder()
                .setExamName(exam.getExamName())
                .setDuration(exam.getDuration().toString())
                .setStartDate(exam.getStartDate().toString())
                .setEndDate(exam.getEndDate().toString())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void controlExamIsStarted(ControlExamIsStartedRequest request, StreamObserver<ControlExamIsStartedResponse> responseObserver) {
        final var id= request.getQuestionId();
        final var testQuestions= testQuestionService.findAllTestsQuestionIsUsed(id);
         if (testQuestions.isEmpty()){
             responseObserver.onNext(ControlExamIsStartedResponse.newBuilder()
                     .setIsStarted(false)
                     .build());
             responseObserver.onCompleted();
         }
         else {
             testQuestions.stream()
                     .filter(testQuestion -> testQuestion.getTest().getStartDate().isBefore(LocalDateTime.now()))
                     .findFirst()
                     .ifPresentOrElse(
                             testQuestion -> {
                                 responseObserver.onNext(ControlExamIsStartedResponse.newBuilder()
                                         .setIsStarted(true)
                                         .build());
                                 responseObserver.onCompleted();
                             },
                             () -> {
                                 responseObserver.onNext(ControlExamIsStartedResponse.newBuilder()
                                         .setIsStarted(false)
                                         .build());
                                 responseObserver.onCompleted();
                             }
                     );
         }
    }
}
