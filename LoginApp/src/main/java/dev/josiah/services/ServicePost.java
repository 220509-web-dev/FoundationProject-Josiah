package dev.josiah.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.complaintDepartment.Exceptions.*;
import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.dtos.UserInfo;
import dev.josiah.dtos.UserPass;
import dev.josiah.entities.User;
import dev.josiah.entities.UserPriv;

import java.sql.SQLException;

import static dev.josiah.services.Encrypt.encrypt;
import static dev.josiah.services.validation.ValidatePassword.validatePassword;
import static dev.josiah.services.validation.ValidateUsername.validateUsername;

public class ServicePost {
    public static User login(UserDAO userDAO, UserPrivDAO upDAO, UserPass userPass) throws IllegalCharacterException, UsernameFormatException, InputWasNullException, ValueOutOfRangeException, SQLException, UserNotFoundException, InvalidCredentialsException {
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
        } else {throw new InvalidCredentialsException("Incorrect Password");} // 401 : UNAUTHORIZED; INVALID USER+PASS COMBO
        // 403 USER WAS LOGGED IN AND HAD A TOKEN/SESSION BUT...
        // IS TRYING TO HIT AN ENDPOINT THEY'RE "FORBIDDEN" FROM GOING TO
    }

    public static void register(ObjectMapper caster, UserDAO userDAO, UserPrivDAO upDAO, UserInfo userInfo) throws SQLException, IllegalCharacterException, UsernameFormatException, InputWasNullException, ValueOutOfRangeException, UsernameNotAvailableException, JsonProcessingException {
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        validateUsername(username);
        validatePassword(password);

        User user = userDAO.getUserByUsername(username);
        if (user != null) {
            throw new UsernameNotAvailableException();
            // USER NOT AVAILABLE : 409
            // 409 : UNIQUENESS CONSTRAINT VIOLATION
            // 409 MEANS "CONFLICT"
        }
        user = caster.readValue(caster.writeValueAsString(userInfo), User.class);
        user = userDAO.createUser(user);
        UserPriv up = new UserPriv(user.getUser_id());
        up.encryptAndSetPassword(password);


    }
}
