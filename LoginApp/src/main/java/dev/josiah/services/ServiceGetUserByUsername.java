package dev.josiah.services;

import dev.josiah.daos.UserDAO;
import dev.josiah.entities.User;

public class ServiceGetUserByUsername {
    final private static int maxLen = 255; // Database constraint for longest possible username
    final private static String userMustEndWith = "@revature.net";
    final private static String userMustStartWith = null;
    final private static String[] userCannotContain = {" ", "\n", ""+'\u00A0', "!", "*"}; // illegal characters

    public static User ServiceUsernameRequest(String username_feed, UserDAO userDAO) {
        return null;
    }
    private static String validateId(String id_feed) {
        return null;
    }
}
