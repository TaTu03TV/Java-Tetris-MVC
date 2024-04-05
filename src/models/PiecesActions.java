package models;
public class PiecesActions {
    public static void movePieceLeft(int[][] PieceGrid) {
        // Check if the entire piece can move to the left
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (PieceGrid[i][j] != 0 && i <= 0) {
                    return; // If any part of the piece is at the left border, don't move the piece
                }
            }
        }

        // If the entire piece can move to the left, move it
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (PieceGrid[i][j] != 0) {
                    PieceGrid[i - 1][j] = PieceGrid[i][j];
                    PieceGrid[i][j] = 0;
                }
            }
        }
    }

    public static void movePieceRight(int[][] PieceGrid) {
        // Check if the entire piece can move to the right
        for (int i = 9; i >= 0; i--) {
            for (int j = 0; j < 20; j++) {
                if (PieceGrid[i][j] != 0 && i >= 9) {
                    return; // If any part of the piece is at the right border, don't move the piece
                }
            }
        }

        // If the entire piece can move to the right, move it
        for (int i = 9; i >= 0; i--) {
            for (int j = 0; j < 20; j++) {
                if (PieceGrid[i][j] != 0) {
                    PieceGrid[i + 1][j] = PieceGrid[i][j];
                    PieceGrid[i][j] = 0;
                }
            }
        }
    }
}