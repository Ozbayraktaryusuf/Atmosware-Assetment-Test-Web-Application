package com.atmosware.belatrix.examSercvice.grpc.client.concretes;

import com.atmosware.belatrix.examSercvice.core.exceptions.types.BusinessException;
import com.atmosware.belatrix.examSercvice.core.exceptions.types.NotFoundException;
import com.atmosware.belatrix.examSercvice.grpc.client.abstacts.QuestionClientService;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import tr.com.atmosware.gyt.grpc.ControlQuestionRequest;
import tr.com.atmosware.gyt.grpc.GetQuestionsRequest;

import tr.com.atmosware.gyt.grpc.QuestionServiceGrpc;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionClientServiceImpl implements QuestionClientService {
    @GrpcClient("questionService")
    QuestionServiceGrpc.QuestionServiceBlockingStub questionServiceBlockingStub;

    @Override
    public List<Long> getQuestionsId(UUID organizationId) {
        final var request = GetQuestionsRequest.newBuilder().setOrganizationId(organizationId.toString()).build();
        final var response = questionServiceBlockingStub.getQuestionsWithOrganizationIdAndNullOrganization(request);

        return response.getQuestionIdList();
    }

    @Override
    public void controlQuestions(List<Long> questionIds) {
        try {
            final var request = ControlQuestionRequest.newBuilder().addAllQuestionId(questionIds).build();
            questionServiceBlockingStub.controlQuestion(request);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                System.err.println("Error: " + e.getStatus().getDescription());
                throw new NotFoundException("Soru bulunmadi.");
            } else {
                System.err.println("Unexpected Error: " + e.getStatus().getDescription());
                throw new BusinessException("An unexpected error occurred in ExamService.");
            }
        }
    }
}
