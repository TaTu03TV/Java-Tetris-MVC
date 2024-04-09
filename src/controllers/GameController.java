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
                }if(e.getKeyChar() == 'p'){
                    grid.pause();
                }
            } 
        });
        board.setFocusable(true);
    }

    public void start() {
        System.out.println("GameController start");
    }

}