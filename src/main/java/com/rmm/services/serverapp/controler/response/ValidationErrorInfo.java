package com.rmm.services.serverapp.controler.response;

/**
 * Used to store information about a validation error.
 */
public class ValidationErrorInfo {
    /**
     * Represents the invalid member (field/property).
     */
    private final String attribute;

    /**
     * Represents the error message.
     */
    private final String message;

    public ValidationErrorInfo(String attribute, String message) {
        this.attribute = attribute;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getAttribute() {
        return attribute;
    }
}