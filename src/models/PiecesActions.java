package models;

public class PiecesActions {
    public static void movePieceLeft(int[][] PieceGrid) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (PieceGrid[i][j] != 0 && i > 0) {
                    PieceGrid[i - 1][j] = PieceGrid[i][j];
                    PieceGrid[i][j] = 0;
                }
            }
        }
    }

    public static void movePieceRight(int[][] PieceGrid) {
        for (int i = 9; i >= 0; i--) {
            for (int j = 0; j < 20; j++) {
                if (PieceGrid[i][j] != 0 && i < 9) {
                    PieceGrid[i + 1][j] = PieceGrid[i][j];
                    PieceGrid[i][j] = 0;
                }
            }
        }
    }
}