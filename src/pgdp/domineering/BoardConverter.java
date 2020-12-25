package pgdp.domineering;

public class BoardConverter {
    public static String boardToString (char[][] board) {
        /* Converts a board to a String that can be stored easily. */
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < board[0].length; j++) {
            for (int i = 0; i < board.length; i++) {
                stringBuilder.append(board[i][j]);
            }
            stringBuilder.append(';');
        }
        return stringBuilder.toString();
    }

    public static char[][] stringToBoard(String s) {
        /*
        Converts a String s to a board array. The String is seperated per Row by ';' but the array is in columns.
        */

        String[] rows = s.split(";");
        int num_rows = rows.length;
        int num_columns = rows[0].length();

        for (int i = 0; i < num_rows; i++) {
            if (rows[i].length() != num_columns) {
               System.out.println("Not every row has the same length.");
               return null;
            }
        }

        char[][] board = new char[num_columns][];

        for (int i = 0; i < num_columns; i++) {
            board[i] = new char[num_rows];
            for (int j = 0; j < num_rows; j++) {
                board[i][j] = rows[j].charAt(i);
            }
        }
        return board;
    }
}
