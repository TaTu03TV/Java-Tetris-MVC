package views;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

import controllers.GameController;
import models.Grid;

import java.awt.event.ActionEvent;

public class MainBoard extends JPanel implements ActionListener {

    private static GameController gameController;
    private static Grid currentGrid;
    
    public MainBoard() {
        gameController = new GameController(this);
        currentGrid = new Grid();
        this.start();
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Action Performed");
        currentGrid.updateGrid();
        this.drawGrid();
    }

    public void start() {
        gameController.start();
    }

    public void drawGrid() {
        int [][] gird = currentGrid.returnGrid();
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.print(gird[i][j]);
            }
            System.out.println();
        }
    }

}
