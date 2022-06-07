package dev.josiah.services;

import dev.josiah.daos.UserDAO;
import dev.josiah.entities.User;

import java.util.List;

import static java.lang.Math.toIntExact;

public class ServiceGetAllUsers {
    public static List<User> ServiceAllUsersRequest(UserDAO userDAO) {
        List<User> users = userDAO.getAllUsers();
        if (users.size() == 0) {
            return null;
        }
        return users;
    }
}
