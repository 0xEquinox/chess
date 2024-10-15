package game.util;

public enum Color {
    WHITE,
    BLACK;

    public String toString() {
        return this == WHITE ? "white" : "black";
    }
}
