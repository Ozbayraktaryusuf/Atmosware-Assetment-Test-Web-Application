package com.atmosware.belatrix.examSercvice.core.exceptions.handlers;

import com.atmosware.belatrix.examSercvice.core.exceptions.problem_details.*;
import com.atmosware.belatrix.examSercvice.core.exceptions.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BusinessProblemDetails handleBusinessException(BusinessException exception) {
        BusinessProblemDetails businessProblemDetails = new BusinessProblemDetails();
        businessProblemDetails.setDetail(exception.getMessage());
        return businessProblemDetails;
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public NotFoundProblemDetails handleNotFoundException(NotFoundException exception) {
        NotFoundProblemDetails notFoundProblemDetails = new NotFoundProblemDetails();
        notFoundProblemDetails.setDetail(exception.getMessage());
        return notFoundProblemDetails;
    }

    @ExceptionHandler({AuthorizationException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AuthorizationProblemDetails handleAuthorizationException() {
        return new AuthorizationProblemDetails();
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AuthenticationProblemDetails handleAuthenticationException() {
        return new AuthenticationProblemDetails();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ValidationProblemDetails handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> validationErrors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                validationErrors.put(error.getField(), error.getDefaultMessage())
        );

        ValidationProblemDetails validationProblemDetails = new ValidationProblemDetails();
        validationProblemDetails.setErrors(validationErrors);
        return validationProblemDetails;
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ValidationProblemDetails handleValidationException(ValidationException exception) {
        ValidationProblemDetails validationProblemDetails = new ValidationProblemDetails();
        validationProblemDetails.setErrors(exception.getErrors());
        return validationProblemDetails;
    }

    //@ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    //@ResponseStatus(HttpStatus.UNAUTHORIZED)
    //public AuthenticationProblemDetails handleBadCredentialsException() {
    //    AuthenticationProblemDetails authenticationProblemDetails = new AuthenticationProblemDetails();
    //    authenticationProblemDetails.setDetail(Messages.AuthenticationMessages.INVALID_USER_OR_PASSWORD);
    //    return authenticationProblemDetails;
    //}

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ValidationProblemDetails handleIllegalArgumentException(IllegalArgumentException exception) {
        ValidationProblemDetails validationProblemDetails = new ValidationProblemDetails();
        validationProblemDetails.setErrors(Map.of("error", exception.getMessage()));
        return validationProblemDetails;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public InternalServerProblemDetails handleException(Exception ex) {
        logger.error(ex.toString());
        return new InternalServerProblemDetails();
    }
}
