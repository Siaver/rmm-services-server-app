package com.rmm.services.serverapp.controler.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to store information about an error.
 */
public class ErrorInfo {
    /**
     * Represents the error code.
     */
    private int code;

    /**
     * Represents the error message.
     */
    private String message;

    /**
     * Represents description of the error message.
     */
    private String details;

    /**
     * Represents a lis of validation errors if exists.
     */
    private List<ValidationErrorInfo> validationErrors;

    public ErrorInfo() {
        this.validationErrors = new ArrayList<>();
    }

    public ErrorInfo(String message) {
        this();
        this.message = message;
    }

    public ErrorInfo(int code, String message, String details) {
        this(message);

        this.code = code;
        this.details = details;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<ValidationErrorInfo> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<ValidationErrorInfo> validationErrors) {
        this.validationErrors = validationErrors;
    }
}

