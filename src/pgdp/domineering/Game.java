package pgdp.domineering;

import java.util.ArrayList;
import java.util.List;

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
            GameVisualizer.printBoard(board);

            // First player plays vertically
            if (getMobility(board, playerVertical) > 0) {
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
            if (getMobility(board, playerHorizontal) > 0) {
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
        Coordinate secondCoordinate = getSecondCoordinate(move, player);
        board[secondCoordinate.getX()][secondCoordinate.getY()] = c;
    }

    public static char[][] makeMoveAndCopyBoard(char[][] boardToCopy, Coordinate move, Player player) {
        // Note: Always check if a move is possible before making a move

        // copy the board first
        char[][] board = new char[boardToCopy.length][];
        for(int i = 0; i < boardToCopy.length; i++)
            board[i] = boardToCopy[i].clone();

        char c;
        if (player == Player.V) c = 'V';
        else c = 'H';

        board[move.getX()][move.getY()] = c;
        Coordinate secondCoordinate = getSecondCoordinate(move, player);
        board[secondCoordinate.getX()][secondCoordinate.getY()] = c;

        return board;
    }

    public static boolean movePossible(char[][] board, Coordinate move, Player player) {
        int width = board.length;
        int height = board[0].length;

        // check if first coordinate is out of bounds or already taken
        if (move.getX() >= width || move.getY() >= height || move.getX() < 0 || move.getY() < 0 ||
                board[move.getX()][move.getY()] != 'E')
            return false;

        // check if second coordinate is out of bounds or already taken
        Coordinate secondCoordinate = getSecondCoordinate(move, player);
        if (secondCoordinate.getX() >= width || secondCoordinate.getY() >= height || secondCoordinate.getX() < 0
                || secondCoordinate.getY() < 0 || board[secondCoordinate.getX()][secondCoordinate.getY()] != 'E')
            return false;

        return true;
    }

    public static int getMobility(char[][] board, Player player) {
        // Mobility: The number of distinct moves that a player can make in a position
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

    public static int getRealMoves(char[][] board, Player player) {
        // Real Moves: The maximum number of moves that a player can make in a position
        int width = board.length;
        int height = board[0].length;
        int moves_count = 0;

        // order of the loops changes depending on the player
        if (player == Player.V) {
            // for the vertical player we iterate over the columns
            for (int i = 0; i < width; i++) {
                int tile_count = 0;
                // iterate through all the tiles in the column
                for (int j = 0; j < height; j++) {
                    if (board[i][j] == 'E')
                        tile_count++;
                    else {
                        // if the chunk has ended add to moves
                        moves_count += tile_count / 2;
                        tile_count = 0;
                    }
                }
                moves_count += tile_count / 2;
            }
        } else {
            // for the horizontal player we iterate over the rows
            for (int j = 0; j < height; j++) {
                int tile_count = 0;
                // iterate through all the tiles in the row
                for (int i = 0; i < width; i++) {
                    if (board[i][j] == 'E')
                        tile_count++;
                    else {
                        // if the chunk has ended add to moves
                        moves_count += tile_count / 2;
                        tile_count = 0;
                    }
                }
                moves_count += tile_count / 2;
            }
        }
        return moves_count;
    }

    public static int getSafeMoves (char[][] board, Player player) {
       /*
       The maximum number of moves that a player can make from a given position in the remaining part of the game,
       irrespective of the moves that the opponent will make.

       Implementation like real moves but with an additonal check if the a tile can't be touched by the other player
        */
        int width = board.length;
        int height = board[0].length;
        int moves_count = 0;

        // order of the loops changes depending on the player
        if (player == Player.V) {
            // for the vertical player we iterate over the columns
            for (int i = 0; i < width; i++) {
                int tile_count = 0;
                // iterate through all the tiles in the column
                for (int j = 0; j < height; j++) {
                    if (isSafeTile(board, new Coordinate(i, j), player))
                        tile_count++;
                    else {
                        // if the chunk has ended add to moves
                        moves_count += tile_count / 2;
                        tile_count = 0;
                    }
                }
                moves_count += tile_count / 2;
            }
        } else {
            // for the horizontal player we iterate over the rows
            for (int j = 0; j < height; j++) {
                int tile_count = 0;
                // iterate through all the tiles in the row
                for (int i = 0; i < width; i++) {
                    if (isSafeTile(board, new Coordinate(i, j), player))
                        tile_count++;
                    else {
                        // if the chunk has ended add to moves
                        moves_count += tile_count / 2;
                        tile_count = 0;
                    }
                }
                moves_count += tile_count / 2;
            }
        }
        return moves_count;
    }

    public static boolean isSafeTile(char[][] board, Coordinate tile, Player player) {
        /*
        Checks if a tile can't be touched by the other player.
        (if the two adjacent on the other players axis are blocked)
        */
        int width = board.length;
        int height = board[0].length;

        // check that the tile is empty
        if (board[tile.getX()][tile.getY()] != 'E')
            return false;

        if (player == Player.V) {
            // then check the left tile
            if (tile.getX() - 1 >= 0) {
                if(board[tile.getX() - 1][tile.getY()] == 'E')
                    return false;
            }
            // and the right tile
            if(tile.getX() + 1 <= width - 1) {
                if(board[tile.getX() + 1][tile.getY()] == 'E')
                    return false;
            }
        } else {
            // check top tile
            if(tile.getY() - 1 >= 0) {
                if(board[tile.getX()][tile.getY() - 1] == 'E')
                    return false;
            }
            // check bottom tile
            if(tile.getY() + 1 <= height - 1) {
                if(board[tile.getX()][tile.getY() + 1] == 'E')
                    return false;
            }
        }
        return true;
    }

    public static Coordinate[] getAllPossibleMoves(char[][] board, Player player) {
        int width = board.length;
        int height = board[0].length;
        List<Coordinate> list = new ArrayList<Coordinate>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (movePossible(board, new Coordinate(i, j), player)) list.add(new Coordinate(i, j));
            }
        }

        return list.toArray(new Coordinate[0]);
    }


    public static Coordinate getSecondCoordinate(Coordinate move, Player player) {
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