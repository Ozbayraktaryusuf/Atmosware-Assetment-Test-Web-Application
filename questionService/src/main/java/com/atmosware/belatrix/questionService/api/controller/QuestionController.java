package com.atmosware.belatrix.questionService.api.controller;

import com.atmosware.belatrix.questionService.business.abstracts.QuestionService;
import com.atmosware.belatrix.questionService.business.dto.requests.question.CreateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.question.CreatedQuestionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.GetAllQuestionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("question-service/api/v1/questions")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    public CreatedQuestionResponse add(@Valid @RequestBody CreateQuestionRequest createQuestionRequest,HttpServletRequest request){
       return this.questionService.add(createQuestionRequest, request);
    }
    @GetMapping
    public List<GetAllQuestionResponse> getAll(){
        return this.questionService.getAll();
    }
}
