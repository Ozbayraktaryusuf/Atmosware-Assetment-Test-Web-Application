package com.atmosware.belatrix.managmentService.core.exceptions.problem_details;

public class AuthorizationProblemDetails extends ProblemDetails {
    public AuthorizationProblemDetails() {
        setTitle("Forbidden");
        setDetail("You are not authorized to access this resource.");
        setType("http://mydomain.com/exceptions/authorization");
        setStatus("403");
    }
}
