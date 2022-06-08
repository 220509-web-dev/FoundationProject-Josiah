package dev.josiah.complaintDepartment;

public class AuthExceptions {
    public static class UsernameFormatException extends AppExceptions {
        public UsernameFormatException(String msg) {
            super(msg);
        }}
    public static class UserNotFoundException extends AppExceptions {
        public UserNotFoundException(String msg) {
            super(msg);
        }}
    public static class InputWasNullException extends AppExceptions {
        public InputWasNullException(String msg) {
            super(msg);
        }}
    public static class InputNotAnIntegerException extends AppExceptions {
        public InputNotAnIntegerException(String msg) {
            super(msg);
        }}
    public static class ValueOutOfRangeException extends AppExceptions {
        public ValueOutOfRangeException(String msg) { super(msg); }}
    public static class UsernameNotAvailableException extends AppExceptions {
        public UsernameNotAvailableException(String msg) { super(msg); }}
    public static class InvalidCredentialsException extends AppExceptions {
        public InvalidCredentialsException(String msg) { super(msg); }}
}
