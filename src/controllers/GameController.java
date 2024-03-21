package controllers;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import models.Grid;
import models.PiecesActions;

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
                if (e.getKeyChar() == 'q') {
                    grid.movePieceLeft();
                }
                if (e.getKeyChar() == 'd') {
                    grid.movePieceRight();
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    while (grid.canDescend()) {
                        grid.descendPiece();
                    }
                }
                if(e.getKeyChar() == 's'){
                    if(grid.canDescend()){
                        grid.descendPiece();
                    }
                }
                if(e.getKeyChar() == 'z'){
                    if(grid.canRotate()){
                        grid.rotatePiece();
                    }
                }
            } 
        });
        board.setFocusable(true);
    }

    public void start() {
        System.out.println("GameController start");
    }

}