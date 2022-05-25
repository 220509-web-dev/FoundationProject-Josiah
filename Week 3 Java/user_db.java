import javax.swing.*;
import java.awt.*;
import java.io.*;

/*
Need to add function to the cancel button
Add cancel button on the login screen
*/

public class user_db {
    static String blankuser = "Username cannot be blank!";
    static String blankpass = "Password cannot be blank!";
    static String userspace = "Username cannot contain spaces!";
    static String passspace = "Password cannot contain spaces!";
    static String usernotfound = "Username not found!";
    static String wrongpass = "Password is incorrect!";
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
                    error = "\nPlease make a VALID selection.";
                }
            }
            switch(inputNumber) {
                case 1:
                    JOptionPane.showMessageDialog(null, op1);
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, op2);
                    Login();
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

    public static Boolean validate(String arg) {
        // Checks if username is avaliable
        String[] fields;
        try (BufferedReader fileReader = new BufferedReader(new FileReader("resources/user.csv"))) {
            fileReader.readLine(); // skips the first line of the CSV file (the header)
            String line = fileReader.readLine();
            while (line != null) {
                System.out.println(line);
                line = fileReader.readLine();
                fields = line.split(",");
                if (fields[0].equals(arg)) { return false;}
            }
        } catch (FileNotFoundException e) {
            System.err.println("That file was not found!");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public static String validate(String[] args) {
        String[] fields;
        String[] errors = new String[5];
        int error_ind = 0;

        try (BufferedReader fileReader = new BufferedReader(new FileReader("resources/users.csv"))) {
            fileReader.readLine(); // skips the first line of the CSV file (the header)
            String line = fileReader.readLine();
            while (line != null) {
                System.out.println(line);
                line = fileReader.readLine();
                if (line != null && !line.trim().equals("")) {
                    fields = line.split(",");
                    if (fields[0].equals(args[0])) {
                        if (fields[1].equals(args[1])) { return ""; }
                        else { errors[error_ind] = wrongpass; error_ind++; }
                        break;
                    }
                }

            }
        } catch (FileNotFoundException e) {
            System.err.println("That file was not found!");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // If this is reached with error_ind==0, not matching user was found
        if (error_ind == 0) { errors[error_ind] = usernotfound; error_ind++; }

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
    public static String[] Login() {
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
                login_error = validate(info);
                if (login_error.equals("")) {login_error = ""; valid = true; }
                else {
                    JOptionPane.showMessageDialog(null, login_error);
                }
            } else {
                JOptionPane.showMessageDialog(null, to_nlsv(errors, error_count-1));
            }
        }
        return info;
    }
}

