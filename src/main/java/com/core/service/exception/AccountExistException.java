package com.core.service.exception;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/

public class AccountExistException extends RuntimeException{
    public AccountExistException() {
        super();
    }

    public AccountExistException(String message) {
        super(message);
    }

    public AccountExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
