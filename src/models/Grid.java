package models;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Scanner;

public class Grid extends Observable {

    private SoundPlayer soundPlayer = new SoundPlayer();
    private int[][] DisplayGrid;
    private int[][] PieceGrid;
    private int[][] CurrentGrid;
    private Piece[] PieceList;
    private Piece ghostPiece;
    private Piece holdPiece;
    private int ghostColor = 8;
    private int bestscore;
    private int score;
    private int descendingSpeed;
    private short level = 1;
    private boolean paused = false;

    public Grid() {
        initializeGrids();
        createNewPiece();
        /* 
        soundPlayer.playSound("assets/Sounds/Musics/theme.wav");
        soundPlayer.setLoop(true);
        bestscore = getBestScore();
        */
    }

    private void initializeGrids() {
        PieceList = new Piece[2];
        DisplayGrid = new int[10][20];
        PieceGrid = new int[4][4];
        CurrentGrid = new int[10][20];
        descendingSpeed = 0;
        score = 0;
        level = 1;
        clearGrids(DisplayGrid);
        clearGrids(CurrentGrid);
        clearGrids(PieceGrid);
    }

    private void clearGrids(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = 0;
            }
        }
    }

    private int getBestScore() {
        try {
            File file = new File("best-score.txt");
            if (file.exists()) {
                try (Scanner scanner = new Scanner(file)) {
                    if (scanner.hasNextInt()) {
                        return scanner.nextInt();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading best score: " + e.getMessage());
        }
        return 0;
    }

    public void reset() {
        score = 0;
        initializeGrids();
        PieceList[0] = null;
        PieceList[1] = null;
        createNewPiece();
        /* 
        soundPlayer.playSound("assets/Sounds/Musics/theme.wav");
        soundPlayer.setLoop(true);
        */
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
                if (!paused) {
                    updateGrid();
                }
                setChanged();
                notifyObservers();
            }
        }
    }

    public void pause() {
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

    public int returnBestScore() {
        return bestscore;
    }

    public void updateGrid() {
        clearDisplayGrid();
        descendPiece();
        fusionGrid();
        calculateGhostPiece();
        if (suppriLigne()) {
            fusionGrid();
        }
    }

    public void createNewPiece() {
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

    public void holdPiece() {
        if (holdPiece == null) {
            holdPiece = new Piece(PieceList[0]);
            holdPiece.setPos(3, 0);
            erasePieceGrid(PieceList[0]);
            createNewPiece();
        } else {
            Piece temp = new Piece(PieceList[0]);
            erasePieceGrid(PieceList[0]);
            PieceList[0] = new Piece(holdPiece);
            addToPieceGrid(PieceList[0]);
            holdPiece = temp;
            holdPiece.setPos(3, 0);
        }
    }

    public void erasePieceGrid(Piece piece) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                PieceGrid[i][j] = 0;
            }
        }
    }

    private void gameover() {
        System.out.println("Game Over");
        /*
        soundPlayer.stop();
        */
        File file = new File("best-score.txt");
        if (score > bestscore) {
            bestscore = score;
            try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
                out.println(bestscore);
            } catch (IOException e) {
                System.out.println("Error saving best score: " + e.getMessage());
            }
        }
        try (PrintWriter out = new PrintWriter(new FileWriter("histo-score.txt", true))) {
            out.println(score);
        } catch (IOException e) {
            System.out.println("Error saving score: " + e.getMessage());
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
        int numbOfFrames = Math.max(1, 7 - level / 2 + 2);
        if (descendingSpeed != numbOfFrames) {
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
    }

    public void clearDisplayGrid() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                DisplayGrid[i][j] = 0;
            }
        }
    }

    public int[][] getPieceGrid() {
        return PieceGrid;
    }

    public Piece returnNextPiece() {
        return PieceList[1];
    }

    public Piece returnHoldPiece() {
        return holdPiece;
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
                    /* 
                    soundPlayer.playSound("assets/Sounds/Effects/clear.wav");
                    */
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

        int multiplier = (level / 2) + 2;
        int[] scores = { 0, 40, 100, 300, 1200 }; 
        if (linesRemoved >= 1 && linesRemoved <= 4) {
            score += scores[linesRemoved] * multiplier;
        }
        calculateLevel();
        return complete;
    }

    public short getLevel() {
        return level;
    }

    private void calculateLevel() {
        // we convert the score to a short to avoid overflow
        short newLevel = (short) (Math.log(score + 1) / Math.log(3) / 2);
        System.out.println("Score: " + score + " Level: " + level + " New Level: " + newLevel);
        level = newLevel;
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
