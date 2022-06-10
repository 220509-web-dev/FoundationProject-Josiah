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
        validatePassword(password);
        User user;
        UserPriv up;
        System.out.println("finding user:"+username);
        user = userDAO.getUserByUsername(username);
        if (user == null) {
            System.out.println("Looks like that was null!");
            throw new UserNotFoundException();
        }
        System.out.println("Finding info for:"+user.getUser_id());
        up = upDAO.getUserInfoById(user.getUser_id());
        if (up == null) {
            System.out.println("Looks like that was null");
            throw new UserNotFoundException();
        }
        if (!encrypt(password).equals(up.getPassword())) {
            throw new InvalidCredentialsException("Incorrect Password");
        }  // 401 : UNAUTHORIZED; INVALID USER+PASS COMBO
        // 403 USER WAS LOGGED IN AND HAD A TOKEN/SESSION BUT...
        // IS TRYING TO HIT AN ENDPOINT THEY'RE "FORBIDDEN" FROM GOING TO
        return user;
    }

    public static void register(UserDAO userDAO, UserPrivDAO upDAO, UserInfo userInfo) throws SQLException, IllegalCharacterException, UsernameFormatException, InputWasNullException, ValueOutOfRangeException, UsernameNotAvailableException, JsonProcessingException {
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
        User user1 = new User(0,userInfo.getUsername(),
                userInfo.getFname(),
                userInfo.getLname(),
                userInfo.getAddress1(),
                userInfo.getAddress2(),
                userInfo.getCity(),
                userInfo.getState(),
                userInfo.getPostalcode());
        user1 = userDAO.createUser(user1);
        UserPriv up = new UserPriv(user1.getUser_id());
        up.encryptAndSetPassword(password);
        System.out.println("Making upDAO req:"+up);
        upDAO.createUserInfo(up);
        System.out.println("done");

    }
}
