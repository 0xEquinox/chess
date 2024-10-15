package pieces;

import game.Board;
import game.Piece;
import game.util.Color;
import game.util.MoveUtils;
import game.util.Point;

public class Rook extends Piece {
    private boolean hasMoved;

    public Rook(Color color) {
        super(color);
        this.hasMoved = false;
    }

    @Override
    public boolean isValidMove(Point from, Point to, Board board, Color color) {
       return MoveUtils.isHorizontalClear(from.getX(), from.getY(), to.getX(), to.getY(), board)
               || MoveUtils.isVerticalClear(from.getX(), from.getY(), to.getX(), to.getY(), board);
    }

    @Override
    public char getSymbol() {
        if (getColor() == Color.WHITE) {
            return '♖';
        }
        return '♜';
    }

    public void setHasMoved() {
        this.hasMoved = true;
    }

    public boolean hasMoved() {
        return this.hasMoved;
    }
}
