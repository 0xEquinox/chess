package game.util;

public class InputParser {

    // Given a string that looks like "e2 e4", return the row and column
    // Return the from row and column as well as the to row and column like (2, 4) (4, 2)
    public static Point[] parseMove(String input) {
        String[] moves = input.split(" ");

        Point[] points = new Point[2]; // from and to

        for (int i = 0; i < 2; i++) {
            String move = moves[i];

            // Get the row and column
            int row = Integer.parseInt(move.substring(1, 2)) - 1;
            int col = charToInt(move.charAt(0));

            // The board is flipped from it's representation, which means that black starts at (0, 0) and white starts at (7, 7)
            // So we need to make sure that when someone enters e2 e4 that translates not to (4, 1) like it would if the board wasn't flipped
            // But rather to (4, 6) which is the correct representation
            row = 7 - row;

            points[i] = new Point(col, row);
        }

        return points;
    }

    private static int charToInt(char c) {
        return c - 'a';
    }
}
