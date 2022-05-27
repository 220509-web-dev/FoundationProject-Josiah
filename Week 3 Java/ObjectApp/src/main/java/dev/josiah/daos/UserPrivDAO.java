package dev.josiah.daos;

import dev.josiah.entities.User;

import java.util.List;

public interface UserPrivDAO {
    User createUserInfo(User user);
    User getUserInfoById(int id);
    List<User> getAllUserInfo();
    User updateUserInfo(User user);
    void deleteUserInfoById(int id);
}
