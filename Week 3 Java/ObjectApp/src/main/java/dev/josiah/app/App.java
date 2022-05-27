package dev.josiah.app;

import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserDaoPostgres;
import dev.josiah.daos.UserPrivDaoPostgres;
import dev.josiah.entities.User;
import dev.josiah.entities.UserPriv;

import java.util.List;

public class App {
    public static void main(String[] args) {
        // Full Java Swing App

        /*
        // test createUser
        UserDAO userDAO = new UserDaoPostgres();
        User u = new User(1, "tester123@revature.net","Tester","McTesterson",
                "100 A Street", "Apt 1","Cityville","TX","99999");
        System.out.println(u.toString());
        u = userDAO.createUser(u);
        User u1 = userDAO.getUserByUsername("tester123@revature.net");
        System.out.println(u1.toString());
        */

        /*
        // test getUserById
        UserDAO userDAO = new UserDaoPostgres();
        User u = userDAO.getUserById(22222);
        System.out.println(u.toString());
        */

        /*
        // test getAllUsers
        UserDAO userDAO = new UserDaoPostgres();
        List<User> us = userDAO.getAllUsers();
        System.out.println(us);
        */

        /*
        // test updateUser
        UserDAO userDAO = new UserDaoPostgres();
        User u = new User(54840, "tester123@revature.net","Tester","McTesterson",
                "100 A Street", "Apt 1","Cityville","TX","99999");
        u.setState("TN");
        u = userDAO.updateUser(u);
        User u1 = userDAO.getUserById(54840);
        System.out.println(u1.toString());
        */

        /*
        // test deleteUserById
        UserDAO userDAO = new UserDaoPostgres();
        userDAO.deleteUserById(54842);
        */

        /*
        UserPriv up = new UserPriv(1);
        up.setSocial_sn("00000001");
        up.setPassword("password");
        System.out.println(up.getPassword());
        */

        /**/
        String uname = "tester1234@revature.net";
        User u = new User(1, uname,"Tester","McTesterson",
                "100 A Street", "Apt 1","Cityville","TX","99999");
        System.out.println(u.toString());

        UserDAO userDAO = new UserDaoPostgres();
        u = userDAO.createUser(u);
        System.out.println(u.toString());

        UserPriv userp = new UserPriv(u.getUser_id());
        userp.setSocial_sn("12345678");
        userp.setPassword("password1");

        UserPrivDaoPostgres userpDAO = new UserPrivDaoPostgres();
        userpDAO.createUserInfo(userp);

        User test1 = userDAO.getUserByUsername(uname);
        UserPriv test2 = userpDAO.getUserInfoById(test1.getUser_id());
        System.out.println("***************************************");
        System.out.println(test1.toString());
        System.out.println(test2.toString());

        userpDAO.deleteUserInfoById(test1.getUser_id());
        userDAO.deleteUserById(test1.getUser_id());
        test1 = userDAO.getUserByUsername(uname);
        if(test1 == null) System.out.println("test1 is null");
        else System.out.println(test1.toString());


    }
}
