package models;

import java.util.Random;

public enum Piece {
    I(new int[][]{
        {0, 0, 0, 0},
        {1, 1, 1, 1},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }, 1, 5, 0),
    O(new int[][]{
        {0, 1, 1, 0},
        {0, 1, 1, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }, 2, 5, 0),
    T(new int[][]{
        {0, 1, 0, 0},
        {1, 1, 1, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }, 3, 5, 0),
    S(new int[][]{
        {0, 1, 1, 0},
        {1, 1, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }, 4, 5, 0),
    Z(new int[][]{
        {1, 1, 0, 0},
        {0, 1, 1, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }, 5, 5, 0),
    J(new int[][]{
        {1, 0, 0, 0},
        {1, 1, 1, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }, 6, 5, 0),
    L(new int[][]{
        {0, 0, 1, 0},
        {1, 1, 1, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }, 7, 5, 0);

    private int[][] shape;
    private int xpos;
    private int ypos;

    private int color;

    Piece(int[][] shape, int color, int xpos, int ypos) {

        this.shape = shape;
        this.color = color;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public int[][] getShape() {
        return this.shape;
    }

    public int getColor() {
        return this.color;
    }
    
    public int[] getPos(){
        int[] pos = {this.xpos, this.ypos};
        return pos;
    }

    public static Piece placeRandomPiece(int[][] PieceGrid) {
        // Choose a random piece
        Piece[] pieces = Piece.values();
        Piece randomPiece = pieces[new Random().nextInt(pieces.length)];
    
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

