package dev.josiah.services;

import dev.josiah.complaintDepartment.AuthExceptions;
import dev.josiah.daos.UserDAO;
import dev.josiah.entities.User;

import java.util.ArrayList;

public class ServiceGetUserByUsername {
    // set anything to null to remove validation for it
    final private static Integer maxLen = 255; // Database constraint for longest possible username
    final private static String userMustEndWith = "@revature.net";
    final private static String userMustStartWith = null;
    final private static ArrayList<String> userCannotContain = null;
    //= {" ", "\n", ""+'\u00A0', "!", "*"}; // illegal characters
    // ArrayList<String> gfg = new ArrayList<String>(
    //            Arrays.asList("Geeks",
    //                          "for",
    //                          "Geeks"));

    public static User ServiceUsernameRequest(String username_feed, UserDAO userDAO) {
        validateUsername(username_feed);
        // If we get here, username has passed validation tests
        return userDAO.getUserByUsername(username_feed);
    }
    private static void validateUsername(String username_feed) {
        if (username_feed == null) {
            throw new AuthExceptions.InputWasNull("Username was null");
            // Shouldn't ever happen. Servlet won't call this if username param is null
        }
        if ((maxLen != null) && (username_feed.length() > maxLen)) {
            throw new AuthExceptions.ValueOutOfRange("Username length was " + username_feed.length() +
                    ", expected value to be <= " + maxLen);
        }
        if ((userMustStartWith != null) && !(username_feed.startsWith(userMustStartWith))) {
            //UsernameFormatException
            throw new AuthExceptions.UsernameFormatException("Username must start with " + userMustStartWith +
                    ", but username entered was " + username_feed);
        }
        if ((userMustEndWith != null) && !(username_feed.endsWith(userMustEndWith))) {
            //UsernameFormatException
            throw new AuthExceptions.UsernameFormatException("Username must end with " + userMustEndWith +
                    ", but username entered was " + username_feed);
        }
        if ((userCannotContain != null) && (true)) { //userCannotContain.stream()
            // col.stream().allMatch(i -> i>0);
            //UsernameFormatException
            throw new AuthExceptions.UsernameFormatException("Username must end with " + userMustEndWith +
                    ", but username entered was " + username_feed);
        }

    }
}
