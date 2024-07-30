package com.atmosware.belatrix.questionService.business.concretes;

import com.atmosware.belatrix.questionService.business.abstracts.OptionService;
import com.atmosware.belatrix.questionService.business.abstracts.QuestionService;
import com.atmosware.belatrix.questionService.business.dto.requests.question.CreateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.option.CreatedOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.CreatedQuestionResponse;
import com.atmosware.belatrix.questionService.business.mappers.QuestionMapper;
import com.atmosware.belatrix.questionService.dataAccess.QuestionRepository;
import com.atmosware.belatrix.questionService.entities.concretes.Question;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionManager implements QuestionService {
    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;
    private final OptionService optionService;
    @Override
    @Transactional
    public CreatedQuestionResponse add(CreateQuestionRequest createQuestionRequest) {
        //TODO:JWT ti Core'a çekine organization id'yi tokendan çek
        Question question = this.questionMapper.toQuestion(createQuestionRequest);

        Question createdQuestion=this.questionRepository.save(question);

        List<CreatedOptionResponse> createdOptionResponse = this.optionService.add(createQuestionRequest.createOptionRequests(),question);

        CreatedQuestionResponse createdQuestionResponse = this.questionMapper.toCreatedQuestionResponse(question);
        createdQuestionResponse.setCreateOptionResponse(createdOptionResponse);

        return createdQuestionResponse;
    }
}
