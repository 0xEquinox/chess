package game;

import game.util.Color;
import game.util.Point;

public abstract class Piece {
    private Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public abstract boolean isValidMove(Point from, Point to, Board board, Color color);

    public abstract char getSymbol();
}
