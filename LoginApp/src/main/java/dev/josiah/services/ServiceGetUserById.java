package dev.josiah.services;

import dev.josiah.complaintDepartment.AuthExceptions;
import dev.josiah.daos.UserDAO;
import dev.josiah.entities.User;

import java.sql.SQLException;

import static dev.josiah.services.validation.ValidateID.validateID;
import static java.lang.Math.toIntExact;

public class ServiceGetUserById {
    public static User ServiceIdRequest(String id_feed, UserDAO userDAO) throws SQLException {
        long id = validateID(id_feed);
        int id_int = toIntExact(id); // can be removed later to accommodate >2.1bil DB records
        User user;
        try {
            user = userDAO.getUserById(id_int);
        } catch (SQLException e) {
            throw new SQLException(e);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
        if (user == null) {
            throw new AuthExceptions.UserNotFoundException("User with ID "+id_int+" not found!");
        }
        return user;
    }
}
