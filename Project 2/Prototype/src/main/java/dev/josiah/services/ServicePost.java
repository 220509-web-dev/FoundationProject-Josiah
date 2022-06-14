package dev.josiah.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.josiah.complaintDepartment.Exceptions.*;
import dev.josiah.daos.AllDAO;
import dev.josiah.dtos.UserInfo;
import dev.josiah.dtos.UserPass;
import dev.josiah.entities.User;
import dev.josiah.entities.UserPriv;

import java.sql.SQLException;

import static dev.josiah.services.Encrypt.encrypt;
import static dev.josiah.services.validation.ValidatePassword.validatePassword;
import static dev.josiah.services.validation.ValidateUsername.validateUsername;

public class ServicePost {
    public static User login(AllDAO allDAO, UserPass userPass) throws IllegalCharacterException, UsernameFormatException, InputWasNullException, ValueOutOfRangeException, SQLException, UserNotFoundException, InvalidCredentialsException {
        String username = userPass.getUsername();
        String password = userPass.getPassword();
        validateUsername(username);
        validatePassword(password);
        User user;
        UserPriv up;
        System.out.println("finding user:"+username);
        user = allDAO.getUserByUsername(username);
        if (user == null) {
            System.out.println("Looks like that was null!");
            throw new UserNotFoundException();
        }
        System.out.println("Finding info for:"+user.getId());
        up = allDAO.getUserInfoById(user.getId());
        if (up == null) {
            System.out.println("Looks like that was null");
            throw new UserNotFoundException();
        }
        if (!encrypt(password).equals(up.getPassword())) {
            throw new InvalidCredentialsException("Incorrect Password");
        }  // 401 : UNAUTHORIZED; INVALID USER+PASS COMBO

        return user;
    }

    public static void register(AllDAO allDAO, UserInfo userInfo) throws SQLException, IllegalCharacterException, UsernameFormatException, InputWasNullException, ValueOutOfRangeException, UsernameNotAvailableException, JsonProcessingException {
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        validateUsername(username);
        validatePassword(password);

        User user = allDAO.getUserByUsername(username);
        System.out.println("User is "+((user==null)?"":"not ")+"null");
        if (user != null) {
            System.out.println("Throwing UsernameNotAvailableException");
            throw new UsernameNotAvailableException();
            // USER NOT AVAILABLE : 409
            // 409 : UNIQUENESS CONSTRAINT VIOLATION
            // 409 MEANS "CONFLICT"
        }
        System.out.println("Line 63 in ServicePost");
        User user1 = new User(0,userInfo.getUsername(),
                userInfo.getFname(),
                userInfo.getLname()
        );
        user1 = allDAO.createUser(user1);
        UserPriv up = new UserPriv(user1.getId());
        up.encryptAndSetPassword(password);
        System.out.println("Making upDAO req:"+up);
        allDAO.createUserInfo(up);
        System.out.println("done");

    }
}
