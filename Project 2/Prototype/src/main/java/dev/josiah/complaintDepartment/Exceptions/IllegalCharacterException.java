package dev.josiah.complaintDepartment.Exceptions;

public class IllegalCharacterException extends Exception {
    public IllegalCharacterException() {
    }

    public IllegalCharacterException(String message) {
        super(message);
    }

    public IllegalCharacterException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCharacterException(Throwable cause) {
        super(cause);
    }

    public IllegalCharacterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
