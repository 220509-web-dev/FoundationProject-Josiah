package dev.josiah.services.validation;


import dev.josiah.complaintDepartment.Exceptions.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ValidatePassword {
    // set anything to null to remove validation for it
    final private static Integer maxLen = 30; // Arbitrary constraint
    final private static Integer minLen = 8;
    final private static ArrayList<String> cannotContain =
            new ArrayList<String>(Arrays.asList(" ", "\n", ""+'\u00A0'));

    public static void validatePassword(String password) throws InputWasNullException, ValueOutOfRangeException, UsernameFormatException {
        if (password == null) {
            throw new InputWasNullException("Password was null");
        }
        if ((maxLen != null) && (password.length() > maxLen)) {
            throw new ValueOutOfRangeException("Password length was " + password.length() +
                    ", expected length to be at most " + maxLen);
        }
        if ((minLen != null) && (password.length() < minLen)) {
            throw new ValueOutOfRangeException("Password length was " + password.length() +
                    ", expected length to be at least " + minLen);
        }
        if ((cannotContain != null) &&
                cannotContain.stream().allMatch(t -> password.contains(t))) {
            throw new UsernameFormatException("Password entered contained an illegal character");
        }
    }
}
