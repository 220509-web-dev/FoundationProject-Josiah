package dev.josiah.complaintDepartment;

public class AuthExceptions {
    public static class NegativeIdException extends AppExceptions {
        public NegativeIdException(String msg) {
            super(msg);
        }
    }


}
