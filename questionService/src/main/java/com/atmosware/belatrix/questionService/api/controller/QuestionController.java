package com.atmosware.belatrix.questionService.api.controller;

import com.atmosware.belatrix.questionService.business.abstracts.QuestionService;
import com.atmosware.belatrix.questionService.business.dto.requests.option.AddOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.option.DeleteOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.question.CreateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.question.UpdateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.option.AddOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.option.DeletedOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.CreatedQuestionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.DeleteQuestionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.GetAllQuestionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.UpdatedQuestionResponse;
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
    @GetMapping("/organization")
    public List<GetAllQuestionResponse> getAll(HttpServletRequest request){
        return this.questionService.getAll(request);
    }
    @DeleteMapping("{id}")
    public DeleteQuestionResponse delete(@PathVariable Long id){
        return this.questionService.delete(id);
    }
    @DeleteMapping("organization/{id}")
    public DeleteQuestionResponse delete(@PathVariable Long id,HttpServletRequest request){
        return this.questionService.delete(id,request);
    }
    @PutMapping("{id}")
    public UpdatedQuestionResponse update(@Valid @RequestBody UpdateQuestionRequest updateQuestionRequest,@PathVariable Long id){
        return this.questionService.update(updateQuestionRequest,id);
    }
    @PutMapping("organization/{id}")
    public UpdatedQuestionResponse update(@Valid @RequestBody UpdateQuestionRequest updateQuestionRequest,@PathVariable Long id,HttpServletRequest request){
        return this.questionService.update(updateQuestionRequest,id,request);
    }
    @PutMapping("add-option/{id}")
    public AddOptionResponse addOption(@Valid @RequestBody AddOptionRequest addOptionRequest, @PathVariable Long id){
        return this.questionService.addOptionToQuestion(addOptionRequest,id);
    }
    @PutMapping("add-option/organization/{id}")
    public AddOptionResponse addOption(@Valid @RequestBody AddOptionRequest addOptionRequest, @PathVariable Long id,HttpServletRequest request){
        return this.questionService.addOptionToQuestion(addOptionRequest,id,request);
    }
    @DeleteMapping("delete-option/{id}")
    public DeletedOptionResponse deleteOption(@Valid @RequestBody DeleteOptionRequest deleteOptionRequest , @PathVariable Long id){
        return this.questionService.deleteOptionOfQuestion(deleteOptionRequest,id);
    }
    @DeleteMapping("delete-option/organization/{id}")
    public DeletedOptionResponse deleteOption(@Valid @RequestBody DeleteOptionRequest deleteOptionRequest , @PathVariable Long id, HttpServletRequest request){
        return this.questionService.deleteOptionOfQuestion(deleteOptionRequest,id,request);
    }
}
