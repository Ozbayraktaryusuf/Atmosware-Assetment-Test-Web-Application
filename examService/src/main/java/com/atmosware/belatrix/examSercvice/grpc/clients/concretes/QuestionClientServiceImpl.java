package com.atmosware.belatrix.examSercvice.grpc.clients.concretes;

import com.atmosware.belatrix.examSercvice.grpc.clients.abstacts.QuestionClientService;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
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
}
