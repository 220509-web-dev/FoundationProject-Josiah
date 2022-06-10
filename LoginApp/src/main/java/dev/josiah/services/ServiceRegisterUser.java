package dev.josiah.services;

import dev.josiah.complaintDepartment.Exceptions.IllegalCharacterException;
import dev.josiah.complaintDepartment.Exceptions.InputWasNullException;
import dev.josiah.complaintDepartment.Exceptions.UsernameFormatException;
import dev.josiah.complaintDepartment.Exceptions.ValueOutOfRangeException;
import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.dtos.UserInfo;
import dev.josiah.entities.User;

import java.sql.SQLException;

import static dev.josiah.services.validation.ValidatePassword.validatePassword;
import static dev.josiah.services.validation.ValidateUsername.validateUsername;

public class ServiceRegisterUser {
    public static void register(UserDAO userDAO, UserPrivDAO upDAO, UserInfo userInfo) throws SQLException, IllegalCharacterException, UsernameFormatException, InputWasNullException, ValueOutOfRangeException {
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        validateUsername(username);
        validatePassword(password);

        User user = userDAO.getUserByUsername(username);
        if (user != null) {

        }

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
