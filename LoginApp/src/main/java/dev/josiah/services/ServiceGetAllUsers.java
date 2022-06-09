package dev.josiah.services;

import dev.josiah.complaintDepartment.AuthExceptions;
import dev.josiah.daos.UserDAO;
import dev.josiah.entities.User;


import java.sql.SQLException;
import java.util.List;

public class ServiceGetAllUsers {

    public static List<User> ServiceAllUsersRequest(UserDAO userDAO) throws SQLException {
        List<User> users = userDAO.getAllUsers();
        if (users.size() == 0) {
            throw new AuthExceptions.UserNotFoundException("Database found no users");
        }
        return users;
    }
}
