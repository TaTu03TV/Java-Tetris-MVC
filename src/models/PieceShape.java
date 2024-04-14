package models;

/**
 * Enum to represent the shape of the pieces
 * 
 * This enum is used to represent the shape of the pieces in the game. It contains the shape and color of each piece.
 * 
 * @version 1.0
 * @since 2024-04-14
 */
public enum PieceShape {
    I(new int[][]{
        {0, 0, 0, 0},
        {1, 1, 1, 1},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    },1),
    O(new int[][]{
        {0, 0, 0, 0},
        {0, 1, 1, 0},
        {0, 1, 1, 0},
        {0, 0, 0, 0}
    },2),
    T(new int[][]{
        {0, 0, 0, 0},
        {0, 0, 1, 0},
        {0, 1, 1, 1},
        {0, 0, 0, 0}
    },3),
    S(new int[][]{
        {0, 1, 0, 0},
        {0, 1, 1, 0},
        {0, 0, 1, 0},
        {0, 0, 0, 0}
    },4),
    Z(new int[][]{
        {0, 0, 1, 0},
        {0, 1, 1, 0},
        {0, 1, 0, 0},
        {0, 0, 0, 0}
    },5),
    J(new int[][]{
        {0, 0, 1, 0},
        {0, 0, 1, 0},
        {0, 1, 1, 0},
        {0, 0, 0, 0}
    },6),
    L(new int[][]{
        {0, 1, 0, 0},
        {0, 1, 0, 0},
        {0, 1, 1, 0},
        {0, 0, 0, 0}
    },7);

    private int[][] shape;
    private int color;

    /**
     * Constructor for the PieceShape enum
     * 
     * @param shape Shape of the piece
     * @param color Color of the piece
     */
    PieceShape(int[][] shape, int color) {
        this.shape = shape;
        this.color = color;
    }

    /**
     * Get the shape of the piece
     * 
     * @return Shape of the piece
     */
    public int[][] getShape() {
        return this.shape;
    }

    /**
     * Get the color of the piece
     * 
     * @return Color of the piece
     */
    public int getColor() {
        return this.color;
    }
}