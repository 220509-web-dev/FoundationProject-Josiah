import javax.swing.JOptionPane;

public class user_db {
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
}
