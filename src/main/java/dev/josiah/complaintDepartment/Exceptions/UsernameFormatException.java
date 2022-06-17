package dev.josiah.complaintDepartment.Exceptions;

public class UsernameFormatException extends Exception {
    public UsernameFormatException() {
    }

    public UsernameFormatException(String message) {
        super(message);
    }

    public UsernameFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameFormatException(Throwable cause) {
        super(cause);
    }

    public UsernameFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
