package dev.josiah.services.validation;

import dev.josiah.complaintDepartment.AuthExceptions;

import java.util.ArrayList;
import java.util.Arrays;

public class ValidatePassword {
    // set anything to null to remove validation for it
    final private static Integer maxLen = 30; // Arbitrary constraint
    final private static Integer minLen = 8;
    final private static ArrayList<String> cannotContain =
            new ArrayList<String>(Arrays.asList(" ", "\n", ""+'\u00A0'));

    public static void validatePassword(String password) {
        if (password == null) {
            throw new AuthExceptions.InputWasNullException("Password was null");
        }
        if ((maxLen != null) && (password.length() > maxLen)) {
            throw new AuthExceptions.ValueOutOfRangeException("Password length was " + password.length() +
                    ", expected length to be at most " + maxLen);
        }
        if ((minLen != null) && (password.length() < minLen)) {
            throw new AuthExceptions.ValueOutOfRangeException("Password length was " + password.length() +
                    ", expected length to be at least " + minLen);
        }
        if ((cannotContain != null) &&
                cannotContain.stream().allMatch(t -> password.contains(t))) {
            throw new AuthExceptions.UsernameFormatException("Password entered contained an illegal character");
        }
    }
}
