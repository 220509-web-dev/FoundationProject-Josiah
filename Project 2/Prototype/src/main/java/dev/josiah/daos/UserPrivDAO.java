package dev.josiah.daos;

import dev.josiah.entities.UserPriv;

import java.sql.SQLException;
import java.util.List;

public interface UserPrivDAO {
    void createUserInfo(UserPriv userp) throws SQLException;
    UserPriv getUserInfoById(int id) throws SQLException;
    List<UserPriv> getAllUserInfo() throws SQLException;
    UserPriv updateUserInfo(UserPriv userp) throws SQLException;
    void deleteUserInfoById(int id) throws SQLException;
}
