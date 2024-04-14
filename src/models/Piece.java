package models;

import java.util.Random;

/**
 * Class to represent the pieces in the game
 * 
 * This class is used to represent the pieces in the game. It contains the shape, color, and position of the piece. It also contains methods to get a random piece, get the shape of the piece, get the color of the piece, set the color of the piece, get the position of the piece, set the position of the piece, and place a random piece on the grid.
 * 
 * @version 1.0
 * @since 2024-04-14
 */
public class Piece {

    private PieceShape shape;
    private int color;
    private int xpos;
    private int ypos;

    /**
     * Constructor for the Piece class
     * 
     * @param shape Shape of the piece
     * @param xpos X position of the piece
     * @param ypos Y position of the piece
     */
    public Piece(PieceShape shape, int xpos, int ypos) {
        this.shape = shape;
        this.color = shape.getColor();
        this.xpos = xpos;
        this.ypos = ypos;
    }

    /**
     * Copy constructor for the Piece class
     * 
     * @param other Piece to copy
     */
    public Piece(Piece other) { 
        this.shape = other.shape;
        this.color = other.color;
        this.xpos = other.xpos;
        this.ypos = other.ypos;
    }

    /**
     * Method to get a random piece
     * 
     * @return Random piece
     */
    public Piece getRandomPiece() {
        PieceShape[] shapes = PieceShape.values();
        PieceShape randomShape = shapes[new Random().nextInt(shapes.length)];
        return new Piece(randomShape, 5, 0);
    }

    /**
     * Method to get the shape of the piece
     * 
     * @return Shape of the piece
     */
    public int[][] getShape() {
        return this.shape.getShape();
    }

    /**
     * Method to get the color of the piece
     * 
     * @return Color of the piece
     */
    public int getColor() {
        return this.color;
    }

    /**
     * Method to set the color of the piece
     * 
     * @param color Color to set
     */
    public void setColor(int color) {
        this.color = color;
    }
    
    /**
     * Method to get the x and y position of the piece
     * 
     * @return Array with the x and y position of the piece
     */
    public int[] getPos(){
        int[] pos = {this.xpos, this.ypos};
        return pos;
    }

    /**
     * Method to set the x and y position of the piece
     * 
     * @param x X position to set
     * @param y Y position to set
     */
    public void setPos(int x, int y){
        this.xpos = x;
        this.ypos = y;
    }

    /**
     * Method to place a random piece on the grid
     * 
     * @param PieceGrid Grid to place the piece on
     * @param render Whether to render the piece
     */
    public static Piece placeRandomPiece(int[][] PieceGrid, boolean render) {
        PieceShape[] shapes = PieceShape.values();
        PieceShape randomShape = shapes[new Random().nextInt(shapes.length)];
        Piece randomPiece = new Piece(randomShape, 5, 0);
        return randomPiece;
    }
}

