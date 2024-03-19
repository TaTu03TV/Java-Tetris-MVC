package models;

import java.util.Random;

public enum Piece {
    I(new int[][]{
        {0, 0, 0, 0},
        {1, 1, 1, 1},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }, 1),
    O(new int[][]{
        {0, 1, 1, 0},
        {0, 1, 1, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }, 2),
    T(new int[][]{
        {0, 1, 0, 0},
        {1, 1, 1, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }, 3),
    S(new int[][]{
        {0, 1, 1, 0},
        {1, 1, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }, 4),
    Z(new int[][]{
        {1, 1, 0, 0},
        {0, 1, 1, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }, 5),
    J(new int[][]{
        {1, 0, 0, 0},
        {1, 1, 1, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }, 6),
    L(new int[][]{
        {0, 0, 1, 0},
        {1, 1, 1, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    }, 7);

    private int[][] shape;

    private int color;

    Piece(int[][] shape, int color) {
        this.shape = shape;
        this.color = color;
    }

    public int[][] getShape() {
        return this.shape;
    }

    public int getColor() {
        return this.color;
    }

    public static void placeRandomPiece(int[][] PieceGrid) {
        // Choisissez une pièce aléatoire
        Piece[] pieces = Piece.values();
        Piece randomPiece = pieces[new Random().nextInt(pieces.length)];

        // Placez la pièce au milieu de la grille
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (randomPiece.getShape()[i][j] != 0) {
                    PieceGrid[3 + i][j] = randomPiece.getColor();
                }
            }
        }
    }
}