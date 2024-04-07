package models;

import java.util.Observable;


public class Grid extends Observable {

    private int[][] DisplayGrid;
    private int[][] PieceGrid;
    private int[][] CurrentGrid;
    private Piece[] PieceList; // Initialize PieceList array
    private Piece ghostPiece;
    private int ghostColor = 8; // or any other distinct color
    private int score;
    private int descendingSpeed;

    public Grid() {
        System.out.println("Grid");
        PieceList = new Piece[2]; // or any desired size
        //we add 2 pieces to the list
        


        // initialisation des grilles

        DisplayGrid = new int[10][20];
        PieceGrid = new int[4][4];
        CurrentGrid = new int[10][20];

        descendingSpeed = 0;

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

        // for debug we print the grid
        printGrid(PieceGrid);

        // initialisation de la piece

        PieceList[0] = Piece.placeRandomPiece(PieceGrid);
        PieceList[1] = Piece.placeRandomPiece(PieceGrid);
        createNewPiece();


    }

    public void reset(){
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

        createNewPiece();
    }

    private class GridRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(75);
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            
                System.out.println("Grid actionPerformed");
                updateGrid();
                // printGrid(DisplayGrid);
                setChanged();
                notifyObservers();
            }
        }
    }

    public void start() {
        new Thread(new GridRunnable()).start();
    }

    public void calculateGhostPiece() {
        ghostPiece = new Piece(PieceList[0]);
        while (canDescend(ghostPiece)) {
            ghostPiece.setPos(ghostPiece.getPos()[0], ghostPiece.getPos()[1] + 1);
        }
    }

    public boolean canDescend(Piece piece) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (PieceGrid[i][j] != 0) {
                    int newY = piece.getPos()[1] + j + 1;
                    if (newY >= 20 || CurrentGrid[piece.getPos()[0] + i][newY] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int[][] returnGrid() {
        return DisplayGrid;
    }

    public int returnScore() {
        return score;
    }

    public void updateGrid() {
        // descend toutes les lignes de 1
        clearDisplayGrid();
        descendPiece();
        printGrid(PieceGrid);

        fusionGrid();
        calculateGhostPiece();
        if (suppriLigne()) {
            fusionGrid();
        }

    }

    public void createNewPiece() {
        



        PieceList[0] = PieceList[1];

        //TODO:
        // Create a new random piece and put it in the second posit
        PieceList[1] = Piece.placeRandomPiece(PieceGrid);



        PieceList[0].setPos(3, 0);
        ghostPiece = new Piece(PieceList[0]);

        // Check if game over
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (PieceGrid[i][j] != 0) {
                    if (CurrentGrid[i + PieceList[0].getPos()[0]][j + PieceList[0].getPos()[1]] != 0) {
                        setChanged();
                        notifyObservers("Game Over");
                        return;
                    }
                }
            }
        }
    }
    public boolean canDescend() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (PieceGrid[i][j] != 0) {
                    int newY = PieceList[0].getPos()[1] + j + 1;
                    if (newY >= 20 || CurrentGrid[PieceList[0].getPos()[0] + i][newY] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void descendPiece() {
        if(descendingSpeed != 5){
            descendingSpeed++;
        }else{
            if (canDescend()) {
                PieceList[0].setPos(PieceList[0].getPos()[0], PieceList[0].getPos()[1] + 1);
            } else {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (PieceGrid[i][j] != 0) {
                            CurrentGrid[i + PieceList[0].getPos()[0]][j + PieceList[0].getPos()[1]] = PieceGrid[i][j];
                            PieceGrid[i][j] = 0;
                        }
                    }
                }
                createNewPiece();
            }
            descendingSpeed = 0;
        }
    }

    public void fusionGrid() {
        int xPos = PieceList[0].getPos()[0];
        int yPos = PieceList[0].getPos()[1];

        // Draw the ghostPiece with a different color
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i + ghostPiece.getPos()[0] < DisplayGrid.length
                        && j + ghostPiece.getPos()[1] < DisplayGrid[0].length) {
                    if (PieceGrid[i][j] != 0) {
                        DisplayGrid[i + ghostPiece.getPos()[0]][j + ghostPiece.getPos()[1]] = ghostColor;
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i + xPos < DisplayGrid.length && j + yPos < DisplayGrid[0].length) {
                    if (PieceGrid[i][j] != 0) {
                        DisplayGrid[i + xPos][j + yPos] = PieceGrid[i][j];
                    }
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (CurrentGrid[i][j] != 0) {
                    DisplayGrid[i][j] = CurrentGrid[i][j];
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

    public void movePieceLeft() {
        boolean canMove = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (PieceGrid[i][j] != 0) {
                    int newX = PieceList[0].getPos()[0] + i - 1;
                    if (newX < 0 || CurrentGrid[newX][PieceList[0].getPos()[1] + j] != 0) {
                        canMove = false;
                        break;
                    }
                }
            }
            if (!canMove) {
                break;
            }
        }
        if (canMove) {
            PieceList[0].setPos(PieceList[0].getPos()[0] - 1, PieceList[0].getPos()[1]);
        }
    }

    public void movePieceRight() {
        boolean canMove = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (PieceGrid[i][j] != 0) {
                    int newX = PieceList[0].getPos()[0] + i + 1;
                    if (newX >= 10 || CurrentGrid[newX][PieceList[0].getPos()[1] + j] != 0) {
                        canMove = false;
                        break;
                    }
                }
            }
            if (!canMove) {
                break;
            }
        }
        if (canMove) {
            PieceList[0].setPos(PieceList[0].getPos()[0] + 1, PieceList[0].getPos()[1]);
        }
    }

    public void rotatePiece() {
        int[][] newPieceGrid = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newPieceGrid[i][j] = PieceGrid[3 - j][i];
            }
        }
        PieceGrid = newPieceGrid;
    }

    public boolean canRotate() {
        int[][] newPieceGrid = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newPieceGrid[i][j] = PieceGrid[3 - j][i];
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (newPieceGrid[i][j] != 0) {
                    int newX = PieceList[0].getPos()[0] + i;
                    int newY = PieceList[0].getPos()[1] + j;
                    if (newX < 0 || newX >= 10 || newY >= 20 || CurrentGrid[newX][newY] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
