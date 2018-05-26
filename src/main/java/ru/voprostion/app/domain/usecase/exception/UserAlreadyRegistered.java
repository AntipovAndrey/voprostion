package ru.voprostion.app.domain.usecase.exception;

public class UserAlreadyRegistered extends RuntimeException {
    public UserAlreadyRegistered() {
    }

    public UserAlreadyRegistered(String message) {
        super(message);
    }

    public UserAlreadyRegistered(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyRegistered(Throwable cause) {
        super(cause);
    }

    public UserAlreadyRegistered(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
