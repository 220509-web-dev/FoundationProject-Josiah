package dev.josiah.complaintDepartment.Exceptions;

public class InputNotAnIntegerException extends Exception {
    public InputNotAnIntegerException() {
    }

    public InputNotAnIntegerException(String message) {
        super(message);
    }

    public InputNotAnIntegerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputNotAnIntegerException(Throwable cause) {
        super(cause);
    }

    public InputNotAnIntegerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
