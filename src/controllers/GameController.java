package controllers;

import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import models.Grid;

/**
 * Class GameController
 * 
 * This class is used to control the game. It contains methods to move the piece horizontally, descend the piece, rotate the piece, pause the game, and hold the piece.
 * 
 * @version 1.0
 * @since 2024-04-14
 * @see Grid
 */
public class GameController {
    private Grid grid;

    /**
     * Constructor for the GameController class. Overrides the key listener to control the game.
     * 
     * @param Grid Grid of the game
     * @param board Board of the game
     */
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
                //if we press echap, we close the game
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.exit(0);
                }
            } 
        });
        board.setFocusable(true);
    }

}