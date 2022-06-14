package dev.josiah.app;

import dev.josiah.daos.AllDAO;
import dev.josiah.daos.AllDaoPostgres;
import dev.josiah.entities.User;
import dev.josiah.entities.UserPriv;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

public class App {
    static int minuserlen = 14; // must end in @revature.net
    static int minpasslen = 8;
    static String useremail = "Username must end in @revature.net";
    static String blankuser = "Username cannot be blank!";
    static String blankpass = "Password cannot be blank!";
    static String userspace = "Username cannot contain spaces!";
    static String passspace = "Password cannot contain spaces!";
    static String usernotfound = "Username not found!";
    static String wrongpass = "Password is incorrect!";
    static String usercomma = "Username cannot contain commas!";
    static String passcomma = "Password cannot contain commas!";
    static String shortuser = "Username must be at least " + minuserlen + " characters!";
    static String shortpass = "Password must be at least " + minpasslen + " characters!";

    JFrame frame=new JFrame();//creating object of JFrame class
    App(){
        prepareGUI();//calling prepareGUI() method
    }
    public void prepareGUI(){
        frame.setTitle("My Window");//Setting title of JFrame
        frame.getContentPane().setLayout(null);//Setting Layout
        frame.setVisible(true);//Setting visibility
        frame.setBounds(200,200,400,400);//Setting Location and Size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Setting default close operation
    }

