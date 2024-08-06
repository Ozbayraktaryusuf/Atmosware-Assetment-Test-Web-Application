package com.atmosware.belatrix.examSercvice.business.concretes;

import com.atmosware.belatrix.examSercvice.business.abstracts.TestQuestionService;
import com.atmosware.belatrix.examSercvice.business.dtos.requests.testQuestion.CreateTestQuestionRequest;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.AddedQuestionToTestResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.CreatedTestQuestionResponse;
import com.atmosware.belatrix.examSercvice.business.dtos.responses.testQuestion.DeletedQuestionFromTestResponse;
import com.atmosware.belatrix.examSercvice.business.mappers.TestQuestionMapper;
import com.atmosware.belatrix.examSercvice.business.rules.TestQuestionBusinessRules;
import com.atmosware.belatrix.examSercvice.dataAccess.TestQuestionRepository;
import com.atmosware.belatrix.examSercvice.entities.concretes.Test;
import com.atmosware.belatrix.examSercvice.entities.concretes.TestQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.ref.PhantomReference;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestQuestionManager implements TestQuestionService {
    private final TestQuestionMapper testQuestionMapper;
    private final TestQuestionRepository testQuestionRepository;
    private final TestQuestionBusinessRules testQuestionBusinessRule;


    @Override
    public List<CreatedTestQuestionResponse> createTestQuestion(List<CreateTestQuestionRequest> createTestQuestionRequests, Test test) {
        List<TestQuestion> testQuestions = this.testQuestionMapper.toTestQuestion(createTestQuestionRequests);

        for (TestQuestion testQuestion : testQuestions) {
            testQuestion.setTest(test);
        }
        this.testQuestionRepository.saveAll(testQuestions);

        return this.testQuestionMapper.toCreatedTestQuestionResponse(testQuestions);
    }

    @Override
    public AddedQuestionToTestResponse addQuestionToTest(Long questionId, Test test) {
        TestQuestion testQuestion = new TestQuestion(questionId, test);

        return this.testQuestionMapper.toAddedQuestionToTestResponse(this.testQuestionRepository.save(testQuestion));
    }

    @Override
    public DeletedQuestionFromTestResponse deleteQuestionFromTest(Long questionId, Test test) {
        Optional<TestQuestion> optionalTestQuestion = this.testQuestionRepository.findByTestIdAndQuestionId(questionId, test.getId());
        this.testQuestionBusinessRule.testQuestionShouldBeExists(optionalTestQuestion);
        this.testQuestionBusinessRule.questionCanNotObtainOptionsLessThanTwo(test.getTestQuestions());

        TestQuestion testQuestion = optionalTestQuestion.get();
        testQuestion.setDeletedDate(LocalDateTime.now());

        return this.testQuestionMapper.toDeletedQuestionFromTestResponse(testQuestion);
    }

    @Override
    public void deleteAll(List<TestQuestion> testQuestions) {
        testQuestions.forEach(testQuestion -> testQuestion.setDeletedDate(LocalDateTime.now()));

        this.testQuestionRepository.saveAll(testQuestions);
    }
}
