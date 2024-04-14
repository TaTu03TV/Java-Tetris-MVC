package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class to create the main menu of the game
 * 
 * This class is used to create the main menu of the game. It contains the play,
 * settings, and quit buttons. It also contains methods to start the game, open
 * the settings, and quit the game.
 * 
 * @version 1.0
 * @since 2024-04-14
 */

public class Menu extends JFrame {
    public Menu() {
        setSize(200, 200); // Make the menu smaller
        setLayout(new GridLayout(3, 1));

        // Create buttons with custom styles
        JButton playButton = createStyledButton("Play");
        JButton settingsButton = createStyledButton("Settings");
        JButton quitButton = createStyledButton("Quit");

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

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
        button.setBackground(Color.LIGHT_GRAY); // Set background color
        button.setForeground(Color.BLACK); // Set text color
        return button;
    }
}