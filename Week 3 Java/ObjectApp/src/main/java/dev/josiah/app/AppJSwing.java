package dev.josiah.app;

import javax.swing.*;
import java.awt.*;

public class AppJSwing {
    public static void main(String[] args)
    {
        //new ActionEventDemo();
        JPanel panel = new JPanel();
        String[] buttonTxt = {"Register","Login","Exit"};
        panel.setLayout(new GridLayout(buttonTxt.length,1));
        JButton[] buttons = new JButton[buttonTxt.length];
        for (int i = 0; i < buttonTxt.length; i++)
        {
            buttons[i] = new JButton(buttonTxt[i]);
            panel.add(buttons[i]);
        }
        JOptionPane.showOptionDialog(null, panel, "Welcome!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
    }

}