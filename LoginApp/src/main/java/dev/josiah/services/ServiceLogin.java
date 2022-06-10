package dev.josiah.services;

import dev.josiah.complaintDepartment.Exceptions.*;
import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.dtos.UserPass;
import dev.josiah.entities.User;
import dev.josiah.entities.UserPriv;

import java.sql.SQLException;

import static dev.josiah.services.Encrypt.encrypt;
import static dev.josiah.services.validation.ValidatePassword.validatePassword;
import static dev.josiah.services.validation.ValidateUsername.validateUsername;


public class ServiceLogin {
    public static User login(UserDAO userDAO, UserPrivDAO upDAO, UserPass userPass) throws IllegalCharacterException, UsernameFormatException, InputWasNullException, ValueOutOfRangeException, SQLException, UserNotFoundException {
        String username = userPass.getUsername();
        String password = userPass.getPassword();
        validateUsername(username);
        validatePassword(password); // error means username or password didn't meet constraints
        User user;
        UserPriv up;
        user = userDAO.getUserByUsername(username);
        up = upDAO.getUserInfoById(user.getUser_id());
        if (user == null) throw new UserNotFoundException();
        if (encrypt(password).equals(up.getPassword())) {
            return user;
        } else {throw new RuntimeException();} // 401 : UNAUTHORIZED; INVALID USER+PASS COMBO
        // 403 USER WAS LOGGED IN AND HAD A TOKEN/SESSION BUT...
        // IS TRYING TO HIT AN ENDPOINT THEY'RE "FORBIDDEN" FROM GOING TO

    }
}
