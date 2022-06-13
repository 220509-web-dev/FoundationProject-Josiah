package dev.josiah.complaintDepartment.Exceptions;

public class UsernameNotAvailableException extends Exception {
    public UsernameNotAvailableException() {
    }

    public UsernameNotAvailableException(String message) {
        super(message);
    }

    public UsernameNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameNotAvailableException(Throwable cause) {
        super(cause);
    }

    public UsernameNotAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
