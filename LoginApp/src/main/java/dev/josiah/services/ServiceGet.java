package dev.josiah.services;

import dev.josiah.complaintDepartment.Exceptions.*;
import dev.josiah.daos.UserDAO;
import dev.josiah.entities.User;

import java.sql.SQLException;
import java.util.List;

import static dev.josiah.services.validation.ValidateID.validateID;
import static dev.josiah.services.validation.ValidateUsername.validateUsername;
import static java.lang.Math.toIntExact;

public class ServiceGet {

    public static User ServiceIdRequest(String id_feed, UserDAO userDAO) throws InputWasNullException, ValueOutOfRangeException, InputNotAnIntegerException, SQLException, UserNotFoundException {
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
            throw new UserNotFoundException("User with ID "+id_int+" not found!");
        }
        return user;
    }

    public static User ServiceUsernameRequest(String username_feed, UserDAO userDAO) throws IllegalCharacterException, UsernameFormatException, InputWasNullException, ValueOutOfRangeException, SQLException, UserNotFoundException {
        validateUsername(username_feed);
        User user = userDAO.getUserByUsername(username_feed);
        if (user == null) {
            throw new UserNotFoundException(username_feed + " is not a username in the database");
        }
        return user;
    }

    public static List<User> ServiceAllUsersRequest(UserDAO userDAO) throws SQLException, UserNotFoundException {
        List<User> users = userDAO.getAllUsers();
        if (users.size() == 0) {
            throw new UserNotFoundException("Database found no users");
        }
        return users;
    }
}
