package com.omsprog.travel.exception;

public class UsernameNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "User does not exist in %s";

    public UsernameNotFoundException(String tableName) {
        super(String.format(ERROR_MESSAGE, tableName));
    }
}