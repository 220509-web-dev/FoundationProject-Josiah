package dev.josiah.daos;

import dev.josiah.entities.UserPriv;

import java.util.List;

public interface UserPrivDAO {
    void createUserInfo(UserPriv userp);
    UserPriv getUserInfoById(int id);
    List<UserPriv> getAllUserInfo();
    UserPriv updateUserInfo(UserPriv userp);
    void deleteUserInfoById(int id);
}
