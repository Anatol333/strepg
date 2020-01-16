package com.cfr.kozhukhar.exception;

public class AppException extends Exception {

    public AppException(String message, Throwable ex) {
        super(message, ex);
    }
}
