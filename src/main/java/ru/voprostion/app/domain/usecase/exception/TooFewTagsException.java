package ru.voprostion.app.domain.usecase.exception;

public class TooFewTagsException extends RuntimeException {
    public TooFewTagsException() {
    }

    public TooFewTagsException(String message) {
        super(message);
    }

    public TooFewTagsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooFewTagsException(Throwable cause) {
        super(cause);
    }

    public TooFewTagsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
