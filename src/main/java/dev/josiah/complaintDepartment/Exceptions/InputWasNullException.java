package dev.josiah.complaintDepartment.Exceptions;

public class InputWasNullException extends Exception {
    public InputWasNullException() {
    }

    public InputWasNullException(String message) {
        super(message);
    }

    public InputWasNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputWasNullException(Throwable cause) {
        super(cause);
    }

    public InputWasNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
