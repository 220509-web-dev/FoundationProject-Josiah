package dev.josiah.complaintDepartment.Exceptions;

public class ValueOutOfRangeException extends Exception {
    public ValueOutOfRangeException() {
    }

    public ValueOutOfRangeException(String message) {
        super(message);
    }

    public ValueOutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValueOutOfRangeException(Throwable cause) {
        super(cause);
    }

    public ValueOutOfRangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
