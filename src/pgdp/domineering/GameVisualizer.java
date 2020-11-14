package pgdp.domineering;

import java.util.Arrays;

public class GameVisualizer {

    public static void main(String[] args) {
        // Copy String of moves from the test in here:
        // Example: String movesFromTest = "[(12,4), (0,0), (0,8), (0,0)]";
        String movesFromTest = "[(12,4), (0,0), (0,8), (0,0)]";

        // Parsing of the string to an array of coordinates
        movesFromTest = movesFromTest.substring(1, movesFromTest.length() - 1);
        String[] movesString = movesFromTest.split(", ");
        Coordinate[] moves = new Coordinate[movesString.length];
        for (int i = 0; i < movesString.length; i++) {
            String[] coordinates = movesString[i].split(",");
            int x = Integer.parseInt(coordinates[0].substring(1));
            int y = Integer.parseInt(coordinates[1].substring(0, coordinates[1].length() - 1));
            moves[i] = new Coordinate(x, y);
        }

        // Create empty board
        char[][] board = new char[13][13];
        for (char[] line : board) {
            Arrays.fill(line, 'E');
        }

        // Recreate every move and print out the board
        char currentChar = 'V';
        for (Coordinate move : moves) {
            try {
                board[move.getX()][move.getY()] = currentChar;
                board[move.getX() + (currentChar == 'H' ? 1 : 0)][move.getY()
                        + (currentChar == 'V' ? 1 : 0)] = currentChar;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("The move " + move + " wasn't (fully) on the board");
                return;
            }
            printBoard(board);
            currentChar = currentChar == 'H' ? 'V' : 'H';
        }
    }

    private static void printBoard(char[][] board) {
        System.out.println("  0 1 2 3 4 5 6 7 8 9 a b c");
        System.out.println(" ┏━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┓");
        for (int y = 0; y < board.length; y++) {
            System.out.print(Integer.toHexString(y) + "┃");
            for (int x = 0; x < board.length; x++) {
                if (board[x][y] == 'V' || board[x][y] == 'H') {
                    System.out.print(board[x][y] + "┃");
                } else {
                    System.out.print(" ┃");
                }
                // System.out.print(board[x][y] + "┃");
            }
            System.out.println();
            if (y != board.length - 1) {
                System.out.println(" ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫");
            }
        }
        System.out.println(" ┗━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┛");
    }
}
