package pgdp.domineering.ai;

import pgdp.domineering.Coordinate;
import pgdp.domineering.Game;
import pgdp.domineering.Mode;
import pgdp.domineering.Player;
import pgdp.domineering.AI;

public class SimpleCostFunctionAI extends AI {
    /*
    NOTE: Code IS NOT maintained and may be outdated. Please use the MinMaxAI.
    **/

    @Override
    public Coordinate playMove(char[][] board, Player player, Mode mode) {
        return getMove(board, player);
    }

    public static Coordinate getMove(char[][] board, Player player) {
        // first get all possible moves
        Coordinate [] movesArray = Game.getAllPossibleMoves(board, player);

        // now get the min and best move
        int min = 2000;
        Coordinate bestMove = null;

        for(int i = 0; i < movesArray.length; i++) {
            int cost = simpleCostFunction(board, movesArray[i], player);
            if (cost < min) {
                min = cost;
                bestMove = movesArray[i];
            }
        }
        return bestMove;
    }

    private static int simpleCostFunction(char[][] board, Coordinate move, Player player) {
        // get board after move
        board = Game.makeMoveAndCopyBoard(board, move, player);

        if (player == Player.V) {
            int possibleMovesHorizontal = Game.getMobility(board, Player.H);
            // we WANT this move as this means that we win the game
            if (possibleMovesHorizontal == 0)
                return -1000;

            int possibleMovesVertical = Game.getMobility(board, Player.V);

            // we really don't 0 moves because this is a sure loss as the other player has >0 moves left
            if (possibleMovesVertical == 0)
                return 1000;

            // the cost function is normally just: MOVES_WE_STILL_HAVE - MOVES_OTHER_PLAYER_HAS_LEFT
            return possibleMovesHorizontal - possibleMovesVertical;
        } else {
            int possibleMovesVertical = Game.getMobility(board, Player.V);
            // we WANT this move as this means that we win the game
            if (possibleMovesVertical == 0)
                return -1000;

            int possibleMovesHorizontal = Game.getMobility(board, Player.H);

            // we really don't 0 moves because this is a sure loss as the other player has >0 moves left
            if (possibleMovesHorizontal == 0)
                return 1000;

            // the cost function is normally just: MOVES_WE_STILL_HAVE - MOVES_OTHER_PLAYER_HAS_LEFT
            return possibleMovesVertical - possibleMovesHorizontal;
        }
    }
}
