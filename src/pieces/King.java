package pieces;

import game.Board;
import game.Piece;
import game.util.Color;
import game.util.Point;

public class King extends Piece {
    private boolean hasMoved;

    public King(Color color) {
        super(color);
        this.hasMoved = false;
    }

    public boolean isInCheck(Board board) {
        return false;
    }

    public boolean isInCheckmate(Board board) {
        return false;
    }

    @Override
    public boolean isValidMove(Point from, Point to, Board board, Color color) {
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
                return board.isSquareAttacked(to, getColor());
            }
        }

        // Castling: Check if the move is a castling move
        if (!hasMoved && rowDiff == 0 && Math.abs(toCol - fromCol) == 2) {
            // Castling conditions
            return isCastlingValid(from, to, board);
        }

        return false;
    }

    // Castling-specific checks
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
            return false;  // Invalid if the Rook has moved or is missing
        }

        // Ensure all squares between the King and Rook are empty
        int direction = isKingside ? 1 : -1;
        for (int col = fromCol + direction; col != rookCol; col += direction) {
            if (board.getPieceAt(col, row) != null) {
                return false;
            }
        }

        // Ensure the King is not in check, doesn't pass through check, and doesn't land in check
        for (int col = fromCol; col != toCol + direction; col += direction) {
            if (board.isSquareAttacked(new Point(col, row), getColor())) {
                return false;  // Castling fails if any square is under attack
            }
        }

        return true;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public char getSymbol() {
        if (getColor() == Color.WHITE) {
            return '♔';
        }
        return '♚';
    }
}
