package pieces;

import game.Board;
import game.Piece;
import game.util.Color;
import game.util.Point;

/**
 * The King class represents a King chess piece. It handles basic movement rules,
 * castling, and checks if the King is in check or checkmate.
 */
public class King extends Piece {
    private boolean hasMoved;

    /**
     * Constructs a King piece with the specified color and initializes its state.
     *
     * @param color the color of the King (White or Black)
     */
    public King(Color color) {
        super(color);
        this.hasMoved = false;
    }

    /**
     * Checks if the King is currently in check (i.e., if any opponent piece
     * can attack the King's current position).
     *
     * @param board the current state of the chess board
     * @return true if the King is in check, false otherwise
     */
    public boolean isInCheck(Board board) {
        // Find the King's position
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPieceAt(i, j);
                if (piece == this) {
                    return board.isSquareAttacked(new Point(j, i), getColor(), true);
                }
            }
        }
        return false; // This should never happen
    }

    /**
     * Determines if the King is in checkmate, where no valid moves can save the King.
     *
     * @param board the current state of the chess board
     * @return true if the King is in checkmate, false otherwise
     */
    public boolean isInCheckmate(Board board) {
        // Check if the King is in check
        if (!isInCheck(board)) {
            return false;
        }

        // Check if the King can move to a square that would save it
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPieceAt(i, j);
                if (piece != null && piece.getColor() != getColor()) {
                    Point from = new Point(j, i);
                    Color attackingColor = piece.getColor();

                    // If an opponent's piece can move to this square, it is under attack
                    if (piece.isValidMove(from, new Point(j, i), board, attackingColor, true)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Validates whether a move is legal for the King. The King can move one square
     * in any direction or perform castling if certain conditions are met.
     *
     * @param from the starting position of the King
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

        int fromRow = from.getY();
        int fromCol = from.getX();
        int toRow = to.getY();
        int toCol = to.getX();

        // Calculate the difference in rows and columns
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        // Basic move: King can move one square in any direction
        if (rowDiff <= 1 && colDiff <= 1) {
            Piece destinationPiece = board.getPieceAt(toCol, toRow);
            if (destinationPiece == null || destinationPiece.getColor() != getColor()) {
                return !board.isSquareAttacked(to, getColor(), false);
            }
        }

        // Castling: Check if the move is a castling move
        if (!hasMoved && rowDiff == 0 && Math.abs(toCol - fromCol) == 2) {
            return isCastlingValid(from, to, board);
        }

        return false;
    }

    /**
     * Checks whether castling is valid under the current game conditions.
     *
     * @param from the starting position of the King
     * @param to the target position for the castling move
     * @param board the current state of the chess board
     * @return true if castling is valid, false otherwise
     */
    private boolean isCastlingValid(Point from, Point to, Board board) {
        int fromCol = from.getX();
        int toCol = to.getX();
        int row = from.getY();

        // Determine if castling is queenside or kingside
        boolean isKingside = toCol > fromCol;
        int rookCol = isKingside ? 7 : 0;

        // Get the Rook
        Piece rook = board.getPieceAt(rookCol, row);
        if (!(rook instanceof Rook) || ((Rook) rook).hasMoved()) {
            return false; // Invalid if the Rook has moved or is missing
        }

        // Ensure all squares between the King and Rook are empty
        int direction = isKingside ? 1 : -1;
        for (int col = fromCol + direction; col != rookCol; col += direction) {
            if (board.getPieceAt(row, col) != null) {
                return false;
            }
        }

        // Ensure the King is not in check, doesn't pass through check, and doesn't land in check
        for (int col = fromCol; col != toCol + direction; col += direction) {
            if (board.isSquareAttacked(new Point(col, row), getColor(), false)) {
                return false; // Castling fails if any square is under attack
            }
        }

        // Move the Rook to the appropriate position for castling
        board.movePiece(new Point(rookCol, row), new Point(toCol - direction, row), getColor());
        ((Rook) rook).setHasMoved();

        return true;
    }

    /**
     * Marks the King as having moved, preventing further castling.
     */
    public void setHasMoved() {
        this.hasMoved = true;
    }

    /**
     * Returns the symbol representing the King piece.
     *
     * @return '♔' if the King is White, '♚' if the King is Black
     */
    @Override
    public char getSymbol() {
        return getColor() == Color.WHITE ? '♔' : '♚';
    }
}
