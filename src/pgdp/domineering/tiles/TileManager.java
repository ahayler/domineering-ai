package pgdp.domineering.tiles;

import pgdp.domineering.Coordinate;
import pgdp.domineering.Game;
import pgdp.domineering.Player;
import pgdp.domineering.Tuple;

import java.util.ArrayList;
import java.util.List;

public class TileManager {

    public static Tile[][] boardToTileBoard(char[][] board) {
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

    public static Tile[][] makeMoveAndCopyBoard(Tile[][] boardToCopy, Coordinate move, Player player) {
        // Note: Always check if a move is possible before making a move. Updates are included

        // copy the board first
        Tile[][] tileBoard = copyTileBoard(boardToCopy);

        char c;
        if (player == Player.V) c = 'V';
        else c = 'H';

        tileBoard[move.getX()][move.getY()].setTileChar(c);
        Coordinate secondCoordinate = Game.getSecondCoordinate(move, player);
        tileBoard[secondCoordinate.getX()][secondCoordinate.getY()].setTileChar(c);

        // determine all tiles to update
        Coordinate[] coordinatesToUpdate = getCoordinatesToUpdate(move, player);

        // update the determined coordinates
        for (int i = 0; i < coordinatesToUpdate.length; i++) {
            if (coordinateInBounds(tileBoard, coordinatesToUpdate[i])) {
                tileBoard[coordinatesToUpdate[i].getX()][coordinatesToUpdate[i].getY()] =
                        createUpdatedTile(tileBoard, coordinatesToUpdate[i]);
            }
        }


        return tileBoard;
    }

    public static Tuple<char[][], Tile[][]> playMoveAndUpdateBoard(char[][] board, Tile[][] tileBoard, Coordinate move,
                                                                   Player player) {
        // Play the move on the normal Board
        board = Game.makeMoveAndCopyBoard(board, move, player);

        // copy the tileBoard too
        tileBoard = TileManager.copyTileBoard(tileBoard);

        // determine all tiles to update
        Coordinate[] coordinatesToUpdate = getCoordinatesToUpdate(move, player);

        // update the determined coordinates
        for (int i = 0; i < coordinatesToUpdate.length; i++) {
            if (coordinateInBounds(tileBoard, coordinatesToUpdate[i])) {
                tileBoard[coordinatesToUpdate[i].getX()][coordinatesToUpdate[i].getY()] =
                        createTileFromBoardPosition(board, coordinatesToUpdate[i]);
            }
        }
        return new Tuple<>(board, tileBoard);
    }

    public static Tuple<char[][], Tile[][]> playMoveAndUpdateBoards(char[][] board, Tile[][] tileBoard, Coordinate move,
                                                                    Player player) {
        // Play the move on the normal Board
        board = Game.makeMoveAndCopyBoard(board, move, player);

        // copy the tileBoard too
        tileBoard = TileManager.copyTileBoard(tileBoard);

        // determine all tiles to update
        Coordinate[] coordinatesToUpdate = getCoordinatesToUpdate(move, player);

        // update the determined coordinates
        for (int i = 0; i < coordinatesToUpdate.length; i++) {
            if (coordinateInBounds(tileBoard, coordinatesToUpdate[i])) {
                tileBoard[coordinatesToUpdate[i].getX()][coordinatesToUpdate[i].getY()] =
                        createTileFromBoardPosition(board, coordinatesToUpdate[i]);
            }
        }
        return new Tuple<>(board, tileBoard);
    }

    public static Coordinate[] getCoordinatesToUpdate(Coordinate move, Player player) {
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
        return coordinatesToUpdate;
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

    public static Tile createUpdatedTile(Tile[][] tileBoard, Coordinate Position) {
        Tuple<Boolean, Integer> verticalPossibilityEvaluation = isPossibilityTile(tileBoard, Position, Player.V);
        Tuple<Boolean, Integer> horizontalPossibilityEvaluation =
                isPossibilityTile(tileBoard, Position, Player.H);

        return new Tile(
                tileBoard[Position.getX()][Position.getY()].getTileChar(),
                tileBoard[Position.getX()][Position.getY()].getTileChar() == 'E',
                TileManager.isSafeTile(tileBoard, Position, Player.V),
                TileManager.isSafeTile(tileBoard, Position, Player.H),
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
                            if (direction == tileBoard[i][j].getVerticalPossibilityDirection()) {
                                possibilityCount++;
                            } else {
                                // wall direction changes (1 because this tile is a possibility tile)
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
                            if (direction == tileBoard[i][j].getHorizontalPossibilityDirection()) {
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

    public static Coordinate[] getAllPossibleMoves(Tile[][] tileBoard, Player player) {
        int width = tileBoard.length;
        int height = tileBoard[0].length;
        List<Coordinate> list = new ArrayList<Coordinate>();

        int[] orderArray = new int[]{1, 11, 2, 10, 3, 9, 4, 8, 5, 7, 6, 0, 12};

        if (false) { //width == 13 && height == 13
            if (player == Player.V) {
                // x stays fixed at the optimized position and y is changing
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        if (movePossible(tileBoard, new Coordinate(orderArray[i], j), player))
                            list.add(new Coordinate(orderArray[i], j));
                    }
                }
            } else {
                // y stays fixed at the optimized position and x is changing
                for (int j = 0; j < height; j++) {
                    for (int i = 0; i < width; i++) {
                        if (movePossible(tileBoard, new Coordinate(i, orderArray[j]), player))
                            list.add(new Coordinate(i, orderArray[j]));
                    }
                }
            }
        } else {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (movePossible(tileBoard, new Coordinate(i, j), player)) list.add(new Coordinate(i, j));
                }
            }
        }


        return list.toArray(new Coordinate[0]);
    }

    public static boolean movePossible(Tile[][] tileBoard, Coordinate move, Player player) {
        int width = tileBoard.length;
        int height = tileBoard[0].length;

        // check if first coordinate is out of bounds or already taken
        if (move.getX() >= width || move.getY() >= height || move.getX() < 0 || move.getY() < 0 ||
                tileBoard[move.getX()][move.getY()].getTileChar() != 'E')
            return false;

        // check if second coordinate is out of bounds or already taken
        Coordinate secondCoordinate = Game.getSecondCoordinate(move, player);
        if (secondCoordinate.getX() >= width || secondCoordinate.getY() >= height || secondCoordinate.getX() < 0
                || secondCoordinate.getY() < 0 ||
                tileBoard[secondCoordinate.getX()][secondCoordinate.getY()].getTileChar() != 'E')
            return false;

        return true;
    }

    public static boolean isSafeMove(Tile[][] tileBoard, Coordinate move, Player player) {
        /*
        This function is mainly used for safe move pruning.
        We assume that any move proposed is at least a move within the bounds of the board.
        */
        if (player == Player.V) {
            return isSafeTile(tileBoard, move, player) &&
                    isSafeTile(tileBoard, new Coordinate(move.getX(), move.getY() + 1), player);
        } else {
            return isSafeTile(tileBoard, move, player) &&
                    isSafeTile(tileBoard, new Coordinate(move.getX() + 1, move.getY()), player);
        }
    }

    public static boolean isSafeTile(Tile[][] tileBoard, Coordinate tile, Player player) {
        /*
        Checks if a tile can't be touched by the other player.
        (if the two adjacent on the other players axis are blocked)
        */
        int width = tileBoard.length;
        int height = tileBoard[0].length;

        // check that the tile is empty
        if (tileBoard[tile.getX()][tile.getY()].getTileChar() != 'E')
            return false;

        if (player == Player.V) {
            // then check the left tile
            if (tile.getX() - 1 >= 0) {
                if (tileBoard[tile.getX() - 1][tile.getY()].getTileChar() == 'E')
                    return false;
            }
            // and the right tile
            if (tile.getX() + 1 <= width - 1) {
                if (tileBoard[tile.getX() + 1][tile.getY()].getTileChar() == 'E')
                    return false;
            }
        } else {
            // check top tile
            if (tile.getY() - 1 >= 0) {
                if (tileBoard[tile.getX()][tile.getY() - 1].getTileChar() == 'E')
                    return false;
            }
            // check bottom tile
            if (tile.getY() + 1 <= height - 1) {
                if (tileBoard[tile.getX()][tile.getY() + 1].getTileChar() == 'E')
                    return false;
            }
        }
        return true;
    }

    public static Tuple<Boolean, Integer> isPossibilityTile(Tile[][] tileBoard, Coordinate tile, Player player) {
        /*
        Returns whether a tile is a SafeMovePossibility-Tile and the direction of the wall.
        1 is left/up and -1 right/down.
        */
        int direction = 0;
        int width = tileBoard.length;
        int height = tileBoard[0].length;

        // check that the tile is empty
        if (tileBoard[tile.getX()][tile.getY()].getTileChar() != 'E')
            return new Tuple<Boolean, Integer>(false, 0);

        int tileCount = 0;

        if (player == Player.V) {
            // then check the left tile
            if (tile.getX() - 1 >= 0) {
                if (tileBoard[tile.getX() - 1][tile.getY()].getTileChar() != 'E')
                    tileCount++;
                direction = 1;
            } else {
                tileCount++;
                direction = 1;
            }
            // and the right tile
            if (tile.getX() + 1 <= width - 1) {
                if (tileBoard[tile.getX() + 1][tile.getY()].getTileChar() != 'E') {
                    tileCount++;
                    direction = -1;
                }
            } else {
                tileCount++;
                direction = -1;
            }
        } else {
            // check top tile
            if (tile.getY() - 1 >= 0) {
                if (tileBoard[tile.getX()][tile.getY() - 1].getTileChar() != 'E') {
                    tileCount++;
                    direction = 1;
                }
            } else {
                tileCount++;
                direction = 1;
            }
            // check bottom tile
            if (tile.getY() + 1 <= height - 1) {
                if (tileBoard[tile.getX()][tile.getY() + 1].getTileChar() != 'E') {
                    tileCount++;
                    direction = -1;
                }
            } else {
                tileCount++;
                direction = -1;
            }
        }
        return new Tuple<Boolean, Integer>(tileCount == 1, direction);
    }

    public static boolean[] isSMCM(Tile[][] tileBoard, Coordinate move, Player player) {
        /*
        SMCM = Safe Move Creation Move ==> A move that creates a safe move.

        0 = isSMCM
        1 = for Sure
        */
        /*
        TODO: Adjust for only fake safe moves remaining.
         */

        if (player == Player.V) {
            //check if there is are safe move possible to the left
            if (move.getX() - 1 >= 0) {
                // check upper tile first
                if (tileBoard[move.getX() - 1][move.getY()].isVerticalPossibilityTile()) {
                    // safe move to the direct left
                    if (move.getY() + 1 < tileBoard[0].length &&
                            tileBoard[move.getX() - 1][move.getY() + 1].isVerticalPossibilityTile()) {
                        return new boolean[]{true, true};
                    }
                    // safe move to the upper left
                    else if (move.getY() - 1 > 0 &&
                            tileBoard[move.getX() - 1][move.getY() - 1].isVerticalSafeTile())
                        return new boolean[]{true, false};
                    // safe move to the lower left
                } else if (move.getY() + 2 < tileBoard[0].length &&
                        tileBoard[move.getX() - 1][move.getY() + 1].isVerticalPossibilityTile() &&
                        tileBoard[move.getX() - 1][move.getY() + 2].isVerticalSafeTile()) {
                    return new boolean[]{true, false};
                }
            }

            //check if there is a safe move possible to the right
            if (move.getX() + 1 < tileBoard.length) {
                // check upper right first
                if (tileBoard[move.getX() + 1][move.getY()].isVerticalPossibilityTile()) {
                    // safe move to the direct right
                    if (move.getY() + 1 < tileBoard[0].length &&
                            tileBoard[move.getX() + 1][move.getY() + 1].isVerticalPossibilityTile()) {
                        return new boolean[]{true, true};
                    }
                    // safe move to the upper right
                    else if (move.getY() - 1 > 0 && tileBoard[move.getX() + 1][move.getY() - 1].isVerticalSafeTile())
                        return new boolean[]{true, false};
                    // safe move to the lower right
                } else if (move.getY() + 2 < tileBoard[0].length &&
                        tileBoard[move.getX() + 1][move.getY() + 1].isVerticalPossibilityTile() &&
                        tileBoard[move.getX() + 1][move.getY() + 2].isVerticalSafeTile()) {
                    return new boolean[]{true, false};
                }
            }
        } else {
            //check if there is are safe move possible to the top
            if (move.getY() - 1 >= 0) {
                // check left tile first
                if (tileBoard[move.getX()][move.getY() - 1].isHorizontalPossibilityTile()) {
                    // safe move directly atop
                    if (move.getX() + 1 < tileBoard.length &&
                            tileBoard[move.getX() + 1][move.getY() - 1].isHorizontalPossibilityTile()) {
                        return new boolean[]{true, true};
                    }
                    // safe move to the top left
                    else if (move.getX() - 1 > 0 &&
                            tileBoard[move.getX() - 1][move.getY() - 1].isHorizontalSafeTile())
                        return new boolean[]{true, false};
                    // safe move to top right
                } else if (move.getX() + 2 < tileBoard.length &&
                        tileBoard[move.getX() + 1][move.getY() - 1].isHorizontalPossibilityTile() &&
                        tileBoard[move.getX() + 2][move.getY() - 1].isHorizontalSafeTile()) {
                    return new boolean[]{true, false};
                }
            }

            //check if there is a safe move possible to the bottom
            if (move.getY() + 1 < tileBoard[0].length) {
                // check the lower left tile first
                if (tileBoard[move.getX()][move.getY() + 1].isHorizontalPossibilityTile()) {
                    // safe move to the direct bottom
                    if (move.getX() + 1 < tileBoard.length &&
                            tileBoard[move.getX() + 1][move.getY() + 1].isHorizontalPossibilityTile()) {
                        return new boolean[]{true, true};
                    }
                    // safe move to the bottom left
                    else if (move.getX() - 1 > 0 && tileBoard[move.getX() - 1][move.getY() + 1].isHorizontalSafeTile())
                        return new boolean[]{true, false};
                    // safe move to the bottom right
                } else if (move.getX() + 2 < tileBoard.length &&
                        tileBoard[move.getX() + 1][move.getY() + 1].isHorizontalPossibilityTile() &&
                        tileBoard[move.getX() + 2][move.getY() + 1].isHorizontalSafeTile()) {
                    return new boolean[]{true, false};
                }
            }
        }

        return new boolean[]{false, false};
    }

    public static Tuple<List<Coordinate>, Integer> getAllSMCMoves(Tile[][] tileBoard, Player player) {
        /*
        Return the moves and if it is sure to contain a SMC.
        */
        int width = tileBoard.length;
        int height = tileBoard[0].length;
        List<Coordinate> list = new ArrayList<Coordinate>();
        int guaranteeSMCCount = 0;

        int[] orderArray = new int[]{1, 11, 2, 10, 3, 9, 4, 8, 5, 7, 6, 0, 12};

        if (false) { //width == 13 && height == 13
            if (player == Player.V) {
                // x stays fixed at the optimized position and y is changing
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        boolean[] eval = isSMCM(tileBoard, new Coordinate(orderArray[i], j), player);
                        if (movePossible(tileBoard, new Coordinate(orderArray[i], j), player) && eval[0]) {
                            list.add(new Coordinate(orderArray[i], j));
                            if (eval[1]) guaranteeSMCCount += 1;
                        }
                    }
                }
            } else {
                // y stays fixed at the optimized position and x is changing
                for (int j = 0; j < height; j++) {
                    for (int i = 0; i < width; i++) {
                        boolean[] eval = isSMCM(tileBoard, new Coordinate(i, orderArray[j]), player);

                        if (movePossible(tileBoard, new Coordinate(i, orderArray[j]), player) && eval[0]) {
                            list.add(new Coordinate(i, orderArray[j]));
                            if (eval[1]) guaranteeSMCCount += 1;
                        }

                    }
                }
            }
        } else {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    boolean[] eval = isSMCM(tileBoard, new Coordinate(i, j), player);

                    if (movePossible(tileBoard, new Coordinate(i, j), player) && eval[0]) {
                        list.add(new Coordinate(i, j));
                        if (eval[1]) guaranteeSMCCount += 1;
                    }

                }
            }
        }


        return new Tuple<>(list, guaranteeSMCCount);
    }

    public static List<Coordinate> getAllBlockingMoves(Tile[][] tileBoard, Player player) {
        /* First just does the usual triple block */
        int width = tileBoard.length;
        int height = tileBoard[0].length;
        List<Coordinate> list = new ArrayList<Coordinate>();

        // opponent locks for moves to
        if (player == Player.H) {
            // go through the rows
            for (int j = 0; j < height - 1; j++) {
                boolean startFound = true;
                int startX = -1;
                for (int i = 0; i < width; i++) {
                    // disruption found?
                    if (tileBoard[i][j].getTileChar() != 'E'
                            || tileBoard[i][j + 1].getTileChar() != 'E') {
                        // 2 wall found?
                        if (tileBoard[i][j].getTileChar() != 'E'
                                && tileBoard[i][j + 1].getTileChar() != 'E') {
                            // length 3, 5 or 7? (==> area found)
                            if (startFound && (i - startX == 4 || i - startX == 6 || i - startX == 8))
                                list.addAll(getMovesInArea(tileBoard, player, startX + 1, j, i - 1, j + 1));

                            // either way new start
                            startFound = true;
                            startX = i;
                        }
                        // disruption but no wall means start lost
                        else {
                            startFound = false;
                        }
                    }
                }
                // length 3, 5 or 7? (==> area found)
                if (startFound && (width - startX == 4 || width - startX == 6 || width - startX == 8))
                    list.addAll(getMovesInArea(tileBoard, player, startX + 1, j, width - 1, j + 1));

            }
        } else {
            // Now searching for move to block H

            // go through the columns
            for(int i = 0; i < width - 1; i++) {
                boolean startFound = true;
                int startY = -1;
                for (int j = 0; j < height; j++) {
                    // disruption found?
                    if (tileBoard[i][j].getTileChar() != 'E'
                            || tileBoard[i + 1][j].getTileChar() != 'E') {
                        // 2 wall found?
                        if (tileBoard[i][j].getTileChar() != 'E'
                                && tileBoard[i + 1][j].getTileChar() != 'E') {
                            // length 3, 5 or 7? (==> area found)
                            if (startFound && (j - startY == 4 || j - startY == 6 || j - startY == 8))
                                list.addAll(getMovesInArea(tileBoard, player, i, startY + 1, i + 1, j - 1));

                            // either way new start
                            startFound = true;
                            startY = j;
                        }
                        // disruption but no wall means start lost
                        else {
                            startFound = false;
                        }
                    }

                }
                // length 3, 5 or 7? (==> area found)
                if (startFound && (height - startY == 4 || height - startY == 6 || height - startY == 8))
                    list.addAll(getMovesInArea(tileBoard, player, i, startY + 1, i + 1, height - 1));
            }
        }
        return list;
    }

    public static List<Coordinate> getMovesInArea(Tile[][] tileBoard, Player player, int x0, int y0, int x1, int y1) {
        List<Coordinate> list = new ArrayList<Coordinate>();
        for (int x = x0; x <= x1; x++) {
            for (int y = y0; y <= y1; y++) {
                if (movePossible(tileBoard, new Coordinate(x, y), player)) list.add(new Coordinate(x, y));
            }
        }
        return list;
    }

    public static List<Coordinate> getBangerMoves(Tile[][] tileBoard, Player player) {
        /*
        A banger move is a playing in the middle of a 7*2 empty tile stretch (with walls on each side).
        Playing it guarantees at least one safe move per turn and often times can only be blocked by expending
        not playing a SMC, thus gaining a one safe move advantage over the opponent.
        */

        int width = tileBoard.length;
        int height = tileBoard[0].length;
        List<Coordinate> list = new ArrayList<Coordinate>();

        // here the player looks for his own moves (not for blocking moves)
        if (player == Player.V) {
            // go through the rows
            for (int j = 0; j < height - 1; j++) {
                boolean startFound = true;
                int startX = -1;
                for (int i = 0; i < width; i++) {
                    // disruption found?
                    if (tileBoard[i][j].getTileChar() != 'E'
                            || tileBoard[i][j + 1].getTileChar() != 'E') {
                        // 2 wall found?
                        if (tileBoard[i][j].getTileChar() != 'E'
                                && tileBoard[i][j + 1].getTileChar() != 'E') {
                            // length 7? (==> banger move found)
                            if (startFound && i - startX == 8)
                                list.add(new Coordinate(i - 4, j));

                            // either way new start
                            startFound = true;
                            startX = i;
                        }
                        // disruption but no wall means start lost
                        else {
                            startFound = false;
                        }
                    }
                }
                // length 7? (==> banger move found)
                if (startFound && width - startX == 8)
                    list.add(new Coordinate(width - 4, j));

            }
        } else {
            // Now searching for banger moves for Player H

            // go through the columns
            for(int i = 0; i < width - 1; i++) {
                boolean startFound = true;
                int startY = -1;
                for (int j = 0; j < height; j++) {
                    // disruption found?
                    if (tileBoard[i][j].getTileChar() != 'E'
                            || tileBoard[i + 1][j].getTileChar() != 'E') {
                        // 2 wall found?
                        if (tileBoard[i][j].getTileChar() != 'E'
                                && tileBoard[i + 1][j].getTileChar() != 'E') {
                            // length 7? (==> banger move found)
                            if (startFound && j - startY == 8)
                                list.add(new Coordinate(i, j - 4));

                            // either way new start
                            startFound = true;
                            startY = j;
                        }
                        // disruption but no wall means start lost
                        else {
                            startFound = false;
                        }
                    }

                }
                // length 7? (==> banger move found)
                if (startFound && height - startY == 8)
                    list.add(new Coordinate(i, height - 4));
            }
        }
        return list;
    }

    public static boolean isCBMove(Tile[][] tileBoard, Coordinate move, Player player) {
        if (player == Player.V) {
            if(coordinateInBounds(tileBoard, new Coordinate(move.getX() - 1, move.getY())) &&
                    tileBoard[move.getX() - 1])
        }
    }
}
