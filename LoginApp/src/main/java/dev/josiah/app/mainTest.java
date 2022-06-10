package dev.josiah.app;



import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserDaoPostgres;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.daos.UserPrivDaoPostgres;
import dev.josiah.dtos.UserInfo;
import dev.josiah.entities.User;
import dev.josiah.entities.UserPriv;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import static dev.josiah.services.Encrypt.encrypt;

public class mainTest {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        UserInfo userInfo = new UserInfo("j@Revature.net","pass1234","Josiah","Sparks","One Street","","SA","TX","");
        User user = mapper.readValue(mapper.writeValueAsString(userInfo), User.class);
        System.out.println(userInfo);
        System.out.println(user);
    }

    private static void testPassword(int i) throws SQLException {
        String password = "12345678";
        String enc = encrypt(password);
        UserPrivDAO upDAO = new UserPrivDaoPostgres();
        UserPriv up = upDAO.getUserInfoById(i);
        System.out.println(up.getPassword());
        System.out.println(enc);
    }

    private static void createUser() throws SQLException {
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
