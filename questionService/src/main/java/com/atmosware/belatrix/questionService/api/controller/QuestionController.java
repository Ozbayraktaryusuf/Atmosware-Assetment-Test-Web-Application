package com.atmosware.belatrix.questionService.api.controller;

import com.atmosware.belatrix.questionService.business.abstracts.QuestionService;
import com.atmosware.belatrix.questionService.business.dto.requests.option.AddOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.option.DeleteOptionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.question.CreateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.requests.question.UpdateQuestionRequest;
import com.atmosware.belatrix.questionService.business.dto.responses.option.AddOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.option.DeletedOptionResponse;
import com.atmosware.belatrix.questionService.business.dto.responses.question.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
    public Page<GetAllQuestionResponse> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "size", defaultValue = "10") int size){
        return this.questionService.getAll(page,size);
    }
    @GetMapping("/organization")
    public Page<GetAllQuestionResponse> getAll(@RequestParam int page,
                                               @RequestParam(name = "size", defaultValue = "10") int size,
                                               HttpServletRequest request){
        return this.questionService.getAll(page,size,request);//TODO: page=0 diyince validation hatası veriyor sor?
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
    @GetMapping("{id}")
    public GetByIdQuestionResponse  getById(@PathVariable Long id){
        return this.questionService.getById(id);
    }
    @GetMapping("/organization/{id}")
    public GetByIdQuestionResponse  getById(@PathVariable Long id,HttpServletRequest request){
        return this.questionService.getById(id,request);
    }
}
