package pieces;

import game.Board;
import game.Piece;
import game.util.Color;
import game.util.Point;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Point from, Point to, Board board, Color color) {
        if (getColor() != color) {
            return false;
        }

        int fromRow = from.getY();
        int fromCol = from.getX();
        int toRow = to.getY();
        int toCol = to.getX();

        // Calculate the difference in rows and columns
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        // Knights move in an "L" shape: two squares in one direction and one in the other
        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            Piece destinationPiece = board.getPieceAt(toCol, toRow);

            // Allow move if destination is empty or contains an opponent's piece
            return destinationPiece == null || destinationPiece.getColor() != getColor();
        }

        // If move doesn't match the knight's pattern, it's invalid
        return false;
    }

    @Override
    public char getSymbol() {
        if (getColor() == Color.WHITE) {
            return '♘';
        }
        return '♞';
    }
}
