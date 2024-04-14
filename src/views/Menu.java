package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

    private int soundVolume = 0;

    public Menu() {
        setSize(200, 200); // Make the menu smaller
        setLayout(new GridLayout(3, 1));

        // Create buttons with custom styles
        JButton playButton = createStyledButton("Play");
        JButton settingsButton = createStyledButton("Settings");
        JButton quitButton = createStyledButton("Quit");

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainFrame mainFrame = new MainFrame(soundVolume);
                dispose();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new settings frame
                JFrame settingsFrame = new JFrame("Settings");
                settingsFrame.setSize(300, 200);
                settingsFrame.setLayout(new GridLayout(3, 1));
        
                // Create a slider to control the volume
                JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, -50, 50, 0);
                volumeSlider.setMajorTickSpacing(10);
                volumeSlider.setPaintTicks(true);
                volumeSlider.setPaintLabels(true);
        
                // Add an action listener to the slider
                volumeSlider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        JSlider source = (JSlider)e.getSource();
                        if (!source.getValueIsAdjusting()) {
                            // Set the volume of the sound player
                            soundVolume = source.getValue();
                        }
                    }
                });
        
                // Create an OK button
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Close the settings frame
                        settingsFrame.dispose();
                    }
                });
        
                // Add the slider and the OK button to the settings frame
                settingsFrame.add(new JLabel("Volume:"));
                settingsFrame.add(volumeSlider);
                settingsFrame.add(okButton);
        
                // Show the settings frame
                settingsFrame.setVisible(true);
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