package dev.josiah.services;

import dev.josiah.daos.UserDAO;
import dev.josiah.entities.User;
import lombok.SneakyThrows;


import static dev.josiah.services.AuthUser.checkUsername;

public class ServiceGetUserByUsername {
    @SneakyThrows
    public static User ServiceUsernameRequest(String username_feed, UserDAO userDAO) {
        checkUsername(username_feed);
        // If we get here, username has passed validation tests
        return userDAO.getUserByUsername(username_feed);
    }
}
