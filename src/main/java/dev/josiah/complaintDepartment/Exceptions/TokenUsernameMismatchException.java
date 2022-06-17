package dev.josiah.complaintDepartment.Exceptions;

public class TokenUsernameMismatchException extends Exception {
    public TokenUsernameMismatchException() {
    }

    public TokenUsernameMismatchException(String message) {
        super(message);
    }

    public TokenUsernameMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenUsernameMismatchException(Throwable cause) {
        super(cause);
    }

    public TokenUsernameMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
