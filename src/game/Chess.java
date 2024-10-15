package game;

import game.util.Color;
import game.util.InputParser;
import game.util.Point;
import pieces.King;

import java.util.Scanner;

public class Chess extends AbstractStrategyGame {
    private final Board board;
    private Color currentPlayer;

    public Chess() {
        this.board = new Board();
        this.currentPlayer = Color.WHITE;
    }

    @Override
    public String instructions() {
        return "Enter a move in the form \"e2-e4\". For example, e2-e4 would be a valid move.";
    }

    @Override
    public String toString() {
        return board.toString();
    }

    @Override
    public int getWinner() {
        // Get each king and check if it is in checkmate
        King[] kings = board.getKings();
        for (King king : kings) {
            if (king.isInCheckmate(board)) {
                return king.getColor() == Color.WHITE ? 1 : 2;
            }
        }
        return -1;
    }

    @Override
    public int getNextPlayer() {
        return currentPlayer == Color.WHITE ? 2 : 1;
    }

    @Override
    public void makeMove(Scanner input) throws IllegalArgumentException {
        String move = input.nextLine();
        Point[] points = InputParser.parseMove(move);

        board.movePiece(points[0], points[1], currentPlayer);

        currentPlayer = getNextPlayer() == 1 ? Color.WHITE : Color.BLACK;
    }
}
