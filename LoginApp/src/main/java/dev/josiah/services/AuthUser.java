package dev.josiah.services;

import dev.josiah.complaintDepartment.AuthExceptions;

import java.util.ArrayList;
import java.util.Arrays;

public class AuthUser{
    // set anything to null to remove validation for it
    final private static Integer maxLen = 255; // Database constraint for longest possible username
    final private static String userMustEndWith = "@revature.net";
    final private static String userMustStartWith = null;
    final private static ArrayList<String> userCannotContain =
            new ArrayList<String>(Arrays.asList(" ", "\n", ""+'\u00A0', "!", "*"));
    final private static Integer minNonRequiredChars = 0;
    final private static Integer minLen = ((userMustEndWith == null) ? 0:userMustEndWith.length()) +
            ((userMustStartWith == null) ? 0:userMustStartWith.length()) + minNonRequiredChars;

    public static void checkUsername(String username) {
        if (username == null) {
            throw new AuthExceptions.InputWasNullException("Username was null");
            // Shouldn't ever happen. Servlet won't call this if username param is null
        }

        if ((maxLen != null) && (username.length() > maxLen)) {
            throw new AuthExceptions.ValueOutOfRangeException("Username length was " + username.length() +
                    ", expected length to be at most " + maxLen);
        }

        if ((minLen != null) && (username.length() < minLen)) {
            throw new AuthExceptions.ValueOutOfRangeException("Username length was " + username.length() +
                    ", expected length to be at least " + minLen);
        }

        if ((userMustStartWith != null) && !(username.startsWith(userMustStartWith))) {
            //UsernameFormatException
            throw new AuthExceptions.UsernameFormatException("Username must start with " + userMustStartWith +
                    ", but username entered was " + username);
        }

        if ((userMustEndWith != null) && !(username.endsWith(userMustEndWith))) {
            //UsernameFormatException
            throw new AuthExceptions.UsernameFormatException("Username must end with " + userMustEndWith +
                    ", but username entered was " + username);
        }

        if ((userCannotContain != null) &&
                userCannotContain.stream().allMatch(t -> username.contains(t))) {
            throw new AuthExceptions.UsernameFormatException(username+" contained an illegal character");
        }
    }
}
