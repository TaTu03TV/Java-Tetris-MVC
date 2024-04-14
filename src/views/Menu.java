package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Class to create the main menu of the game
 * 
 * This class is used to create the main menu of the game. It contains the play, settings, and quit buttons. It also contains methods to start the game, open the settings, and quit the game.
 * 
 * @version 1.0
 * @since 2024-04-14
 */
public class Menu extends JFrame {

    /**
     * Constructor for the Menu class
     * 
     * @see Main
     */
    public Menu() {
        setSize(300, 300);
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