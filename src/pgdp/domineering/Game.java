package pgdp.domineering;

public class Game {

    private AI verticalAI;
    private AI horizontalAI;
    private Mode mode;
    private AI winner;

    public Game(AI verticalAI, AI horizontalAI, Mode mode) {
        this.verticalAI = verticalAI;
        this.horizontalAI = horizontalAI;
        this.mode = mode;
        this.winner = null;
    }

    public void runGame() {

        // Generate an empty board and the players
        char[][] board = generateBoard(13, 13);
        Player playerVertical = Player.V;
        Player playerHorizontal = Player.H;


        while (true) {
            // First player plays vertically
            if (countPossibleMoves(board, playerVertical) > 0) {
                Coordinate moveVertical = verticalAI.playMove(board, playerVertical, mode);

                if (movePossible(board, moveVertical, playerVertical))
                    makeMove(board, moveVertical, playerVertical);
                else {
                    this.winner = horizontalAI;
                    return;
                }

            } else {
                this.winner = horizontalAI;
                return;
            }

            GameVisualizer.printBoard(board);

            // second player plays horizontally
            if (countPossibleMoves(board, playerHorizontal) > 0) {
                Coordinate moveHorizontal = horizontalAI.playMove(board, playerHorizontal, mode);

                if (movePossible(board, moveHorizontal, playerHorizontal))
                    makeMove(board, moveHorizontal, playerHorizontal);
                else {
                    this.winner = verticalAI;
                    return;
                }

            } else {
                this.winner = verticalAI;
                return;
            }

            GameVisualizer.printBoard(board);
        }
    }

    public AI getWinner() {
        return winner;
    }

    public static void makeMove(char[][] board, Coordinate move, Player player) {
        // Note: Always check if a move is possible before making a move
        char c;
        if (player == Player.V) c = 'V';
        else c = 'H';

        board[move.getX()][move.getY()] = c;
        Coordinate secondCoordinate = getSecondCoordiante(move, player);
        board[secondCoordinate.getX()][secondCoordinate.getY()] = c;
    }

    public static boolean movePossible(char[][] board, Coordinate move, Player player) {
        int width = board.length;
        int height = board[0].length;

        // check if first coordinate is out of bounds or already taken
        if (move.getX() >= width || move.getY() >= height || move.getX() < 0 || move.getY() < 0 ||
                board[move.getX()][move.getY()] != 'E')
            return false;

        // check if second coordinate is out of bounds or already taken
        Coordinate secondCoordinate = getSecondCoordiante(move, player);
        if (secondCoordinate.getX() >= width || secondCoordinate.getY() >= height || secondCoordinate.getX() < 0
                || secondCoordinate.getY() < 0 || board[secondCoordinate.getX()][secondCoordinate.getY()] != 'E')
            return false;

        return true;
    }

    public static int countPossibleMoves(char[][] board, Player player) {
        int width = board.length;
        int height = board[0].length;
        int count = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (movePossible(board, new Coordinate(i, j), player)) count++;
            }
        }
        return count;
    }

    public static Coordinate getSecondCoordiante(Coordinate move, Player player) {
        // get the second coordinate
        Coordinate secondCoordinate;
        if (player == Player.V)
            secondCoordinate = new Coordinate(move.getX(), move.getY() + 1);
        else
            secondCoordinate = new Coordinate(move.getX() + 1, move.getY());

        return secondCoordinate;
    }

    public static char[][] generateBoard(int width, int height) {
        // generate a new empty board
        char[][] board = new char[width][];

        for (int i = 0; i < width; i++) {
            board[i] = new char[height];
            for (int j = 0; j < height; j++) {
                board[i][j] = 'E';
            }
        }

        return board;
    }
}