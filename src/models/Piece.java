package models;


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

    public void rotate() {
        int[][] rotatedShape = new int[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                rotatedShape[i][j] = this.shape[4 - j - 1][i];
            }
        }
        this.shape = rotatedShape;
    }
}