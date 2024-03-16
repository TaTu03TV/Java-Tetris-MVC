package controllers;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import models.Grid;

public class GameController {
    private Timer timer;
    private Grid grid;

    public GameController(Grid Grid, JPanel board) {
        System.out.println("GameController");
        this.grid = Grid;
        timer = new Timer(500, grid);
        timer.start();

        board.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'd') {
                    System.out.println("Debug: 'd' key pressed");
                }
            }
        });
        board.setFocusable(true);
    }

    public void start() {
        System.out.println("GameController start");
    }

}