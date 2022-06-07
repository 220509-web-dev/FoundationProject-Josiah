package dev.josiah.services;

import com.google.common.hash.Hashing;
import dev.josiah.dtos.UserPass;
import dev.josiah.entities.User;

import java.nio.charset.StandardCharsets;

public class AuthUser{
    final private static String mustEndWith = "@revature.net";
    final private static int minUserLen = mustEndWith.length() + 1;
    final private static int minPassLen = 8;

    public static void authenticate(UserPass userPass){

        //initial check
        // then if that's good
        //Database check for if user exists
        // throws error if username is not in DB
        // throws error if password not correct
    }
    public static String authenticate(String username){

        // is the username good?
        // if so, query DB
        // return result
        return null;
    }
    private static void initialCheck(String user, String pass, int passLen) {
        if (user == null) ;// throw error
        if (pass == null) ;// throw error
        if (user.length() < minUserLen) ;// throw error

    }
}
