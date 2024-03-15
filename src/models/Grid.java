package models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class Grid extends Observable implements ActionListener {

    private int[][] grid;
    
    public Grid() {
        System.out.println("Grid");

        grid = new int[10][20];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                grid[i][j] = 0;
            }
        }

        grid[0][0] = 1;
        grid[0][1] = 1;
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Grid actionPerformed");
        updateGrid();
        printGrid();
        setChanged();
        notifyObservers();

    }

    public int[][] returnGrid() {
        return grid;
    }

    public void updateGrid() {
        // descend toutes les lignes de 1
        for (int i = 9; i > 0; i--) {
            for (int j = 0; j < 20; j++) {
                grid[i][j] = grid[i - 1][j];
                grid[i - 1][j] = 0;
            }
        }

    }

    public void printGrid() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

}
