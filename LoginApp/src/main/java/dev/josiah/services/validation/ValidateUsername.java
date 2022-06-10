package dev.josiah.services.validation;


import dev.josiah.complaintDepartment.Exceptions.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ValidateUsername {
    // set anything to null to remove validation for it
    final private static Integer maxLen = 255; // Database constraint for longest possible username
    final private static String userMustEndWith = "@revature.net";
    final private static String userMustStartWith = null;
    final private static ArrayList<String> userCannotContain =
            new ArrayList<String>(Arrays.asList(" ", "\n", ""+'\u00A0', "!", "*"));
    final private static Integer minNonRequiredChars = 0;
    final private static Integer minLen = ((userMustEndWith == null) ? 0:userMustEndWith.length()) +
            ((userMustStartWith == null) ? 0:userMustStartWith.length()) + minNonRequiredChars;

    public static void validateUsername(String username) throws InputWasNullException, ValueOutOfRangeException, UsernameFormatException, IllegalCharacterException {
        if (username == null) {
            throw new InputWasNullException("Username was null");
            // Shouldn't ever happen. Servlet won't call this if username param is null
        }

        if ((maxLen != null) && (username.length() > maxLen)) {
            throw new ValueOutOfRangeException("Username length was " + username.length() +
                    ", expected length to be at most " + maxLen);
        }

        if ((minLen != null) && (username.length() < minLen)) {
            throw new ValueOutOfRangeException("Username length was " + username.length() +
                    ", expected length to be at least " + minLen);
        }

        if ((userMustStartWith != null) && !(username.startsWith(userMustStartWith))) {
            //UsernameFormatException
            throw new UsernameFormatException("Username must start with " + userMustStartWith +
                    ", but username entered was " + username);
        }

        if ((userMustEndWith != null) && !(username.endsWith(userMustEndWith))) {
            //UsernameFormatException
            throw new UsernameFormatException("Username must end with " + userMustEndWith +
                    ", but username entered was " + username);
        }

        if ((userCannotContain != null) &&
                userCannotContain.stream().allMatch(t -> username.contains(t))) {
            throw new IllegalCharacterException(username+" contained an illegal character");
        }
    }
}
