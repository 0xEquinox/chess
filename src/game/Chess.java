package game;

import game.util.Color;
import game.util.InputParser;
import game.util.Point;
import pieces.King;

import java.util.Scanner;

/**
 * The Chess class represents the game logic for a chess match. It extends the AbstractStrategyGame class
 * and implements the required methods to provide instructions, make moves, and determine the winner.
 */
public class Chess extends AbstractStrategyGame {
    private final Board board;  // The chess board where the game takes place
    private Color currentPlayer;  // The color of the player whose turn it is

    /**
     * Initializes a new Chess game with the board set up and White set to move first.
     */
    public Chess() {
        this.board = new Board();
        this.currentPlayer = Color.WHITE;  // White always starts the game
    }

    /**
     * Provides instructions on how to play the game.
     *
     * @return a string containing the instructions for making a move
     */
    @Override
    public String instructions() {
        return "Enter a move in the form \"e2-e4\". For example, e2-e4 would be a valid move.";
    }

    /**
     * Returns a string representation of the current state of the chess board.
     *
     * @return the string representation of the chess board
     */
    @Override
    public String toString() {
        return board.toString();
    }

    /**
     * Checks the current state of the game to determine if either player has won by checkmate.
     *
     * @return 1 if White has won, 2 if Black has won, or -1 if no winner has been determined yet
     */
    @Override
    public int getWinner() {
        // Retrieve both kings from the board
        King[] kings = board.getKings();
        for (King king : kings) {
            if (king.isInCheckmate(board)) {
                // Return 2 if White's king is checkmated, 1 if Black's king is checkmated
                return king.getColor() == Color.WHITE ? 2 : 1;
            }
        }
        return -1;  // No winner yet
    }

    /**
     * Determines which player goes next based on the current player.
     *
     * @return 1 if it's White's turn, 2 if it's Black's turn
     */
    @Override
    public int getNextPlayer() {
        return currentPlayer == Color.WHITE ? 1 : 2;
    }

    /**
     * Reads a move from the input and makes the move on the board.
     *
     * @param input a Scanner object to read the player's move input
     * @throws IllegalArgumentException if the move is invalid
     */
    @Override
    public void makeMove(Scanner input) throws IllegalArgumentException {
        // Get the move from the player (e.g., "e2-e4")
        String move = input.nextLine();
        // Parse the move into a starting and ending point
        Point[] points = InputParser.parseMove(move);

        // Make the move on the board
        board.movePiece(points[0], points[1], currentPlayer);

        // Switch to the next player after the move
        currentPlayer = getNextPlayer() == 1 ? Color.BLACK : Color.WHITE;
    }
}
