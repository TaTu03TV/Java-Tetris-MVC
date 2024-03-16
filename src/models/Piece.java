package models;

import java.util.Random;

public enum Piece {
    I(new int[][]{
        {0, 0, 0, 0},
        {1, 1, 1, 1},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }),
    O(new int[][]{
        {0, 1, 1, 0},
        {0, 1, 1, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }),
    T(new int[][]{
        {0, 1, 0, 0},
        {1, 1, 1, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }),
    S(new int[][]{
        {0, 1, 1, 0},
        {1, 1, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }),
    Z(new int[][]{
        {1, 1, 0, 0},
        {0, 1, 1, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }),
    J(new int[][]{
        {1, 0, 0, 0},
        {1, 1, 1, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }),
    L(new int[][]{
        {0, 0, 1, 0},
        {1, 1, 1, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    });

    private int[][] shape;

    Piece(int[][] shape) {
        this.shape = shape;
    }

    public int[][] getShape() {
        return this.shape;
    }

    public static void placeRandomPiece(int[][] PieceGrid, int color) {
        // Choisissez une pièce aléatoire
        Piece[] pieces = Piece.values();
        Piece randomPiece = pieces[new Random().nextInt(pieces.length)];

        // Placez la pièce au milieu de la grille
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (randomPiece.getShape()[i][j] != 0) {
                    PieceGrid[3 + i][j] = color;
                }
            }
        }
    }
}