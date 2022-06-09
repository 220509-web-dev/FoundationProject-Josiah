package dev.josiah.services;

import dev.josiah.daos.UserDAO;
import dev.josiah.entities.User;


import java.sql.SQLException;

import static dev.josiah.services.validation.ValidateUsername.validateUsername;

public class ServiceGetUserByUsername {
    public static User ServiceUsernameRequest(String username_feed, UserDAO userDAO) throws SQLException {
        validateUsername(username_feed);
        // If we get here, username has passed validation tests
        return userDAO.getUserByUsername(username_feed);
    }
}
