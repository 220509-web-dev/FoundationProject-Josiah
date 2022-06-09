package dev.josiah.services;

import dev.josiah.complaintDepartment.AuthExceptions;
import dev.josiah.daos.UserDAO;
import dev.josiah.entities.User;


import java.sql.SQLException;

import static dev.josiah.services.validation.ValidateUsername.validateUsername;

public class ServiceGetUserByUsername {
    public static User ServiceUsernameRequest(String username_feed, UserDAO userDAO) throws SQLException {
        validateUsername(username_feed);
        // If we get here, username has passed validation tests
        User user = userDAO.getUserByUsername(username_feed);
        if (user == null) {
            throw new AuthExceptions.UserNotFoundException(username_feed + " is not a username in the database");
        }
        return user;
    }
}
