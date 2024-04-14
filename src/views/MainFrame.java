package views;

import java.awt.*;
import javax.swing.*;

/**
 * Class to create the main frame of the game
 * 
 * This class is used to create the main frame of the game. It contains the main board of the game. It also contains methods to set the layout, add the main board, set the preferred size, set the title, set the default close operation, pack the frame, set the visibility, and set the resizable property.
 * 
 * @version 1.0
 * @since 2024-04-14
 * @see MainBoard
 */
public class MainFrame extends JFrame{

    private MainBoard board;

    /**
     * Constructor for the MainFrame class
     * 
     * @see MainBoard
     */
    public MainFrame() {
        board = new MainBoard();
        setLayout(new BorderLayout());
        add(board, BorderLayout.CENTER);
        setPreferredSize(new Dimension(590, 990));
        setTitle("Miguel's Tetris");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setResizable(false);
    }
    
}
