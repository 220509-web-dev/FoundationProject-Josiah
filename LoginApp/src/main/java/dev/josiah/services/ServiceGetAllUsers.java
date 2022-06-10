package dev.josiah.services;

import dev.josiah.complaintDepartment.Exceptions.UserNotFoundException;
import dev.josiah.daos.UserDAO;
import dev.josiah.entities.User;


import java.sql.SQLException;
import java.util.List;

public class ServiceGetAllUsers {

    public static List<User> ServiceAllUsersRequest(UserDAO userDAO) throws SQLException, UserNotFoundException {
        List<User> users = userDAO.getAllUsers();
        if (users.size() == 0) {
            throw new UserNotFoundException("Database found no users");
        }
        return users;
    }
}
