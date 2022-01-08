package com.proof.api;

import com.proof.exceptions.NotFoundException;
import com.proof.exceptions.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
public class CustomExceptionHandler {

    private static final String LOG_MESSAGE =
            "An unexpected error has occurred. Error code: {}. Method: {}. Path: {}. HttpStatus: {}. Message: {}";
    private static final String BINDING_ERROR = "Request validation error. Check errors for more information.";
    private static final String UNEXPECTED_ERROR = "An unexpected error has occurred";

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ErrorResponse> handleNotFoundException(
            NotFoundException notFoundException, ServletWebRequest request) {
        return handleCommonException(notFoundException, notFoundException.getMessage(), request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponse> handleValidationException(
            ValidationException validationException, ServletWebRequest request) {

        return handleComplexException(validationException,
                validationException.getMessage(),
                validationException.getErrors(),
                request,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponse> handleBindException(
            BindException bindException, ServletWebRequest request) {

        List<String> errors = bindException.
                getAllErrors().
                stream().
                map(objectError -> {
                    if (objectError instanceof FieldError) {
                        return ((FieldError) objectError).getField() + " " + objectError.getDefaultMessage();
                    } else {
                        return objectError.getDefaultMessage();
                    }
                }).
                collect(Collectors.toList());

        return handleComplexException(bindException, BINDING_ERROR, errors, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponse> handleException(
            Exception exception, ServletWebRequest request) {
        return handleCommonException(exception, UNEXPECTED_ERROR, request, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> handleCommonException(
            Exception exception, String message, ServletWebRequest request, HttpStatus httpStatus) {
        String errorCode = UUID.randomUUID().toString();
        logException(errorCode, exception, request, httpStatus);

        return ResponseEntity
                .status(httpStatus)
                .body(ErrorResponse.builder().errorCode(errorCode).message(message).build());
    }


    private ResponseEntity<ErrorResponse> handleComplexException(
            Exception exception, String message, List<String> errors, ServletWebRequest request, HttpStatus httpStatus) {
        String errorCode = UUID.randomUUID().toString();
        logException(errorCode, exception, request, httpStatus);

        return ResponseEntity
                .status(httpStatus)
                .body(ErrorResponse.builder().errorCode(errorCode).message(message).errors(errors).build());
    }

    private void logException(String errorCode, Exception exception, ServletWebRequest request, HttpStatus httpStatus) {
        log.error(LOG_MESSAGE,
                errorCode,
                request.getHttpMethod(),
                request.getRequest().getServletPath(),
                httpStatus,
                exception.getMessage());
    }
}
