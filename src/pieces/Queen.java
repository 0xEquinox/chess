package pieces;

import game.Board;
import game.Piece;
import game.util.Color;
import game.util.MoveUtils;
import game.util.Point;

/**
 * The Queen class represents a queen chess piece with specific movement rules.
 */
public class Queen extends Piece {
    public Queen(Color color) {
        super(color); // Call the constructor of the superclass (Piece) with the piece's color
    }

    /**
     * Validates the queen's move. A queen can move any number of squares along a rank, file, or diagonal.
     *
     * @param from the starting position of the queen
     * @param to the target position of the queen
     * @param board the current state of the chess board
     * @param color the color of the player making the move
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean isValidMove(Point from, Point to, Board board, Color color, boolean checkMode) {
        if (!super.isValidMove(from, to, board, color, checkMode)) {
            return false;
        }

        // Ensure the queen is moving either diagonally or horizontally or vertically but not both
        // The condition checks if the start and end points are neither on the same row nor column,
        // and also checks if the move is not blocked by any pieces.
        if ((from.getX() != to.getX()) && (from.getY() != to.getY()) &&
                !MoveUtils.isDiagonalMove(from, to)) {
            return false; // Invalid move if not on the same line or blocked
        }

        // Check for clear path either diagonally, horizontally, or vertically
        if (MoveUtils.isDiagonalMove(from, to)) {
            return MoveUtils.isDiagonalClear(from.getY(), from.getX(), to.getY(), to.getX(), board);
        }
        return MoveUtils.isHorizontalClear(from.getY(), from.getX(), to.getY(), to.getX(), board) ||
                MoveUtils.isVerticalClear(from.getY(), from.getX(), to.getY(), to.getX(), board);
    }

    /**
     * Returns the symbol representing the queen piece.
     *
     * @return '♕' for a white queen, '♛' for a black queen
     */
    @Override
    public char getSymbol() {
        if (getColor() == Color.WHITE) {
            return '♕'; // Unicode for white queen
        }
        return '♛'; // Unicode for black queen
    }
}
