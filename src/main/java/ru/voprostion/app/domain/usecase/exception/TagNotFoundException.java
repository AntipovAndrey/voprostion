package ru.voprostion.app.domain.usecase.exception;

public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException(String message) {
        super(message);
    }
}
