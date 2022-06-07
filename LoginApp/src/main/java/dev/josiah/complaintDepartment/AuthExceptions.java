package dev.josiah.complaintDepartment;

public class AuthExceptions {
    public static class UserNotFound extends AppExceptions {
        public UserNotFound(String msg) {
            super(msg);
        }
    }

    public static class InputNotAnInteger extends AppExceptions {
        public InputNotAnInteger(String msg) {
            super(msg);
        }
    }

    public static class IdOutOfRange extends AppExceptions {
        public IdOutOfRange(String msg) {
            super(msg);
        }
    }


}
