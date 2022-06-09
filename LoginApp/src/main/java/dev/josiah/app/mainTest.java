package dev.josiah.app;

import dev.josiah.complaintDepartment.AuthExceptions;
import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserDaoPostgres;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.daos.UserPrivDaoPostgres;
import dev.josiah.entities.User;
import dev.josiah.entities.UserPriv;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;
import static dev.josiah.services.AuthUser.checkUsername;
import static dev.josiah.services.Encrypt.encrypt;
import static java.lang.Math.toIntExact;

public class mainTest {

    public static void main(String[] args) throws IOException {
        //(new char[6]).replace("\0", "@");
        FileWriter writer = new FileWriter("text.txt", true);
    }

    @SneakyThrows
    private static void testPassword() {
        String password = "12345678";
        String enc = encrypt(password);
        UserPrivDAO upDAO = new UserPrivDaoPostgres();
        UserPriv up = upDAO.getUserInfoById(50025);
        System.out.println(up.getPassword());
        System.out.println(enc);
    }
    @SneakyThrows
    private static void createUser() {
        User user = new User();
        user.setUsername("Jsparks109@revature.net");
        user.setFname("Josiah");
        user.setLname("Sparks");
        user.setState("TX");
        user.setPostalcode("78250");
        user.setCity("San Antonio");
        user.setAddress1("One Street");
        user.setAddress2("");
        UserDAO userDAO = new UserDaoPostgres();
        user = userDAO.createUser(user);
        System.out.println(user);
        UserPrivDAO upDAO = new UserPrivDaoPostgres();
        UserPriv up = new UserPriv(user.getUser_id());
        up.encryptAndSetPassword("12345678");
        upDAO.createUserInfo(up);
    }
}
