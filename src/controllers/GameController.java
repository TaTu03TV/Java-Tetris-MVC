package controllers;

import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import models.Grid;

public class GameController {
    private Grid grid;

    public GameController(Grid Grid, JPanel board) {
        System.out.println("GameController");
        this.grid = Grid;

        board.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyChar() == 'q' || e.getKeyChar() == 'Q') {
                    grid.movePieceHorizontally(false);
                }
                if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
                    grid.movePieceHorizontally(true);

                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    while (grid.canDescend()) {
                        grid.descendPiece();
                    }
                }
                if(e.getKeyChar() == 's' || e.getKeyChar() == 'S'){
                    if(grid.canDescend()){
                        grid.descendPiece();
                    }
                }
                if(e.getKeyChar() == 'z' || e.getKeyChar() == 'Z'){
                    if(grid.canRotate()){
                        grid.rotatePiece();
                    }
                }if(e.getKeyChar() == 'p' || e.getKeyChar() == 'P'){
                    grid.pause();
                }
                if (e.getKeyChar() == 'h' || e.getKeyChar() == 'H') {
                    grid.holdPiece();
                }
            } 
        });
        board.setFocusable(true);
    }

    public void start() {
        System.out.println("GameController start");
    }

}