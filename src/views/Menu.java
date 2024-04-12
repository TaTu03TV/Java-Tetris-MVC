package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame {
    public Menu() {
        setLayout(new GridLayout(3, 1));
        JButton playButton = new JButton("Play");
        JButton settingsButton = new JButton("Settings");
        JButton quitButton = new JButton("Quit");

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainFrame mainFrame = new MainFrame();
                dispose();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open settings
            }
        });

        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(playButton);
        add(settingsButton);
        add(quitButton);
    }
}