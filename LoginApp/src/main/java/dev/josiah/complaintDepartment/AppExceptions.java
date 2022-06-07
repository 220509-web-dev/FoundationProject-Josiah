package dev.josiah.complaintDepartment;

public class AppExceptions extends RuntimeException {
    private String message;

    public AppExceptions(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }
}
