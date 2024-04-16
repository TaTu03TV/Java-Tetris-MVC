package models;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Scanner;

/**
 * 
 * Class to represent the grid of the game
 * 
 * This class is responsible for managing the grid of the game, the pieces, the
 * score, the level and the sounds
 * 
 * @version 1.0
 * @since 2024-04-14
 * @see Piece
 * @see SoundPlayer
 * 
 */
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

    /**
     * Constructor for the Grid class
     * 
     * @see Piece
     * @see SoundPlayer
     */
    public Grid(float volume) {
        initializeGrids();
        initializeSounds(volume);
        createNewPiece();
        bestscore = getBestScore();

    }

    /**
     * Method to initialize the sounds of the game
     */
    private void initializeSounds(float volume) {
        soundPlayer.addSoundFile("/assets/Sounds/Musics/theme.wav");
        soundPlayer.addSoundFile("/assets/Sounds/Effects/clear.wav");
        soundPlayer.addSoundFile("/assets/Sounds/Effects/gameover.wav");
        soundPlayer.addSoundFile("/assets/Sounds/Effects/success.wav");
        System.out.println("Volume: " + volume);
        soundPlayer.setVolumeAll(volume);
        soundPlayer.playSound(0);
        soundPlayer.loopSound(0);
    }

    /**
     * Method to initialize the grids of the game
     */
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

    /**
     * Method to clear the grids of the game
     * 
     * @param grid Grid to clear
     */
    private void clearGrids(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = 0;
            }
        }
    }

    /**
     * Method to get the best score from the file
     * 
     * @return Best score of the game
     */
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

    /**
     * Method to reset the game
     */
    public void reset() {
        score = 0;
        initializeGrids();
        PieceList[0] = null;
        PieceList[1] = null;
        createNewPiece();
        soundPlayer.playSound(0);
    }

    /**
     * Loop to update the grid
     * 
     * @return Sound player
     */
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

    /**
     * Method to pause the game
     */
    public void pause() {
        paused = !paused;
    }

    /**
     * Method to start the loop of the grid
     */
    public void start() {
        new Thread(new GridRunnable()).start();
    }

    /**
     * Method to calculate the ghost piece
     */
    public void calculateGhostPiece() {
        ghostPiece = new Piece(PieceList[0]);
        while (canDescend(ghostPiece)) {
            ghostPiece.setPos(ghostPiece.getPos()[0], ghostPiece.getPos()[1] + 1);
        }
    }

    /**
     * Method to check if a piece can fall
     * 
     * @param piece Piece to check
     * @return True if the piece can fall, false otherwise
     */
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

    /**
     * Method to descend a piece
     */
    public void descendPiece() {
        int numbOfFrames = Math.max(1, 7 - level + 2);
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

    /**
     * Method to return the grid
     * 
     * @return Grid of the game
     */
    public int[][] returnGrid() {
        return DisplayGrid;
    }

    /**
     * Method to return the score
     * 
     * @return Score of the game
     */
    public int returnScore() {
        return score;
    }

    /**
     * Method to return the best score
     * 
     * @return Best score of the game
     */
    public int returnBestScore() {
        return bestscore;
    }

    /**
     * Method to update the grid
     */
    public void updateGrid() {
        clearDisplayGrid();
        descendPiece();
        fusionGrid();
        calculateGhostPiece();
        if (suppriLigne()) {
            fusionGrid();
        }
    }

    /**
     * Method to create a new piece
     */
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

    /**
     * Method to hold a piece
     */
    public void holdPiece() {
        if (holdPiece == null) {
            erasePieceGrid();
            holdPiece = new Piece(PieceList[0]);
            holdPiece.setPos(3, 0);
            createNewPiece();
        } else {
            Piece temp = new Piece(PieceList[0]);
            erasePieceGrid();
            PieceList[0] = new Piece(holdPiece);
            holdPiece = temp;
            holdPiece.setPos(3, 0);
            addToPieceGrid(PieceList[0]);
        }
    }

    /**
     * Method to erase the piece from the piece grid
     * 
     */
    public void erasePieceGrid() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                PieceGrid[i][j] = 0;
            }
        }
    }

    /**
     * Method to end the game
     */
    private void gameover() {
        System.out.println("Game Over");
        soundPlayer.stopSound(0);
        try {
            soundPlayer.playSound(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    /**
     * Method to add a piece to the piece grid
     * 
     * @param piece Piece to add
     */
    private void addToPieceGrid(Piece piece) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (piece.getShape()[i][j] != 0) {
                    PieceGrid[i][j] = piece.getColor();
                }
            }
        }
    }

    /**
     * Method to check if a piece can descend
     * 
     * @return True if the piece can descend, false otherwise
     */
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

    /**
     * Method to fusion the grid piece and current grid to display grid
     */
    public void fusionGrid() {
        int xPos = PieceList[0].getPos()[0];
        int yPos = PieceList[0].getPos()[1];
        try{ 
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
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "Reverting fusionGrid() to previous version");
        }
        try {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i + xPos < DisplayGrid.length && j + yPos < DisplayGrid[0].length) {
                    if (PieceGrid[i][j] != 0) {
                        DisplayGrid[i + xPos][j + yPos] = PieceGrid[i][j];
                    }
                }
            }
        }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "Reverting fusionGrid() to previous version");
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (CurrentGrid[i][j] != 0) {
                    DisplayGrid[i][j] = CurrentGrid[i][j];
                }
            }
        }
    }

    /**
     * Method to clear the display grid
     */
    public void clearDisplayGrid() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                DisplayGrid[i][j] = 0;
            }
        }
    }

    /**
     * Method to get the piece grid
     * 
     * @return Piece grid
     */
    public int[][] getPieceGrid() {
        return PieceGrid;
    }

    /**
     * Method to get the next piece
     * 
     * @return Next piece
     */
    public Piece returnNextPiece() {
        return PieceList[1];
    }

    /**
     * Method to get the hold piece
     * 
     * @return Hold piece
     */
    public Piece returnHoldPiece() {
        return holdPiece;
    }

    /**
     * Method to delete a line if it is full
     * 
     * @return True if a line is full, false otherwise
     */
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
                    soundPlayer.playSound(1);
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
            if (linesRemoved == 4) {
                soundPlayer.setVolume(5.0f, 3);
                soundPlayer.playSound(3);
            } else {
                soundPlayer.playSound(1);
            }
        }
        calculateLevel();
        return complete;
    }

    /**
     * Method to get the level
     * 
     * @return Level of the game
     */
    public short getLevel() {
        return level;
    }

    /**
     * Method to calculate the level
     */
    private void calculateLevel() {
        short newLevel = (short) Math.max(1, (Math.log(score + 1) / Math.log(3) / 2));
        level = newLevel;
    }

    /**
     * Method to move a piece horizontally
     * 
     * @param moveRight True if the piece needs to move right, false to move left
     */
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

    /**
     * Method to rotate the piece
     */
    public void rotatePiece() {
        int[][] newPieceGrid = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newPieceGrid[i][j] = PieceGrid[3 - j][i];
            }
        }
        PieceGrid = newPieceGrid;
    }

    /**
     * Method to check if a piece can rotate
     * 
     * @return True if the piece can rotate, false otherwise
     */
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
