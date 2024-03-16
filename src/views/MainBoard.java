package views;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;


import controllers.GameController;
import models.Grid;


public class MainBoard extends JPanel implements Observer{
 

    private static GameController gameController;
    private static Grid currentGrid;
    
    private Image backgroundImage;
    
    public MainBoard() {

        try {
        backgroundImage = ImageIO.read(new File("assets/Mockup/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentGrid = new Grid();
        currentGrid.addObserver(this);
        gameController = new GameController(currentGrid, this);
        this.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, -10, 0, this);
    }

    public void start() {
        gameController.start();
    }

    public void update(Observable o, Object arg) {
        drawGrid(currentGrid.returnGrid());
        System.err.println("MainBoard update");
    }

    public void drawGrid(int[][] grid) {
        // clear le pannel
        Graphics g = this.getGraphics();
        
        // affiche des carré de couleur rouge où grid contient 1
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    g.setColor(TetrisColor.getColorByValue(grid[i][j]).getColor());
                    g.fillRect(10 + i * 40, 140 + j * 40, 40, 40); // décalé de 10px à gauche et 30px vers le haut

                }
                else {
                    g.setColor(TetrisColor.getColorByValue(grid[i][j]).getColor());
                    g.fillRect(10 + i * 40, 140 + j * 40, 40, 40); // décalé de 10px à gauche et 30px vers le haut
                }
            }
        }
    }

}
