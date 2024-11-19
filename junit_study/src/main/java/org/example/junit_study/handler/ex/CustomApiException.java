package org.example.junit_study.handler.ex;

public class CustomApiException extends RuntimeException {
    public CustomApiException(String message) {
        super(message);
    }
}