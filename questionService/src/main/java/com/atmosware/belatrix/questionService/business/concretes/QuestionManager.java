package com.atmosware.belatrix.questionService.business.concretes;

import com.atmosware.belatrix.core.services.JwtService;
import com.atmosware.belatrix.questionService.business.abstracts.OptionService;
import com.atmosware.belatrix.questionService.business.abstracts.QuestionService;
import com.atmosware.belatrix.questionService.business.dto.requests.question.CreateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.option.CreatedOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.CreatedQuestionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.GetAllQuestionResponse;
import com.atmosware.belatrix.questionService.business.mappers.QuestionMapper;
import com.atmosware.belatrix.questionService.dataAccess.QuestionRepository;
import com.atmosware.belatrix.questionService.entities.concretes.Question;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionManager implements QuestionService {
    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;
    private final OptionService optionService;
    private final JwtService jwtService;
    @Override
    @Transactional
    public CreatedQuestionResponse add(CreateQuestionRequest createQuestionRequest, HttpServletRequest request) {
        //TODO:En az bir doğru cevap olması lazım bunun kontrol mekanizması
        Question question = this.questionMapper.toQuestion(createQuestionRequest);

        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);

        List<String> roles=this.jwtService.extractRoles(token);

        if (roles.get(0).equals("organization")){
            UUID organizationId = UUID.fromString(this.jwtService.getClaims(token).get("organizationId").toString());
            question.setOrganizationId(organizationId);
        }

        Question createdQuestion=this.questionRepository.save(question);

        List<CreatedOptionResponse> createdOptionResponse = this.optionService.add(createQuestionRequest.createOptionRequests(),question);

        CreatedQuestionResponse createdQuestionResponse = this.questionMapper.toCreatedQuestionResponse(question);
        createdQuestionResponse.setCreateOptionResponse(createdOptionResponse);

        return createdQuestionResponse;
    }

    @Override
    public List<GetAllQuestionResponse> getAll() {
        //TODO: paging yapmayı unutma
        List<Question> questionList = this.questionRepository.findAll();

        return this.questionMapper.toGetAllQuestionResponse(questionList);
    }
}
