package pieces;

import game.Board;
import game.Piece;
import game.util.Color;
import game.util.Point;

/**
 * The Knight class represents a Knight chess piece, which moves in an "L" shape.
 */
public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
    }

    /**
     * Validates the knight's move. Knights move in an "L" shape and can jump over other pieces.
     *
     * @param from the starting position of the knight
     * @param to the target position of the knight
     * @param board the current state of the chess board
     * @param color the color of the piece making the move
     * @return true if the move is valid, false otherwise
     */
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

        return false; // Invalid move pattern for a knight
    }

    /**
     * Returns the symbol representing the Knight piece.
     *
     * @return '♘' for a white knight, '♞' for a black knight
     */
    @Override
    public char getSymbol() {
        return getColor() == Color.WHITE ? '♘' : '♞';
    }
}