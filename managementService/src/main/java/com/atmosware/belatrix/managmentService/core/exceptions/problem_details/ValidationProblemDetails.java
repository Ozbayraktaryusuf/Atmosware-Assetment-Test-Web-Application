package com.atmosware.belatrix.managmentService.core.exceptions.problem_details;

import java.util.Map;

public class ValidationProblemDetails extends ProblemDetails {
    public ValidationProblemDetails() {
        setTitle("Validation Rule Violation");
        setDetail("Validation Problem");
        setType("http://mydomain.com/exceptions/validation");
        setStatus("422");
    }

    private Map<String, String> errors;

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}