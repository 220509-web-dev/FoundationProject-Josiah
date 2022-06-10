package dev.josiah.services;

import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.dtos.UserInfo;

import static dev.josiah.services.validation.ValidatePassword.validatePassword;
import static dev.josiah.services.validation.ValidateUsername.validateUsername;

public class ServiceRegisterUser {
    public static void register(UserDAO userDAO, UserPrivDAO upDAO, UserInfo userInfo) {
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        validateUsername(username);
        validatePassword(password);
        // USER NOT AVAILABLE : 409
        // 409 : UNIQUENESS CONSTRAINT VIOLATION
        // 409 MEANS "CONFLICT"

    }
}
/*
        username
        password
        fname
        lname
        address1
        address2
        city
        state
        postalcode
 */
