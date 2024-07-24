package com.atmosware.belatrix.managmentService.core.exceptions.problem_details;

public class NotFoundProblemDetails extends ProblemDetails {
    public NotFoundProblemDetails() {
        setTitle("Not Found");
        setType("http://mydomain.com/exceptions/not-found");
        setStatus("404");
    }
}
