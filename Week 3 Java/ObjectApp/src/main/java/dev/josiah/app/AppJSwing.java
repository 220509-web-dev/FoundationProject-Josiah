package dev.josiah.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AppJSwing {
    public static void main(String[] args)
    {
        //new ActionEventDemo();
        JPanel panel = new JPanel();
        String[] buttonTxt = {"Register","Login","Exit"};
        panel.setLayout(new GridLayout(buttonTxt.length,1));
        /*
        JButton[] buttons = new JButton[buttonTxt.length];
        for (int i = 0; i < buttons.length; i++)
        {
            buttons[i] = new JButton(buttonTxt[i]);
            panel.add(buttons[i]);
        }
        */
        JButton regButton = new JButton( new AbstractAction(buttonTxt[0]) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                // register
            }
        });
        JButton loginButton = new JButton( new AbstractAction(buttonTxt[1]) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                // login
            }
        });
        JButton exitButton = new JButton( new AbstractAction(buttonTxt[2]) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                System.exit(0);
            }
        });
        JButton[] buttons = {regButton, loginButton, exitButton};
        for (int i = 0; i < buttons.length; i++) panel.add(buttons[i]);
        JOptionPane.showOptionDialog(null, panel, "Welcome!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
    }

}