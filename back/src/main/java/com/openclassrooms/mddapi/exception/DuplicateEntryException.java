package com.openclassrooms.mddapi.exception;

public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException(final String message) {
        super(message);
    }
}
