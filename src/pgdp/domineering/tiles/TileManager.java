package pgdp.domineering.tiles;

import pgdp.domineering.Coordinate;
import pgdp.domineering.Game;
import pgdp.domineering.Player;
import pgdp.domineering.Tuple;

public class TileManager {

    public static Tile[][] boardToTileBoard(char[][] board){
        /*
        Convert a board into a tile board.
        */
        Tile[][] tileBoard = new Tile[board.length][];

        // Fill the board
        for (int i = 0; i < board.length; i++) {
            tileBoard[i] = new Tile[board[0].length];
            for (int j = 0; j < board[0].length; j++) {
                // Create the tile
                Coordinate tileCoordinate = new Coordinate(i, j);

                tileBoard[i][j] = TileManager.createTileFromBoardPosition(board, tileCoordinate);
            }
        }
        return tileBoard;
    }

    public static char[][] tileBoardToBoard(Tile[][] tileBoard) {
        char[][] board = new char[tileBoard.length][];

        for (int i = 0; i < tileBoard.length; i++) {
            board[i] = new char[tileBoard[0].length];
            for (int j = 0; j < tileBoard[0].length; j++) {
                board[i][j] = tileBoard[i][j].getTileChar();
            }
        }
        return board;
    }

    public static Tile[][] copyTileBoard(Tile[][] boardToCopy) {
        Tile[][] copy = new Tile[boardToCopy.length][];

        for (int i = 0; i < boardToCopy.length; i++) {
            copy[i] = new Tile[boardToCopy[0].length];
            for (int j = 0; j < boardToCopy[0].length; j++) {
                copy[i][j] = boardToCopy[i][j].copy();
            }
        }
        return copy;
    }

    public static Tuple<char[][], Tile[][]>playMoveAndUpdateBoards(char[][] board, Tile[][] tileBoard, Coordinate move,
                                                            Player player) {
        // Play the move on the normal Board
        board = Game.makeMoveAndCopyBoard(board, move, player);

        // copy the tileBoard too
        tileBoard = TileManager.copyTileBoard(tileBoard);

        // determine all tiles to update
        Coordinate[] coordinatesToUpdate = new Coordinate[8];
        if (player == Player.V) {
            // tile itself
            coordinatesToUpdate[0] = move;
            // one on top; two below
            coordinatesToUpdate[1] = new Coordinate(move.getX(), move.getY() - 1);
            coordinatesToUpdate[2] = new Coordinate(move.getX(), move.getY() + 1);
            coordinatesToUpdate[3] = new Coordinate(move.getX(), move.getY() + 2);
            // tiles to the left and right
            coordinatesToUpdate[4] = new Coordinate(move.getX() - 1, move.getY());
            coordinatesToUpdate[5] = new Coordinate(move.getX() + 1, move.getY());
            // tiles to the left and right one down
            coordinatesToUpdate[6] = new Coordinate(move.getX() - 1, move.getY() + 1);
            coordinatesToUpdate[7] = new Coordinate(move.getX() + 1, move.getY() + 1);
        } else {
            // tile itself
            coordinatesToUpdate[0] = move;
            // one to the left; two to the right
            coordinatesToUpdate[1] = new Coordinate(move.getX() - 1, move.getY());
            coordinatesToUpdate[2] = new Coordinate(move.getX() + 1, move.getY());
            coordinatesToUpdate[3] = new Coordinate(move.getX() + 2, move.getY());
            // tiles to the top and bottom
            coordinatesToUpdate[4] = new Coordinate(move.getX(), move.getY() - 1);
            coordinatesToUpdate[5] = new Coordinate(move.getX(), move.getY() + 1);
            // tiles to the top and bottom
            coordinatesToUpdate[6] = new Coordinate(move.getX() + 1, move.getY() - 1);
            coordinatesToUpdate[7] = new Coordinate(move.getX() + 1, move.getY() + 1);
        }

        // update the determined coordinates
        for (int i = 0; i < coordinatesToUpdate.length; i++) {
            if (coordinateInBounds(tileBoard, coordinatesToUpdate[i])) {
                tileBoard[coordinatesToUpdate[i].getX()][coordinatesToUpdate[i].getY()] =
                        createTileFromBoardPosition(board, coordinatesToUpdate[i]);
            }
        }
        return new Tuple<>(board, tileBoard);
    }

    public static boolean coordinateInBounds(Tile[][] tileBoard, Coordinate coordinate) {
        return coordinate.getX() >= 0 && coordinate.getY() >= 0
                && coordinate.getX() < tileBoard.length && coordinate.getY() < tileBoard[0].length;
    }

