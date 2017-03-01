package com.core.service.exception;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/

public class TodoNotFountException extends RuntimeException {

    public TodoNotFountException() {
        super();
    }

    public TodoNotFountException(String message) {
        super(message);
    }

    public TodoNotFountException(String message, Throwable cause) {
        super(message, cause);
    }
}
