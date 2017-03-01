package com.core.service.exception;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/

public class AccountDoesNotFindException extends RuntimeException {
    public AccountDoesNotFindException() {
        super();
    }

    public AccountDoesNotFindException(String message) {
        super(message);
    }

    public AccountDoesNotFindException(String message, Throwable cause) {
        super(message, cause);
    }
}
