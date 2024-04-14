package views;

import java.awt.Color;

/**
 * Enum class to define the colors of the Tetris pieces
 * 
 * This enum class is used to define the colors of the Tetris pieces. It contains the color and value of each Tetris piece.
 * 
 * @version 1.0
 * @since 2024-04-14
 */
public enum TetrisColor {
    I(new Color(0, 188, 212), 1), // Cyan
    O(new Color(255, 235, 59), 2), // Yellow
    T(new Color(156, 39, 176), 3), // Magenta
    S(new Color(76, 175, 80), 4), // Green
    Z(new Color(244, 67, 54), 5), // Red
    J(new Color(33, 150, 243), 6), // Blue
    L(new Color(255, 152, 0), 7), // Orange
    EMPTY(new Color(47, 39, 41), 0), //47, 39, 41
    GHOST(new Color(250, 250, 250, 100), 8);

    private Color color;
    private int value;

    /**
     * Constructor for the TetrisColor enum
     * 
     * @param color the color of the Tetris piece
     * @param value the value of the Tetris piece
     */
    TetrisColor(Color color, int value) {
        this.color = color;
        this.value = value;
    }

    /**
     * Get the color of the Tetris piece
     * 
     * @return the color of the Tetris piece
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Get the color of the Tetris piece by its value
     * 
     * @return the value of the Tetris piece
     */
    public static TetrisColor getColorByValue(int value) {
        for (TetrisColor color : TetrisColor.values()) {
            if (color.value == value) {
                return color;
            }
        }
        return EMPTY; // return EMPTY color if no match is found
    }
}
