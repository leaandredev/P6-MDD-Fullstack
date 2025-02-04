package com.openclassrooms.mddapi.exception;

public class NoEntryFoundException extends RuntimeException {
    public NoEntryFoundException(final String message) {
        super(message);
    }

}
