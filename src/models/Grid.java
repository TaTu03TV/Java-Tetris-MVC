package models;

import java.awt.DisplayMode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Random;

public class Grid extends Observable implements ActionListener {

    private int[][] DisplayGrid;
    private int[][] PieceGrid;
    private int[][] CurrentGrid;
    private Piece currentPiece;
    private int score;

    public Grid() {
        System.out.println("Grid");

        // initialisation des grilles

        DisplayGrid = new int[10][20];
        PieceGrid = new int[4][4];
        CurrentGrid = new int[10][20];

        score = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                DisplayGrid[i][j] = 0;
                CurrentGrid[i][j] = 0;
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                PieceGrid[i][j] = 0;
            }
        }



        //for debug we print the grid
        printGrid(PieceGrid);

        // initialisation de la piece

        createNewPiece();

        // initialisation de la grille courante

        CurrentGrid[9][19] = 1;
        CurrentGrid[8][19] = 1;
        CurrentGrid[7][19] = 1;
        CurrentGrid[6][19] = 1;

    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Grid actionPerformed");
        updateGrid();
        // printGrid(DisplayGrid);
        setChanged();
        notifyObservers();

    }

    public int[][] returnGrid() {
        return DisplayGrid;
    }

    public void updateGrid() {
        // descend toutes les lignes de 1
        System.out.println("Until here, it's working");
        clearDisplayGrid();
        System.out.println("clearDisplayGrid WORked");
        descendPiece();
        System.out.println("descendPiece WORked");

        System.out.println("At this point we have this grids:");
        printGrid(PieceGrid);
        printGrid(CurrentGrid);





        fusionGrid();
        System.out.println("fusionGrid WORked");
        if (suppriLigne()) {
            fusionGrid();
        }
        System.out.println("suppriLigne WORked");

    }

    public void createNewPiece() {
        currentPiece = Piece.placeRandomPiece(PieceGrid);
        currentPiece.getPos()[0] = 0; // x position
        currentPiece.getPos()[1] = 0; // y position
    }

    public boolean canDescend() {
        for (int i = 0; i < 4; i++) {
            for (int j = 3; j >= 0; j--) {
                if (PieceGrid[i][j] != 0) {
                    if (CurrentGrid[i + currentPiece.getPos()[0]][j + currentPiece.getPos()[1] + 1] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void descendPiece() {
        if (canDescend()) {
            currentPiece.setPos(currentPiece.getPos()[0], currentPiece.getPos()[1] + 1);
        } else {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (PieceGrid[i][j] != 0) {
                        CurrentGrid[i + currentPiece.getPos()[0]][j + currentPiece.getPos()[1]] = PieceGrid[i][j];
                        PieceGrid[i][j] = 0;
                    }
                }
            }
            createNewPiece();
        }
    }
    public void fusionGrid() {
        int xPos = currentPiece.getPos()[0];
        int yPos = currentPiece.getPos()[1];

        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i + xPos < DisplayGrid.length && j + yPos < DisplayGrid[0].length) {
                    if (PieceGrid[i][j] != 0) {
                        DisplayGrid[i + xPos][j + yPos] = PieceGrid[i][j];
                    }
                    if (CurrentGrid[i + xPos][j + yPos] != 0) {
                        DisplayGrid[i + xPos][j + yPos] = CurrentGrid[i + xPos][j + yPos];
                    }
                }
            }
        }
        System.out.println("FusionGrid");
    }

    public void clearDisplayGrid() {
        // clear la grille d'affichage
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                DisplayGrid[i][j] = 0;
            }
        }
    }

    public void printGrid(int[][] grid) {
        int w = grid.length;
        int h = grid[0].length;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                System.out.print(grid[j][i]);
            }
            System.out.println();
        }
    }

    public int[][] getPieceGrid() {
        return PieceGrid;
    }

    public boolean suppriLigne() {
        // supprime les lignes complètes (ligne et colonne inversé)

        boolean complete = false;
        for (int j = 19; j >= 0; j--) {
            complete = true;
            for (int i = 0; i < 10; i++) {
                if (CurrentGrid[i][j] == 0) {
                    complete = false;
                }
            }
            if (complete) {
                System.out.println("ligne complète");
                score += 1;
                for (int k = j; k > 0; k--) {
                    for (int i = 0; i < 10; i++) {
                        CurrentGrid[i][k] = CurrentGrid[i][k - 1];
                    }
                }
                j++;
            }
        }
        return complete;

    }

}