    public static Tile createTileFromBoardPosition(char[][] board, Coordinate Position) {
        Tuple<Boolean, Integer> verticalPossibilityEvaluation =
                Game.isPossibilityTile(board, Position, Player.V);
        Tuple<Boolean, Integer> horizontalPossibilityEvaluation =
                Game.isPossibilityTile(board, Position, Player.H);

        return new Tile(
                board[Position.getX()][Position.getY()], board[Position.getX()][Position.getY()] == 'E',
                Game.isSafeTile(board, Position, Player.V),
                Game.isSafeTile(board, Position, Player.H),
                verticalPossibilityEvaluation.x, verticalPossibilityEvaluation.y,
                horizontalPossibilityEvaluation.x, horizontalPossibilityEvaluation.y
        );
    }

    public static int[] getRealMovesSafeMovesAndPossibilitiesVerticalAndHorizontal(Tile[][] tileBoard) {
        /*
        Return all the important data in the following order:
        verticalRealMoves, horizontalRealMoves, verticalSafeMoves, horizontalSafeMoves,
        verticalPossibilities, horizontalPossibilities
        */

        // lets get the vertical counts first ==> iterate over all columns
        int verticalRealMoves = 0;
        int verticalSafeMoves = 0;
        int verticalPossibilities = 0;
        for (int i = 0; i < tileBoard.length; i++) {
            int realTileCount = 0;
            int safeTileCount = 0;
            int possibilityCount = 0;
            int direction = 0;
            for (int j = 0; j < tileBoard[0].length; j++) {
                if (tileBoard[i][j].isEmpty()) {
                    // check if it is an Empty tile
                    realTileCount++;

                    if (tileBoard[i][j].isVerticalSafeTile()) {
                        // check if it is also a safe tile
                        safeTileCount++;

                        verticalPossibilities += possibilityCount / 2;
                        possibilityCount = 0;
                    } else {
                        verticalSafeMoves += safeTileCount / 2;
                        safeTileCount = 0;

                        if (tileBoard[i][j].isVerticalPossibilityTile()) {
                            if(direction == tileBoard[i][j].getVerticalPossibilityDirection()) {
                                possibilityCount++;
                            } else {
                                // wall direction changes (1 because this tile is a possiblity tile)
                                verticalPossibilities += possibilityCount / 2;
                                possibilityCount = 1;
                                direction = tileBoard[i][j].getVerticalPossibilityDirection();
                            }
                        } else {
                            verticalPossibilities += possibilityCount / 2;
                            possibilityCount = 0;
                        }
                    }

                } else {
                    verticalRealMoves += realTileCount / 2;
                    realTileCount = 0;

                    verticalSafeMoves += safeTileCount / 2;
                    safeTileCount = 0;

                    verticalPossibilities += possibilityCount / 2;
                    possibilityCount = 0;
                }
            }
            verticalRealMoves += realTileCount / 2;
            verticalSafeMoves += safeTileCount / 2;
            verticalPossibilities += possibilityCount / 2;
        }

        // And all again for the horizontal direction
        int horizontalRealMoves = 0;
        int horizontalSafeMoves = 0;
        int horizontalPossibilities = 0;
        for (int j = 0; j < tileBoard[0].length; j++) {
            int realTileCount = 0;
            int safeTileCount = 0;
            int possibilityCount = 0;
            int direction = 0;
            for (int i = 0; i < tileBoard.length; i++) {
                if (tileBoard[i][j].isEmpty()) {
                    // check if it is an Empty tile
                    realTileCount++;

                    if (tileBoard[i][j].isHorizontalSafeTile()) {
                        // check if it is also a safe tile
                        safeTileCount++;

                        horizontalPossibilities += possibilityCount / 2;
                        possibilityCount = 0;
                    } else {
                        horizontalSafeMoves += safeTileCount / 2;
                        safeTileCount = 0;

                        if (tileBoard[i][j].isHorizontalPossibilityTile()) {
                            if(direction == tileBoard[i][j].getHorizontalPossibilityDirection()) {
                                possibilityCount++;
                            } else {
                                // wall direction changes (1 because this tile is a possiblity tile)
                                horizontalPossibilities += possibilityCount / 2;
                                possibilityCount = 1;
                                direction = tileBoard[i][j].getHorizontalPossibilityDirection();
                            }
                        } else {
                            horizontalPossibilities += possibilityCount / 2;
                            possibilityCount = 0;
                        }
                    }

                } else {
                    horizontalRealMoves += realTileCount / 2;
                    realTileCount = 0;

                    horizontalSafeMoves += safeTileCount / 2;
                    safeTileCount = 0;

                    horizontalPossibilities += possibilityCount / 2;
                    possibilityCount = 0;
                }
            }
            horizontalRealMoves += realTileCount / 2;
            horizontalSafeMoves += safeTileCount / 2;
            horizontalPossibilities += possibilityCount / 2;
        }

        return new int[]{verticalRealMoves, horizontalRealMoves, verticalSafeMoves,
                horizontalSafeMoves, verticalPossibilities, horizontalPossibilities};
    }
}
