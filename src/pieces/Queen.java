package pieces;

import game.Board;
import game.Piece;
import game.util.Color;
import game.util.MoveUtils;
import game.util.Point;

public class Queen extends Piece {
    public Queen(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Point from, Point to, Board board, Color color) {
        return MoveUtils.isDiagonalClear(from.getX(), from.getY(), to.getX(), to.getY(), board)
                || MoveUtils.isHorizontalClear(from.getX(), from.getY(), to.getX(), to.getY(), board)
                || MoveUtils.isVerticalClear(from.getX(), from.getY(), to.getX(), to.getY(), board);
    }

    @Override
    public char getSymbol() {
        if (getColor() == Color.WHITE) {
            return '♕';
        }
        return '♛';
    }
}
