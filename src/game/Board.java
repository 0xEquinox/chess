package game;

import game.util.Color;
import game.util.Point;
import pieces.*;

/**
 * The Board class represents a chess board, containing an 8x8 grid of pieces.
 * It handles the initialization of the chess board, moving pieces, and checking game conditions.
 */
public class Board {
    private final Piece[][] board;  // 2D array to represent the chess board

    /**
     * Constructor that initializes the board and sets up the pieces in their starting positions.
     */
    public Board() {
        this.board = new Piece[8][8];
        setupBoard();  // Set up the pieces for the start of the game
    }

    /**
     * Initializes the chess board with pieces in their standard starting positions.
     * Black pieces are placed at the top (rows 0 and 1), white pieces at the bottom (rows 6 and 7).
     */
    public void setupBoard() {
        // Set up Black pieces
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

        // White pawns on the seventh row
        for (int col = 0; col < 8; col++) {
            board[6][col] = new Pawn(Color.WHITE);
        }

        // Empty squares in the middle of the board (rows 2 through 5)
        for (int row = 2; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = null;
            }
        }
    }

    /**
     * Retrieves the piece at a given board position.
     *
     * @param row the row index (0 to 7)
     * @param col the column index (0 to 7)
     * @return the piece at the specified position, or null if the square is empty
     */
    public Piece getPieceAt(int row, int col) {
        return this.board[row][col];
    }

    /**
     * Moves a piece from one square to another if the move is valid.
     *
     * @param from the starting point of the piece
     * @param to the destination point of the piece
     * @param color the color of the player making the move
     * @throws IllegalArgumentException if the move is invalid
     */
    public void movePiece(Point from, Point to, Color color) throws IllegalArgumentException {
        Piece piece = getPieceAt(from.getY(), from.getX());

        if (piece.isValidMove(from, to, this, color)) {
            this.board[to.getY()][to.getX()] = piece;

            // Track if the piece has moved for future logic (e.g., castling)
            if (piece instanceof Pawn) {
                ((Pawn) piece).setHasMoved();
            } else if (piece instanceof Rook) {
                ((Rook) piece).setHasMoved();
            } else if (piece instanceof King) {
                ((King) piece).setHasMoved();
            }
        } else {
            throw new IllegalArgumentException("Illegal move");
        }

        // Remove the piece from the original position
        this.board[from.getY()][from.getX()] = null;
    }

    /**
     * Retrieves both kings on the board.
     *
     * @return an array containing the white and black kings
     */
    public King[] getKings() {
        King[] kings = new King[2];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece instanceof King) {
                    // Store the kings in the array
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

    /**
     * Checks if a given square is under attack by any opponent piece.
     *
     * @param square the point representing the square to check
     * @param defendingColor the color of the piece defending the square
     * @return true if the square is attacked, false otherwise
     */
    public boolean isSquareAttacked(Point square, Color defendingColor) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = getPieceAt(row, col);

                // Check only opponent's pieces
                if (piece != null && piece.getColor() != defendingColor) {
                    Point from = new Point(col, row);
                    Color attackingColor = piece.getColor();

                    // If an opponent's piece can move to this square, it is under attack
                    if (piece.isValidMove(from, square, this, attackingColor)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Returns the 2D array of pieces representing the board.
     *
     * @return the 2D array of pieces
     */
    public Piece[][] getPieces() {
        return this.board;
    }

    /**
     * Converts the board to a human-readable string format, useful for displaying the board state.
     *
     * @return the string representation of the board
     */
    @Override
    public String toString() {
        String result = "    a  b  c  d  e  f  g  h\n";
        result += "  ------------------------\n";

        for (int row = 0; row < 8; row++) {
            result += (8 - row) + " |";  // Row label
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece == null) {
                    result += " □ ";  // Empty square
                } else {
                    result += " " + piece.getSymbol() + " ";  // Chess piece symbol
                }
            }
            result += "| " + (8 - row) + "\n";  // Row label on the right
        }

        result += "  ------------------------\n";
        result += "    a  b  c  d  e  f  g  h\n";

        return result;
    }

    /**
     * Clears the specified square on the board, primarily used for testing purposes.
     *
     * @param square the point representing the square to clear
     */
    public void clearSquare(Point square) {
        this.board[square.getY()][square.getX()] = null;
    }

    /**
     * Places a piece on the specified square, primarily used for testing purposes.
     *
     * @param piece the piece to place
     * @param square the point representing the square to place the piece on
     */
    public void placePiece(Piece piece, Point square) {
        this.board[square.getY()][square.getX()] = piece;
    }
}