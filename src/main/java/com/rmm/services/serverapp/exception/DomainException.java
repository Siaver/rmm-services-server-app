package com.rmm.services.serverapp.exception;

/**
 * Exception thrown for domain errors.
 */
public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
