package com.atmosware.belatrix.questionService.core.exceptions.problem_details;

public class NotFoundProblemDetails extends ProblemDetails {
    public NotFoundProblemDetails() {
        setTitle("Not Found");
        setType("http://mydomain.com/exceptions/not-found");
        setStatus("404");
    }
}
