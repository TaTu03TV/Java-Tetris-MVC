package models;

import java.util.Random;

public class Piece {

    private PieceShape shape;
    private int color;
    private int xpos;
    private int ypos;

    public Piece(PieceShape shape, int xpos, int ypos) {
        this.shape = shape;
        this.color = shape.getColor();
        this.xpos = xpos;
        this.ypos = ypos;
    }


    public Piece(Piece other) { //constructeur par copie
        this.shape = other.shape;
        this.color = other.color;
        this.xpos = other.xpos;
        this.ypos = other.ypos;
    }

    public Piece getRandomPiece() {
        PieceShape[] shapes = PieceShape.values();
        PieceShape randomShape = shapes[new Random().nextInt(shapes.length)];
        return new Piece(randomShape, 5, 0);
    }

    public Piece Piece(){
        return getRandomPiece();
    }


    public int[][] getShape() {
        return this.shape.getShape();
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }
    
    public int[] getPos(){
        int[] pos = {this.xpos, this.ypos};
        return pos;
    }

    public void setPos(int x, int y){
        this.xpos = x;
        this.ypos = y;
    }

    public static Piece placeRandomPiece(int[][] PieceGrid, boolean render) {
        PieceShape[] shapes = PieceShape.values();
        PieceShape randomShape = shapes[new Random().nextInt(shapes.length)];
    
        // Create a new piece with the random shape and its color
        Piece randomPiece = new Piece(randomShape, 5, 0);
        return randomPiece;
    }
}

