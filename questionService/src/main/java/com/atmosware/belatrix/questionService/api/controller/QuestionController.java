package com.atmosware.belatrix.questionService.api.controller;

import com.atmosware.belatrix.questionService.business.abstracts.QuestionService;
import com.atmosware.belatrix.questionService.business.dto.requests.question.CreateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.question.CreatedQuestionResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("question-service/api/v1/questions")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    public CreatedQuestionResponse add(@Valid @RequestBody CreateQuestionRequest createQuestionRequest){
       return this.questionService.add(createQuestionRequest);
    }
}
