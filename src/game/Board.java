package game;

import game.util.Color;
import game.util.Point;
import pieces.*;

public class Board {
    private final Piece[][] board;

    public Board() {
        this.board = new Piece[8][8];
        setupBoard();
    }

    public void setupBoard() {
        // Set up Black pieces (lowercase)
        board[0][0] = new Rook(Color.BLACK);
        board[0][1] = new Knight(Color.BLACK);
        board[0][2] = new Bishop(Color.BLACK);
        board[0][3] = new Queen(Color.BLACK);
        board[0][4] = new King(Color.BLACK);
        board[0][5] = new Bishop(Color.BLACK);
        board[0][6] = new Knight(Color.BLACK);
        board[0][7] = new Rook(Color.BLACK);

        // Black pawns
        for (int col = 0; col < 8; col++) {
            board[1][col] = new Pawn(Color.BLACK);
        }

        // Set up White pieces (uppercase)
        board[7][0] = new Rook(Color.WHITE);
        board[7][1] = new Knight(Color.WHITE);
        board[7][2] = new Bishop(Color.WHITE);
        board[7][3] = new Queen(Color.WHITE);
        board[7][4] = new King(Color.WHITE);
        board[7][5] = new Bishop(Color.WHITE);
        board[7][6] = new Knight(Color.WHITE);
        board[7][7] = new Rook(Color.WHITE);

        // White pawns
        for (int col = 0; col < 8; col++) {
            board[6][col] = new Pawn(Color.WHITE);
        }

        // Empty squares
        for (int row = 2; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = null;
            }
        }
    }

    public Piece getPieceAt(int row, int col) {
        return this.board[row][col];
    }

    public void movePiece(Point from, Point to, Color color) throws IllegalArgumentException {
        Piece piece = getPieceAt(from.getY(), from.getX());

        if (piece.isValidMove(from, to, this, color)) {
            this.board[to.getY()][to.getX()] = piece;
        } else {
            throw new IllegalArgumentException("Illegal move");
        }

        // remove the piece from the old position
        this.board[from.getY()][from.getX()] = null;
    }

    public King[] getKings() {
        King[] kings = new King[2];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece instanceof King) {
                    // TODO make the code better
                    if (kings[0] == null) {
                        kings[0] = (King) piece;
                    } else {
                        kings[1] = (King) piece;
                    }
                }
            }
        }

        return kings;
    }

    // Method to check if a square is attacked by any opponent piece
    public boolean isSquareAttacked(Point square, Color defendingColor) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = getPieceAt(col, row);

                // Skip if the square is empty or the piece belongs to the defender
                if (piece == null || piece.getColor() == defendingColor) {
                    continue;
                }

                Point from = new Point(col, row);
                Color attackingColor = piece.getColor();

                // Check if this opponent's piece can move to the square
                if (piece.isValidMove(from, square, this, attackingColor)) {
                    return true;  // The square is attacked
                }
            }
        }

        return false;  // No valid move found; the square is not attacked
    }


    // TODO: Fix alignment of board
    @Override
    public String toString() {
        String result = "   a  b  c  d  e  f  g  h\n";
        result += "  ------------------------\n";

        for (int row = 0; row < 8; row++) {
            result += (8 - row) + " |";  // Row label
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece == null) {
                    result += " □ ";  // Full-width character for empty square
                } else {
                    result += " " + piece.getSymbol() + " ";  // Chess piece symbol
                }
            }
            result += "| " + (8 - row) + "\n";  // Row label on the right
        }

        result += "  ------------------------\n";
        result += "   a  b  c  d  e  f  g  h\n";

        return result;
    }
}
