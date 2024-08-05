package com.atmosware.belatrix.examSercvice.business.concretes;

import com.atmosware.belatrix.examSercvice.business.abstracts.TestQuestionService;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.CreateTestQuestionRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.CreatedTestQuestionResponse;
import com.atmosware.belatrix.examSercvice.business.mappers.TestQuestionMapper;
import com.atmosware.belatrix.examSercvice.dataAccess.TestQuestionRepository;
import com.atmosware.belatrix.examSercvice.entities.concretes.Test;
import com.atmosware.belatrix.examSercvice.entities.concretes.TestQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.ref.PhantomReference;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestQuestionManager implements TestQuestionService {
    private final TestQuestionMapper testQuestionMapper;
    private final TestQuestionRepository testQuestionRepository;


    @Override
    public List<CreatedTestQuestionResponse> createTestQuestion(List<CreateTestQuestionRequest> createTestQuestionRequests, Test test) {
        List<TestQuestion> testQuestions = this.testQuestionMapper.toTestQuestion(createTestQuestionRequests);

        for (TestQuestion testQuestion :testQuestions){
            testQuestion.setTest(test);
        }
        return this.testQuestionMapper.toCreatedTestQuestionResponse(testQuestions);
    }
}
