package dev.josiah.complaintDepartment;



public class AuthExceptions {
    public static class UsernameFormatException extends AppExceptions {
        public UsernameFormatException(String msg) {
            super(msg);
        }
    }
    public static class UserNotFound extends AppExceptions {
        public UserNotFound(String msg) {
            super(msg);
        }
    }

    public static class InputWasNull extends AppExceptions {
        public InputWasNull(String msg) {
            super(msg);
        }
    }

    public static class InputNotAnInteger extends AppExceptions {
        public InputNotAnInteger(String msg) {
            super(msg);
        }
    }

    public static class ValueOutOfRange extends AppExceptions {
        public ValueOutOfRange(String msg) {
            super(msg);
        }
    }
}
