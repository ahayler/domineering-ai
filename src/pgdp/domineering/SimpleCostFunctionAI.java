package pgdp.domineering;

public class SimpleCostFunctionAI extends AI {

    @Override
    public Coordinate playMove(char[][] board, Player player, Mode mode) {
        return getMove(board, player);
    }

    public static Coordinate getMove(char[][] board, Player player) {
        // first get all possible moves
        Coordinate [] movesArray = Game.getAllPossibleMoves(board, player);

        // now get the max and best move
        int max = -2000;
        Coordinate bestMove = null;

        for(int i = 0; i < movesArray.length; i++) {
            int cost = SimpleCostFunction(board, movesArray[i], player);
            if (cost > max) {
                max = cost;
                bestMove = movesArray[i];
            }
        }
        return bestMove;
    }

    private static int SimpleCostFunction(char[][] board, Coordinate move, Player player) {
        // get board after move
        board = Game.makeMoveAndCopyBoard(board, move, player);

        if (player == Player.V) {
            int possibleMovesHorizontal = Game.countPossibleMoves(board, Player.H);
            // we WANT this move as this means that we win the game
            if (possibleMovesHorizontal == 0)
                return 1000;

            int possibleMovesVertical = Game.countPossibleMoves(board, Player.V);

            // we really don't 0 moves because this is a sure loss as the other player has >0 moves left
            if (possibleMovesVertical == 0)
                return -1000;

            // the cost function is normally just: MOVES_WE_STILL_HAVE - MOVES_OTHER_PLAYER_HAS_LEFT
            return possibleMovesVertical - possibleMovesHorizontal;
        } else {
            int possibleMovesVertical = Game.countPossibleMoves(board, Player.V);
            // we WANT this move as this means that we win the game
            if (possibleMovesVertical == 0)
                return 1000;

            int possibleMovesHorizontal = Game.countPossibleMoves(board, Player.H);

            // we really don't 0 moves because this is a sure loss as the other player has >0 moves left
            if (possibleMovesHorizontal == 0)
                return -1000;

            // the cost function is normally just: MOVES_WE_STILL_HAVE - MOVES_OTHER_PLAYER_HAS_LEFT
            return possibleMovesHorizontal - possibleMovesVertical;
        }
    }
}
