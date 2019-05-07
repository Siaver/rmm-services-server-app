package com.rmm.services.serverapp.controller;

import com.rmm.services.serverapp.controller.response.ErrorInfo;
import com.rmm.services.serverapp.controller.response.ValidationErrorInfo;
import com.rmm.services.serverapp.exception.DomainException;
import com.rmm.services.serverapp.exception.ObjectNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
    // 400
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info(ex.getClass().getName());

        final ErrorInfo errorResponse = new ErrorInfo(
                HttpStatus.BAD_REQUEST.value(),
                "Your request is not valid!",
                "The following errors were detected during validation.");

        errorResponse.setValidationErrors(this.parseErrors(ex.getBindingResult().getFieldErrors()));
        return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    // 404
    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(final ObjectNotFoundException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("Resource not found!", ex);

        final ErrorInfo errorResponse = new ErrorInfo(HttpStatus.NOT_FOUND.value(), "Resource not found!", ex.getMessage());
        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }


    // 500
    @ExceptionHandler({DomainException.class})
    public ResponseEntity<Object> handleUserFriendlyException(final DomainException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("Domain Error!", ex);

        final ErrorInfo errorResponse = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), "Domain Error!", ex.getMessage());
        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("Internal error!", ex);

        final ErrorInfo errorResponse = new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal error!", ex.getMessage());
        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    private List<ValidationErrorInfo> parseErrors(List<FieldError> errors) {
        return errors.stream()
                .map(error -> new ValidationErrorInfo(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
