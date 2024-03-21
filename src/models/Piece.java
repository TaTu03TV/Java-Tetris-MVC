package models;

import java.util.Random;

public class Piece {

    private PieceShape shape;
    private int color;
    private int xpos;
    private int ypos;

    public Piece(PieceShape shape, int xpos, int ypos) {
        this.shape = shape;
        this.color = 0;
        this.xpos = xpos;
        this.ypos = ypos;
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

    public static Piece placeRandomPiece(int[][] PieceGrid) {
        PieceShape[] shapes = PieceShape.values();
        PieceShape randomShape = shapes[new Random().nextInt(shapes.length)];


        // Create a new piece with the random shape
        Piece randomPiece = new Piece(randomShape, 5, 0);
        randomPiece.setColor(randomShape.getColor());
    
        // Place the piece at the middle of the grid
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (randomPiece.getShape()[i][j] != 0) {
                    PieceGrid[i][j] = randomPiece.getColor();
                }
            }
        }
    
        return randomPiece;
    }
}

