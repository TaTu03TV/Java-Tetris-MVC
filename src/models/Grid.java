package models;

public class Grid {

    private int[][] grid;
    
    public Grid() {
        System.out.println("Grid");

        grid = new int[10][20];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                grid[i][j] = 0;
            }
        }
    }

    public int[][] returnGrid() {
        return grid;
    }

}