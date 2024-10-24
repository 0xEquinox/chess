package game;

import game.util.Color;
import game.util.Point;
import pieces.King;

/**
 * The Piece class represents a generic chess piece. It is an abstract class that other specific pieces
 * (e.g., Rook, Knight, Bishop) will extend. It contains common functionality shared by all chess pieces.
 */
public abstract class Piece {
    private final Color color;  // The color of the piece, either White or Black

    /**
     * Constructor for a Piece.
     *
     * @param color the color of the piece (White or Black)
     */
    public Piece(Color color) {
        this.color = color;
    }

    /**
     * Returns the color of the piece.
     *
     * @return the color of the piece
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Determines if a move is valid for the specific type of piece.
     * This method must be implemented by each specific piece type.
     *
     * @param from the starting position of the piece
     * @param to the target position for the move
     * @param board the current state of the chess board
     * @param color the color of the piece attempting the move
     * @return true if the move is valid, false otherwise
     */
    public boolean isValidMove(Point from, Point to, Board board, Color color, boolean checkMode) {
        if (color != getColor()) {
            return false;
        }

        Piece piece = board.getPieceAt(to.getY(), to.getX());
        if (piece == null) {
            return true;
        }

        if (!checkMode && piece instanceof King) {
            return false;
        }

        return piece.color != color;
    }

    /**
     * Returns the symbol representing the piece (e.g., 'R' for Rook, 'N' for Knight).
     * This method must be implemented by each specific piece type.
     *
     * @return the character symbol of the piece
     */
    public abstract char getSymbol();
}