import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import game.Piece;
import game.util.Color;
import game.util.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.King;
import pieces.Rook;
import game.Board;

public class Testing {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();  // Assuming this sets up the starting position
    }

    // Test valid Knight movement
    @Test
    public void testKnightValidMoves() {
        // White Knight at (1, 7) (b1 in chess notation)
        Point start = new Point(1, 7);
        Piece knight = board.getPieceAt(start.getX(), start.getY());

        // Valid L-shaped moves
        assertTrue(knight.isValidMove(start, new Point(0, 5), board, Color.WHITE));  // a3
        assertTrue(knight.isValidMove(start, new Point(2, 5), board, Color.WHITE));  // c3
    }

    // Test invalid Knight movement
    @Test
    public void testKnightInvalidMoves() {
        // White Knight at (1, 7)
        Point start = new Point(1, 7);
        Piece knight = board.getPieceAt(start.getY(), start.getX());

        // Invalid moves (not L-shaped)
        assertFalse(knight.isValidMove(start, new Point(1, 5), board, Color.WHITE));  // vertical move
        assertFalse(knight.isValidMove(start, new Point(3, 7), board, Color.WHITE));  // horizontal move
    }

    // Test valid Pawn movement
    @Test
    public void testPawnValidMoves() {
        // White Pawn at (4, 6) (e2 in chess notation)
        Point start = new Point(4, 6);
        Piece pawn = board.getPieceAt(start.getY(), start.getX());

        // Valid moves: one square forward or two squares forward on the first move
        assertTrue(pawn.isValidMove(start, new Point(4, 5), board, Color.WHITE));  // e3
        assertTrue(pawn.isValidMove(start, new Point(4, 4), board, Color.WHITE));  // e4
    }

    // Test invalid Pawn movement
    @Test
    public void testPawnInvalidMoves() {
        // White Pawn at (4, 6)
        Point start = new Point(4, 6);
        Piece pawn = board.getPieceAt(start.getX(), start.getY());

        // Invalid move: pawns can't move backward or sideways
        assertFalse(pawn.isValidMove(start, new Point(3, 6), board, Color.WHITE));  // moving left
        assertFalse(pawn.isValidMove(start, new Point(4, 7), board, Color.WHITE));  // moving backward
    }

    // Test valid King movement
    @Test
    public void testKingValidMoves() {
        // White King at (4, 7) (e1 in chess notation)
        Point start = new Point(4, 7);
        Piece king = board.getPieceAt(start.getX(), start.getY());

        // Valid one-square moves
        assertTrue(king.isValidMove(start, new Point(4, 6), board, Color.WHITE));  // e2
        assertTrue(king.isValidMove(start, new Point(5, 6), board, Color.WHITE));  // f2
    }

    // Test invalid King movement (moving into check)
    @Test
    public void testKingInvalidMovesIntoCheck() {
        // White King at (4, 7)
        Point start = new Point(4, 7);
        Piece king = board.getPieceAt(start.getX(), start.getY());

        // Simulate an attacking Black Rook on (4, 4)
        board.placePiece(new Rook(Color.BLACK), new Point(4, 4));  // Rook at (4, 4)

        // Moving to e2 would place the King in check from the Rook
        assertFalse(king.isValidMove(start, new Point(4, 6), board, Color.WHITE));  // e2
    }

    // Test castling (valid case)
    @Test
    public void testKingValidCastling() {
        // Clear pieces between King and Rook for castling
        board.clearSquare(new Point(5, 7));  // f1
        board.clearSquare(new Point(6, 7));  // g1

        Point kingStart = new Point(4, 7);  // e1
        Piece king = board.getPieceAt(kingStart.getX(), kingStart.getY());

        // Assume no squares between King and Rook are under attack, and Rook is at (7, 7)
        assertTrue(king.isValidMove(kingStart, new Point(6, 7), board, Color.WHITE));  // Castling (King side)
    }

    // Test invalid castling (King has moved)
    @Test
    public void testKingInvalidCastlingDueToKingMovement() {
        Point kingStart = new Point(4, 7);
        King king = (King) board.getPieceAt(kingStart.getX(), kingStart.getY());

        // Simulate King moving previously
         king.setHasMoved();  // King has moved

        // Now castling should be invalid
        assertFalse(king.isValidMove(kingStart, new Point(6, 7), board, Color.WHITE));  // Castling
    }

    // Test isSquareAttacked method
    @Test
    public void testIsSquareAttacked() {
        // Place a Black Rook at (4, 4)
        board.placePiece(new Rook(Color.BLACK), new Point(4, 4));

        // Check if square (4, 7) (e1) is attacked by the Rook
        Point square = new Point(4, 7);
        assertTrue(board.isSquareAttacked(square, Color.WHITE));  // e1 is attacked by Rook

        // Check if square (5, 7) (f1) is not attacked by the Rook
        square = new Point(5, 7);
        assertFalse(board.isSquareAttacked(square, Color.WHITE));  // f1 is not attacked
    }
}
