package models;

import java.util.Observable;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

public class Grid extends Observable {

    private SoundPlayer soundPlayer = new SoundPlayer();

    private int[][] DisplayGrid;
    private int[][] PieceGrid;
    private int[][] CurrentGrid;
    private Piece[] PieceList; // Initialize PieceList array
    private Piece ghostPiece;
    private int ghostColor = 8; // or any other distinct color
    private int score;
    private int descendingSpeed;
    private boolean paused = false;

    public Grid() {
        System.out.println("Grid");
        PieceList = new Piece[2]; // or any desired size
        // we add 2 pieces to the list

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

        createNewPiece();
        
        soundPlayer.playSound("assets/Sounds/Musics/theme.wav");
        soundPlayer.setLoop(true);

    }

    public void reset() {
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

        soundPlayer.playSound("assets/Sounds/Musics/theme.wav");
        soundPlayer.setLoop(true);
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
                if(!paused){
                    updateGrid();
                }
                
                setChanged();
                notifyObservers();
            }
        }
    }

    public void pause(){
        paused = !paused;
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

        fusionGrid();
        calculateGhostPiece();
        if (suppriLigne()) {
            fusionGrid();
        }
        System.out.println("The next piece is: " + PieceList[1].getShape());
    }

    public void createNewPiece() {
        // we check if the array is empty
        if (PieceList[1] == null || PieceList[0] == null) {
            PieceList[0] = Piece.placeRandomPiece(PieceGrid, true);
            addToPieceGrid(PieceList[0]);
            PieceList[1] = Piece.placeRandomPiece(PieceGrid, false);
        } else {
            PieceList[0] = PieceList[1];
            addToPieceGrid(PieceList[0]);
            PieceList[1] = Piece.placeRandomPiece(PieceGrid, false);
        }

        PieceList[0].setPos(3, 0);
        ghostPiece = new Piece(PieceList[0]);

        // Check if game over
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (PieceGrid[i][j] != 0) {
                    if (CurrentGrid[i + PieceList[0].getPos()[0]][j + PieceList[0].getPos()[1]] != 0) {

                        gameover();

                        return;
                    }
                }
            }
        }
    }

    private void gameover() {
        // Game over
        System.out.println("Game Over");
    
        soundPlayer.stop();
    
        // Save best score
        try {
            File file = new File("best-score.txt");
            int bestScore = 0;
    
            // Read best score from file
            if (file.exists()) {
                try (Scanner scanner = new Scanner(file)) {
                    if (scanner.hasNextInt()) {
                        bestScore = scanner.nextInt();
                    }
                }
            }
    
            // Compare best score with current score
            if (score > bestScore) {
                bestScore = score;
    
                // Save new best score
                try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
                    out.println(bestScore);
                }
            }
            // Save score in Histo
            try (PrintWriter out = new PrintWriter(new FileWriter("histo-score.txt", true))) {
                out.println(score);
            }
        } catch (IOException e) {
            System.out.println("Error saving best score: " + e.getMessage());
        }
    
        setChanged();
        notifyObservers("Game Over");
    }


    private void addToPieceGrid(Piece piece) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (piece.getShape()[i][j] != 0) {
                    PieceGrid[i][j] = piece.getColor();
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
        if (descendingSpeed != 5) {
            descendingSpeed++;
        } else {
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

    public Piece returnNextPiece() {
        return PieceList[1];
    }

    public boolean suppriLigne() {
        boolean complete = false;
        int linesRemoved = 0;
        for (int j = 19; j >= 0; j--) {
            complete = true;
            for (int i = 0; i < 10; i++) {
                if (CurrentGrid[i][j] == 0) {
                    complete = false;
                }
            }
            if (complete) {
                linesRemoved += 1;
                try {
                    soundPlayer.playSound("assets/Sounds/Effects/clear.wav");
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (int k = j; k > 0; k--) {
                    for (int i = 0; i < 10; i++) {
                        CurrentGrid[i][k] = CurrentGrid[i][k - 1];
                    }
                }
                j++;
            }
        }

        switch (linesRemoved) {
            case 1:
                score += 40;
                break;
            case 2:
                score += 100;
                break;
            case 3:
                score += 300;
                break;
            case 4:
                score += 1200;
                break;
        }

        return complete;
    }

    public void movePieceHorizontally(boolean moveRight) {
        boolean canMove = true;
        int direction = moveRight ? 1 : -1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (PieceGrid[i][j] != 0) {
                    int newX = PieceList[0].getPos()[0] + i + direction;
                    if (newX < 0 || newX >= 10 || CurrentGrid[newX][PieceList[0].getPos()[1] + j] != 0) {
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
            PieceList[0].setPos(PieceList[0].getPos()[0] + direction, PieceList[0].getPos()[1]);
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
