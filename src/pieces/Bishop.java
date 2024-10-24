package pieces;

import game.Board;
import game.Piece;
import game.util.Color;
import game.util.MoveUtils;
import game.util.Point;


/**
 * The Bishop class represents a Bishop chess piece. It extends the abstract Piece class
 * and implements the specific movement and symbol of a Bishop.
 */
public class Bishop extends Piece {

    /**
     * Constructor for a Bishop piece.
     *
     * @param color the color of the Bishop (White or Black)
     */
    public Bishop(Color color) {
        super(color);
    }

    /**
     * Determines if the Bishop's move is valid. Bishops move diagonally across the board.
     * The move is valid if the destination is along a diagonal path and no other pieces
     * block the movement.
     *
     * @param from the starting position of the Bishop
     * @param to the target position for the move
     * @param board the current state of the chess board
     * @param color the color of the piece attempting the move
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean isValidMove(Point from, Point to, Board board, Color color, boolean checkMode) {
        if (!super.isValidMove(from, to, board, color, checkMode)) {
            return false;
        }
        // Check if the move follows a diagonal path
        if (!MoveUtils.isDiagonalMove(from, to)) {
            return false;
        }

        // Check if the diagonal path is clear of obstacles
        return MoveUtils.isDiagonalClear(from.getY(), from.getX(), to.getY(), to.getX(), board);
    }

    /**
     * Returns the symbol representing the Bishop piece.
     * The symbol depends on the color of the Bishop.
     *
     * @return '♗' if the Bishop is White, '♝' if the Bishop is Black
     */
    @Override
    public char getSymbol() {
        return getColor() == Color.WHITE ? '♗' : '♝';
    }
}