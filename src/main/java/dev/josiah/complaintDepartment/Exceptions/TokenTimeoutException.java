package dev.josiah.complaintDepartment.Exceptions;

public class TokenTimeoutException extends Exception {
    public TokenTimeoutException() {
    }

    public TokenTimeoutException(String message) {
        super(message);
    }

    public TokenTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenTimeoutException(Throwable cause) {
        super(cause);
    }

    public TokenTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
