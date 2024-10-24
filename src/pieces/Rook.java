package pieces;

import game.Board;
import game.Piece;
import game.util.Color;
import game.util.MoveUtils;
import game.util.Point;

/**
 * The Rook class represents a rook chess piece with specific movement rules.
 */
public class Rook extends Piece {
    private boolean hasMoved; // Indicates if the rook has moved at least once

    public Rook(Color color) {
        super(color); // Call the constructor of the superclass (Piece) with the piece's color
        this.hasMoved = false; // Initialize hasMoved to false
    }

    /**
     * Validates the rook's move. A rook can move any number of squares along a rank or file.
     *
     * @param from the starting position of the rook
     * @param to the target position of the rook
     * @param board the current state of the chess board
     * @param color the color of the player making the move
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean isValidMove(Point from, Point to, Board board, Color color, boolean checkMode) {
        if (!super.isValidMove(from, to, board, color, checkMode)) {
            return false;
        }

        // Ensure that the move is not diagonal; the rook can only move horizontally or vertically
        if (to.getX() != from.getX() && to.getY() != from.getY()) {
            return false; // Invalid move if not on the same row or column
        }

        // Check for clear path horizontally or vertically
        return MoveUtils.isHorizontalClear(from.getY(), from.getX(), to.getY(), to.getX(), board) ||
                MoveUtils.isVerticalClear(from.getY(), from.getX(), to.getY(), to.getX(), board);
    }

    /**
     * Returns the symbol representing the rook piece.
     *
     * @return '♖' for a white rook, '♜' for a black rook
     */
    @Override
    public char getSymbol() {
        if (getColor() == Color.WHITE) {
            return '♖'; // Unicode for white rook
        }
        return '♜'; // Unicode for black rook
    }

    /**
     * Marks the rook as having moved, which can be useful for rules like castling.
     */
    public void setHasMoved() {
        this.hasMoved = true; // Set the hasMoved flag to true
    }

    /**
     * Checks if the rook has moved at least once.
     *
     * @return true if the rook has moved, false otherwise
     */
    public boolean hasMoved() {
        return this.hasMoved; // Return the state of the hasMoved flag
    }
}