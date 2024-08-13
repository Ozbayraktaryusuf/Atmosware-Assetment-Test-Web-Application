package com.atmosware.belatrix.examSercvice.grpc;

import com.atmosware.belatrix.examSercvice.business.abstracts.TestService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import tr.com.atmosware.gyt.grpc.ExamServiceGrpc;
import tr.com.atmosware.gyt.grpc.GetExamRequest;
import tr.com.atmosware.gyt.grpc.GetExamResponse;

@GrpcService
@RequiredArgsConstructor
public class ExamGrpcService extends ExamServiceGrpc.ExamServiceImplBase {
    private final TestService testService;

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
}
