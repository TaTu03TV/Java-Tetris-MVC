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

    public enum TetrisColor {
        I(Color.CYAN, 1),
        O(Color.YELLOW, 2),
        T(Color.MAGENTA, 3),
        S(Color.GREEN, 4),
        Z(Color.RED, 5),
        J(Color.BLUE, 6),
        L(Color.ORANGE, 7),
        EMPTY(new Color(47, 39, 255), 0); //47, 39, 41

        private Color color;
        private int value;

        TetrisColor(Color color, int value) {
            this.color = color;
            this.value = value;
        }

        public Color getColor() {
            return this.color;
        }

        public static TetrisColor getColorByValue(int value) {
            for (TetrisColor color : TetrisColor.values()) {
                if (color.value == value) {
                    return color;
                }
            }
            return EMPTY; // return EMPTY color if no match is found
        }
    }

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
        gameController = new GameController(currentGrid);
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

                    // ajoute des bordures au rectangle
                    g.setColor(g.getColor().darker().darker());
                    for (int k = 0; k < 4; k++) { // 4 is the thickness of the line
                        g.drawLine(10 + i * 40, 140 + j * 40 + k, 50 + i * 40, 140 + j * 40 + k); // Top border
                        g.drawLine(10 + i * 40, 180 + j * 40 - k, 50 + i * 40, 180 + j * 40 - k); // Bottom border
                        g.drawLine(10 + i * 40 + k, 140 + j * 40, 10 + i * 40 + k, 180 + j * 40); // Left border
                        g.drawLine(50 + i * 40 - k, 140 + j * 40, 50 + i * 40 - k, 180 + j * 40); // Right border
                    }

                }
                else {
                    g.setColor(TetrisColor.getColorByValue(grid[i][j]).getColor());
                    g.fillRect(10 + i * 40, 140 + j * 40, 40, 40); // décalé de 10px à gauche et 30px vers le haut
                }
            }
        }
    }

}
