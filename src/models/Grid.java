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
    
    public Grid() {
        System.out.println("Grid");

        // initialisation des grilles

        DisplayGrid = new int[10][20];
        PieceGrid = new int[10][20];
        CurrentGrid = new int[10][20];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                DisplayGrid[i][j] = 0;
                PieceGrid[i][j] = 0;
                CurrentGrid[i][j] = 0;
            }
        }

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
        printGrid(DisplayGrid);
        setChanged();
        notifyObservers();

    }

    public int[][] returnGrid() {
        return DisplayGrid;
    }

    public void updateGrid() {
        // descend toutes les lignes de 1
        clearDisplayGrid();
        descendPiece();
        fusionGrid();

    }

    public void createNewPiece() {

        // Créez une nouvelle pièce
        Piece.placeRandomPiece(PieceGrid);
    }

    
    public boolean canDescend() {
        for (int i = 0; i < 10; i++) {
            for (int j = 19; j >= 0; j--) {
                if (PieceGrid[i][j] != 0) {
                    if (j == 19 || CurrentGrid[i][j + 1] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public void descendPiece() {
        if (canDescend()) {
            // descend la piece
            for (int j = 19; j > 0; j--) {
                for (int i = 0; i < 10; i++) {
                    PieceGrid[i][j] = PieceGrid[i][j - 1];
                    PieceGrid[i][j - 1] = 0;
                }
            }
        } else {
            // ajoute la piece à CurrentGrid et crée une nouvelle pièce
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 20; j++) {
                    if (PieceGrid[i][j] != 0) {
                        CurrentGrid[i][j] = PieceGrid[i][j];
                        PieceGrid[i][j] = 0;
                    }
                }
            }
            // crée une nouvelle pièce
            
            createNewPiece();
        }
    }
    

    public void fusionGrid(){
        // fusionne la piece avec le reste de la grille
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (PieceGrid[i][j] != 0) {
                    DisplayGrid[i][j] = PieceGrid[i][j];
                }
                if(CurrentGrid[i][j] != 0){
                    DisplayGrid[i][j] = CurrentGrid[i][j];
                }
            }
        }
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
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    public int[][] getPieceGrid() {
        return PieceGrid;
    }

}
