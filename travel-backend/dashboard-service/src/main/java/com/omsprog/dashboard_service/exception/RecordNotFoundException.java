package com.omsprog.dashboard_service.exception;

public class RecordNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Record does not exist in %s";

    public RecordNotFoundException(String tableName) {
        super(String.format(ERROR_MESSAGE, tableName));
    }
}