    public static void main(String[] args) {
        String menuSelection = "";
        int inputNumber = 0;
        String menu = "Please select one of the following.";
        menu += "\n1) Register\n2) Login\n3) Exit";
        String welcome = "Welcome to the database!";
        String op1 = "Please enter your details";
        String op2 = "Please enter your login details";
        String op3 = "Exiting Program";
        String error = "";
        Boolean valid = false;
        error = "";
        JOptionPane.showMessageDialog(null, welcome);
        while(true) {
            valid = false;
            error = "";
            while (!valid || inputNumber <= 0 || inputNumber >= 4) {
                try {
                    menuSelection = JOptionPane.showInputDialog(menu+error);
                    inputNumber = Integer.parseInt(menuSelection);
                    if (inputNumber <= 0 || inputNumber >= 4) {
                        error = "\nPlease make a VALID selection.";
                    } else {valid = true; }
                } catch (NumberFormatException e) {
                    Complain(e);
                    error = "\nPlease make a VALID selection.";
                } catch (Throwable t) {
                    Complain(t);
                }
            }
            switch(inputNumber) {
                case 1:
                    JOptionPane.showMessageDialog(null, op1);
                    JOptionPane.showMessageDialog(null, "Your credentials have been registered, "+ Register());
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, op2);
                    JOptionPane.showMessageDialog(null, "Welcome, "+ Login());
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, op3);
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Unreachable code");
            }
        }
    }
    @SneakyThrows
    public static Boolean validate(String username) {
        AllDAO userDAO = new AllDaoPostgres();
        User u = userDAO.getUserByUsername(username);
        if (u == null) return true;  // username available!
        return false; // username not available.
    }
    @SneakyThrows
    public static String validate(String username, String password) {
        String[] fields;
        String[] errors = new String[5];
        int error_ind = 0;
        AllDAO userDAO = new AllDaoPostgres();
        User u = userDAO.getUserByUsername(username);

        if (u == null) {
            errors[error_ind] = usernotfound;
            error_ind++;
            return to_nlsv(errors, error_ind-1);
        }
        AllDAO userpDAO = new AllDaoPostgres();
        UserPriv up = userpDAO.getUserInfoById(u.getId());
        UserPriv test = new UserPriv();
        test.encryptAndSetPassword(password);
        if (up.getPassword().equals(test.getPassword())) return "";
        else {
            errors[error_ind] = wrongpass;
            error_ind++;
        }
        return to_nlsv(errors, error_ind-1); // Returns any error messages
    }
    public static String to_nlsv(String[] args, int max_ind) {
        // to new-line separated value (nlsv)
        if (max_ind < 0) { return ""; }
        String output = "";
        int ind = 0;
        while (max_ind > 0) {
            output += args[ind] + "\n";
            ind++;
            max_ind--;
        }
        output += args[ind];
        return output;
    }
    @SneakyThrows
    public static void Store(String first, String last, String username, String password) {
        User u = new User(1, username, first, last, "null","null");
        AllDAO userDAO = new AllDaoPostgres();
        u = userDAO.createUser(u);

        UserPriv up = new UserPriv(u.getId());
        up.encryptAndSetPassword(password);
        AllDAO userpDAO = new AllDaoPostgres();
        userpDAO.createUserInfo(up);
    }

    // https://stackoverflow.com/questions/18395615/joptionpane-with-username-and-password-input
    public static String Register() {
        String[] info = new String[2];

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
        label.add(new JLabel("First Name", SwingConstants.RIGHT));
        label.add(new JLabel("Last Name", SwingConstants.RIGHT));
        label.add(new JLabel("Username", SwingConstants.RIGHT));
        label.add(new JLabel("Password", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField fname = new JTextField();
        controls.add(fname);
        JTextField lname = new JTextField();
        controls.add(lname);
        JTextField username = new JTextField();
        controls.add(username);
        JPasswordField password = new JPasswordField();
        controls.add(password);
        panel.add(controls, BorderLayout.CENTER);
        Boolean valid = false;
        String login_error = "";
        Boolean login_correct = false;
        String[] errors = new String[10];
        int error_count = 0;
        String first = "null";
        String last = "null";
        while (!valid) {
            JOptionPane.showMessageDialog(null, panel, "register", JOptionPane.QUESTION_MESSAGE);
            first = fname.getText();
            last = lname.getText();
            info[0] = username.getText();
            info[1] = new String(password.getPassword());
            errors = new String[10];
            error_count = 0;
            if (info[0].trim().equals("")) { errors[error_count] = blankuser; error_count++; }
            if (!info[0].trim().equals(info[0])) { errors[error_count] = userspace; error_count++; }
            if (info[0].contains(",")) { errors[error_count] = usercomma; error_count++; }
            if (info[0].length() < minuserlen) { errors[error_count] = shortuser; error_count++; }
            if (!info[0].endsWith("@revature.net")) { errors[error_count] = useremail; error_count++; }
            if (info[1].trim().equals("")) { errors[error_count] = blankpass; error_count++; }
            if (!info[1].trim().equals(info[1])) { errors[error_count] = passspace; error_count++; }
            if (info[1].contains(",")) { errors[error_count] = passcomma; error_count++; }
            if (info[1].length() < minpasslen) { errors[error_count] = shortpass; error_count++; }
            if (error_count == 0) {
                // Call function that validates login details against CSV db
                // login_error = validate(info);
                if (validate(info[0])) {login_error = ""; valid = true; }
                else {
                    login_error = "Username already registered!";
                    JOptionPane.showMessageDialog(null, login_error);
                }
            } else {
                JOptionPane.showMessageDialog(null, to_nlsv(errors, error_count-1));
            }
        }
        Store(first, last, info[0], info[1]);
        return info[0];
    }

    public static String Login() {
        String[] info = new String[2];

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
        label.add(new JLabel("Username", SwingConstants.RIGHT));
        label.add(new JLabel("Password", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField username = new JTextField();
        controls.add(username);
        JPasswordField password = new JPasswordField();
        controls.add(password);
        panel.add(controls, BorderLayout.CENTER);
        Boolean valid = false;
        String login_error = "";
        Boolean login_correct = false;
        String[] errors = new String[10];
        int error_count = 0;
        while (!valid) {
            JOptionPane.showMessageDialog(null, panel, "login", JOptionPane.QUESTION_MESSAGE);
            info[0] = username.getText();
            info[1] = new String(password.getPassword());
            errors = new String[10];
            error_count = 0;
            if (info[0].trim().equals("")) { errors[error_count] = blankuser; error_count++; }
            if (!info[0].trim().equals(info[0])) { errors[error_count] = userspace; error_count++; }
            if (info[1].trim().equals("")) { errors[error_count] = blankpass; error_count++; }
            if (!info[1].trim().equals(info[1])) { errors[error_count] = passspace; error_count++; }
            if (error_count == 0) {
                // Call function that validates login details against CSV db
                login_error = validate(info[0], info[1]);
                if (login_error.equals("")) {login_error = ""; valid = true; }
                else {
                    JOptionPane.showMessageDialog(null, login_error);
                }
            } else {
                JOptionPane.showMessageDialog(null, to_nlsv(errors, error_count-1));
            }
        }
        return info[0];
    }
    @SneakyThrows
    private static void code_holder() {

        /*
        // test createUser
        AllDAO userDAO = new AllDaoPostgres();
        User u = new User(1, "tester123@revature.net","Tester","McTesterson",
                "100 A Street", "Apt 1","Cityville","TX","99999");
        System.out.println(u.toString());
        u = userDAO.createUser(u);
        User u1 = userDAO.getUserByUsername("tester123@revature.net");
        System.out.println(u1.toString());
        */

        /*
        // test getUserById
        AllDAO userDAO = new AllDaoPostgres();
        User u = userDAO.getUserById(22222);
        System.out.println(u.toString());
        */

        /*
        // test getAllUsers
        AllDAO userDAO = new AllDaoPostgres();
        List<User> us = userDAO.getAllUsers();
        System.out.println(us);
        */

        /*
        // test updateUser
        AllDAO userDAO = new AllDaoPostgres();
        User u = new User(54840, "tester123@revature.net","Tester","McTesterson",
                "100 A Street", "Apt 1","Cityville","TX","99999");
        u.setState("TN");
        u = userDAO.updateUser(u);
        User u1 = userDAO.getUserById(54840);
        System.out.println(u1.toString());
        */

        /*
        // test deleteUserById
        AllDAO userDAO = new AllDaoPostgres();
        userDAO.deleteUserById(54842);
        */

        /*
        UserPriv up = new UserPriv(1);
        up.setSocial_sn("00000001");
        up.encryptAndSetPassword("password");
        System.out.println(up.getPassword());
        */

        /**/
        String uname = "tester1234@revature.net";
        User u = new User(1,"Tester@revature.net","Tester","Testerson","","");
        System.out.println(u.toString());

        AllDAO userDAO = new AllDaoPostgres();
        u = userDAO.createUser(u);
        System.out.println(u.toString());

        UserPriv userp = new UserPriv(u.getId());
        userp.encryptAndSetPassword("password1");

        AllDaoPostgres userpDAO = new AllDaoPostgres();
        userpDAO.createUserInfo(userp);

        User test1 = userDAO.getUserByUsername(uname);
        UserPriv test2 = userpDAO.getUserInfoById(test1.getId());
        System.out.println("***************************************");
        System.out.println(test1.toString());
        System.out.println(test2.toString());

        userpDAO.deleteUserInfoById(test1.getId());
        userDAO.deleteUserById(test1.getId());
        test1 = userDAO.getUserByUsername(uname);
        if(test1 == null) System.out.println("test1 is null");
        else System.out.println(test1.toString());
    }
}
