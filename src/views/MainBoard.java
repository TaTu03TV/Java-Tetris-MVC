package views;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;


import controllers.GameController;
import models.Grid;


public class MainBoard extends JPanel{

    private static GameController gameController;
    private static Grid currentGrid;
    
    public MainBoard() {
        currentGrid = new Grid();
        gameController = new GameController(currentGrid);
        this.start();
    }

    public void start() {
        gameController.start();
    }

    public void drawGrid() {
        int [][] grid = currentGrid.returnGrid();

        // clear le pannel
        Graphics g = this.getGraphics();
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        
        // affiche des carré de couleur rouge où grid contient 1
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    g.setColor(Color.RED);
                    g.fillRect(20 + j * 40, 130 + i * 40, 40, 40); // Attention, les coordonnées sont inversées
                }
            }
        }
    }

}
