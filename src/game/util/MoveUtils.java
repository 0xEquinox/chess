package game.util;

import game.Board;

public class MoveUtils {
    // Check if the vertical path between (fromRow, fromCol) and (toRow, toCol) is clear
    public static boolean isVerticalClear(int fromRow, int fromCol, int toRow, int toCol, Board board) {
        int step = (fromRow < toRow) ? 1 : -1;
        for (int row = fromRow + step; row != toRow; row += step) {
            if (board.getPieceAt(row, fromCol) != null) {
                return false;
            }
        }
        return true;
    }

    // Check if the horizontal path between (fromRow, fromCol) and (toRow, toCol) is clear
    public static boolean isHorizontalClear(int fromRow, int fromCol, int toRow, int toCol, Board board) {
        int step = (fromCol < toCol) ? 1 : -1;
        for (int col = fromCol + step; col != toCol; col += step) {
            if (board.getPieceAt(fromRow, col) != null) {
                return false;
            }
        }
        return true;
    }

    // Check if the diagonal path between (fromRow, fromCol) and (toRow, toCol) is clear
    public static boolean isDiagonalClear(int fromRow, int fromCol, int toRow, int toCol, Board board) {
        int rowStep = (toRow > fromRow) ? 1 : -1;
        int colStep = (toCol > fromCol) ? 1 : -1;
        int row = fromRow + rowStep;
        int col = fromCol + colStep;
        while (row != toRow && col != toCol) {
            if (board.getPieceAt(row, col) != null) {
                return false;
            }
            row += rowStep;
            col += colStep;
        }
        return true;
    }
}
