package com.mborodin.javacourse;

public class InvalidFeedbackException extends RuntimeException {
    public InvalidFeedbackException(String message) {
        super(message);
    }
}