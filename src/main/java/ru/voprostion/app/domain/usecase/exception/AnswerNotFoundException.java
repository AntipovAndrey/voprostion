package ru.voprostion.app.domain.usecase.exception;

public class AnswerNotFoundException extends RuntimeException {

    public AnswerNotFoundException(String message) {
        super(message);
    }
}
