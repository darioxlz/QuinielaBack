package com.josepadron.quinielaapp.exceptions;

public class UserDontExistsException extends RuntimeException {
    public UserDontExistsException(String message) {
        super(message);
    }
}