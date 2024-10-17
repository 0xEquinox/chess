package pieces;

import game.Board;
import game.Piece;
import game.util.Color;
import game.util.Point;

/**
 * The Pawn class represents a pawn chess piece with specific movement rules.
 */
public class Pawn extends Piece {
    private boolean hasMoved;
    private static final int WHITE_DIRECTION = -1;
    private static final int BLACK_DIRECTION = 1;

    /**
     * Constructs a Pawn piece with the specified color and initializes its state.
     *
     * @param color the color of the pawn (White or Black)
     */
    public Pawn(Color color) {
        super(color);
        this.hasMoved = false;
    }

    /**
     * Validates the pawn's move. Pawns move differently depending on whether they're advancing,
     * capturing, or performing the en passant move.
     *
     * @param from        the starting position of the pawn
     * @param to          the target position of the pawn
     * @param board       the current state of the chess board
     * @param playerColor the color of the player making the move
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean isValidMove(Point from, Point to, Board board, Color playerColor) {
        // Check if the piece being moved is owned by the current player
        if (playerColor != getColor()) {
            return false;
        }

        int fromRow = from.getY();
        int fromCol = from.getX();
        int toRow = to.getY();
        int toCol = to.getX();
        int direction = (getColor() == Color.WHITE) ? WHITE_DIRECTION : BLACK_DIRECTION;
        int startRow = (getColor() == Color.WHITE) ? 6 : 1;  // Row where pawns start

        // Standard one-square move forward
        if (toCol == fromCol && toRow == fromRow + direction && board.getPieceAt(toRow, toCol) == null) {
            return true;
        }

        // Double move forward on first move
        if (!hasMoved && toCol == fromCol && toRow == fromRow + 2 * direction && board.getPieceAt(toRow, toCol) == null
            && board.getPieceAt(toRow - direction, toCol) == null) {
            return true;
        }

        // Capture move (diagonally)
        if (Math.abs(toCol - fromCol) == 1 && toRow == fromRow + direction && board.getPieceAt(toRow, toCol) != null
            && board.getPieceAt(toRow, toCol).getColor() != getColor()) {
            return true;
        }

        // En passant (additional logic required to track the previous move)
        return Math.abs(toCol - fromCol) == 1 && toRow == fromRow + direction && board.getPieceAt(fromRow, toCol) != null
                && ((Pawn) board.getPieceAt(fromRow, toCol)).canBeCapturedEnPassant(fromRow, fromCol);
    }

    /**
     * Checks if this pawn can be captured via en passant.
     *
     * @param fromRow the row of the pawn's last move
     * @param fromCol the column of the pawn's last move
     * @return true if the pawn can be captured en passant, false otherwise
     */
    public boolean canBeCapturedEnPassant(int fromRow, int fromCol) {
        // En passant is only possible if the pawn hasn't moved yet and was just moved two squares
        return !hasMoved && Math.abs(fromRow - fromCol) == 2;
    }

    /**
     * Marks the pawn as having moved, disabling double-move in the future.
     */
    public void setHasMoved() {
        this.hasMoved = true;
    }

    /**
     * Returns the symbol representing the pawn piece.
     *
     * @return '♙' for a white pawn, '♟' for a black pawn
     */
    @Override
    public char getSymbol() {
        if (getColor() == Color.BLACK) {
            return '♟';
        }
        return '♙';
    }
}
