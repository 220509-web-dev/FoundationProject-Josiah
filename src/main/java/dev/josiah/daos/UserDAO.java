package dev.josiah.daos;

import dev.josiah.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    User createUser(User user) throws SQLException;
    User getUserById(int id) throws SQLException;
    User getUserByUsername(String username) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    User updateUser(User user) throws SQLException;
    void deleteUserById(int id) throws SQLException;
}
