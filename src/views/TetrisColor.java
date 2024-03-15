package views;

import java.awt.Color;

public enum TetrisColor {
    I(Color.CYAN, 1),
    O(Color.YELLOW, 2),
    T(Color.MAGENTA, 3),
    S(Color.GREEN, 4),
    Z(Color.RED, 5),
    J(Color.BLUE, 6),
    L(Color.ORANGE, 7),
    EMPTY(new Color(47, 39, 41), 0); //47, 39, 41

    private Color color;
    private int value;

    TetrisColor(Color color, int value) {
        this.color = color;
        this.value = value;
    }

    public Color getColor() {
        return this.color;
    }

    public static TetrisColor getColorByValue(int value) {
        for (TetrisColor color : TetrisColor.values()) {
            if (color.value == value) {
                return color;
            }
        }
        return EMPTY; // return EMPTY color if no match is found
    }
}
