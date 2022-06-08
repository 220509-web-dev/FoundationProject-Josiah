package dev.josiah.services;

import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.entities.User;
import dev.josiah.entities.UserPriv;

import static dev.josiah.services.AuthUser.checkUsername;
import static dev.josiah.services.AuthPass.checkPassword;
import static dev.josiah.services.Encrypt.encrypt;


public class ServiceLogin {
    public int login(UserDAO userDAO, UserPrivDAO userPrivDAO, String username, String password) {
        checkUsername(username);
        checkPassword(password); // error means username or password didn't meet constraints
        User user;
        UserPriv up;
        try {
            user = userDAO.getUserByUsername(username);
            if (user == null) return 1; // jank-enum 1 means user not found
            up = userPrivDAO.getUserInfoById(user.getUser_id());
            String pw = encrypt(password);
            if (pw != up.getPassword()) return 2; // jank-enum 2 means incorrect password

        } catch (Throwable e) {
            return 3; // jank-enum 3 means database error
        }
        return 0; // jank-enum 0 means successful login
    }
}
