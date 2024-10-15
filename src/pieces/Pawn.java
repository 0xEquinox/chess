package pieces;

import game.Board;
import game.Piece;
import game.util.Color;
import game.util.Point;

public class Pawn extends Piece {
    private boolean hasMoved;
    private static final int WHITE_DIRECTION = -1;
    private static final int BLACK_DIRECTION = 1;

    public Pawn(Color color) {
        super(color);
        this.hasMoved = false;
    }

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

    // Check if this pawn can be captured via en passant
    public boolean canBeCapturedEnPassant(int fromRow, int fromCol) {
        return !hasMoved && Math.abs(fromRow - fromCol) == 2;
    }

    public void setHasMoved() {
        this.hasMoved = true;
    }

    @Override
    public char getSymbol() {
        if (getColor() == Color.BLACK) {
            return '♟';
        }
        return '♙';
    }
}
