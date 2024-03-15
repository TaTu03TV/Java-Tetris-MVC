package views;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame{

    private MainBoard board;

    public MainFrame() {
        board = new MainBoard();
        setLayout(new BorderLayout());
        add(board, BorderLayout.CENTER);
        board.start();
        setPreferredSize(new Dimension(590, 960));
        setTitle("Miguel's Tetris");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setResizable(false);
    }
    
}
