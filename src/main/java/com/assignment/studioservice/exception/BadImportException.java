package com.assignment.studioservice.exception;

public class BadImportException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BadImportException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public BadImportException(String errorMessage) {
        super(errorMessage);
    }
}
