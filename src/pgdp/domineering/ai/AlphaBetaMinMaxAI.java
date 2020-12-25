package pgdp.domineering.ai;

import pgdp.domineering.*;
import pgdp.domineering.ai.AI;

public class AlphaBetaMinMaxAI extends AI {
    @Override
    public Coordinate playMove(char[][] board, Player player, Mode mode) {
        return getMove(board, player, 3);
    }

    public static Coordinate getMove(char[][] board, Player player, int depth) {
        return minMaxHead(board, depth, player);
    }

    private static Coordinate minMaxHead(char[][] board, int depth, Player player) {
        // the head is very similar to mixMax itself but it returns the coordinate. Now with Alpha-Beta-Pruning
        int alpha = -2000;
        int beta = 2000;

        // first get all possible moves
        Coordinate [] movesArray = Game.getAllPossibleMoves(board, player);

        if (depth < 1) {
            return null;
        }

        if (player == Player.V) {
            // MAX
            int maxRealMovesDiff = -2000;
            int maxSafeMoveDiff = -2000;
            Coordinate bestMove = null;
            for (int i = 0; i < movesArray.length; i++) {
                int[] evaluation = minMax(Game.makeMoveAndCopyBoard(board, movesArray[i], player),
                        depth - 1, alpha, beta, Player.H);

                // max(evaluation, best move)
                if (evaluation[0] == 1000) {
                    // if a winning move is found end search
                    return movesArray[i];
                } else if (evaluation[0] > maxRealMovesDiff ||
                        (evaluation[0] == maxRealMovesDiff && evaluation[1] > maxSafeMoveDiff)) {
                    // is new max
                    maxRealMovesDiff = evaluation[0];
                    maxSafeMoveDiff = evaluation[1];
                    bestMove = movesArray[i];
                }

                // max(alpha, eval)
                alpha = Math.max(alpha, evaluation[0]);

                // if beta <= alpha: break (but this can't be reached in the head)
            }
            return bestMove;

        } else {
            // MIN
            int minRealMovesDiff =  2000;
            int minSafeMoveDiff = 2000;
            Coordinate bestMove = null;

            for(int i = 0; i < movesArray.length; i++) {
                int[] evaluation = minMax(Game.makeMoveAndCopyBoard(board, movesArray[i], player),
                        depth - 1, alpha, beta, Player.V);

                // min(eval, best move)
                if (evaluation[0] == -1000) {
                    // if a winning move is found end search
                    return movesArray[i];
                } else if (evaluation[0] < minRealMovesDiff ||
                        (evaluation[0] == minRealMovesDiff && evaluation[1] < minSafeMoveDiff)) {
                    // is new max
                    minRealMovesDiff = evaluation[0];
                    minSafeMoveDiff = evaluation[1];
                    bestMove = movesArray[i];
                }

                // beta = min(beta, eval)
                beta = Math.min(beta, evaluation[0]);

                // if beta <= alpha break (can't be reached in the head
            }
            return bestMove;

        }

    }

    private static int[] minMax(char[][] board, int depth, int alpha, int beta, Player player) {
        /* The vertical player maximizes and horizontal minimizes */


        // first check if this is a losing or winning: if so end the search here
        Tuple<Boolean, int[]> boardEvaluation = checkIfGameEndedAndComputeStaticEvaluation(board, player);

        if (boardEvaluation.x || depth == 0) {
            // game has ended or max has been reached
            return boardEvaluation.y;
        }

        // now its time to get recursive

        // The vertical player is the maximizing player
        if (player == Player.V)
            return max(board, depth, alpha, beta);
        else return min(board, depth, alpha, beta);
    }

    private static int[] max (char[][] board, int depth, int alpha, int beta) {
        // maximize the value of the Static Evaluation

        // first get all possible moves
        Coordinate [] movesArray = Game.getAllPossibleMoves(board, Player.V);

        int maxRealMovesDiff = -2000;
        int maxSafeMoveDiff = -2000;

        for(int i = 0; i < movesArray.length; i++) {
            int[] evaluation = minMax(Game.makeMoveAndCopyBoard(board, movesArray[i], Player.V), depth - 1,
                    alpha, beta, Player.H);

            // max (maxEval, eval)
            if (evaluation[0] == 1000) {
                // if a winning move is found end search
                return evaluation;
            } else if (evaluation[0] > maxRealMovesDiff ||
                    (evaluation[0] == maxRealMovesDiff && evaluation[1] > maxSafeMoveDiff)){
                // is new max
                maxRealMovesDiff = evaluation[0];
                maxSafeMoveDiff = evaluation[1];
            }

            // max (alpha, eval)
            alpha = Math.max(alpha, evaluation[0]);

            // if beta <= alpha break
            if (beta <= alpha)
                break;

        }
        return  new int[]{maxRealMovesDiff, maxSafeMoveDiff};
    }

    private static int[] min (char[][] board, int depth, int alpha, int beta) {
        // minimize the value of the Static Evaluation

        // first get all possible moves
        Coordinate [] movesArray = Game.getAllPossibleMoves(board, Player.H);

        int minRealMovesDiff =  2000;
        int minSafeMoveDiff = 2000;

        for(int i = 0; i < movesArray.length; i++) {
            int[] evaluation = minMax(Game.makeMoveAndCopyBoard(board, movesArray[i], Player.H), depth - 1,
                    alpha, beta, Player.V);

            // min (minEval, eval)
            if (evaluation[0] == -1000) {
                // if a winning move is found end search
                return evaluation;
            } else if (evaluation[0] <minRealMovesDiff ||
                    (evaluation[0] == minRealMovesDiff && evaluation[1] < minSafeMoveDiff)){
                // is new max
                minRealMovesDiff = evaluation[0];
                minSafeMoveDiff = evaluation[1];
            }

            // min (beta, eval)
            beta = Math.min(beta, evaluation[0]);

            // if beta <= alpha break
            if (beta <= alpha)
                break;
        }
        return  new int[]{minRealMovesDiff, minSafeMoveDiff};
    }

    private static Tuple<Boolean, int[]> checkIfGameEndedAndComputeStaticEvaluation (char[][] board, Player player) {
        /*
        Checks if the game has ended. The player is needed to
        see who JUST made a move.

        If the game has ended the method returns the winner by stating -1000 or +1000.
        If not it returns the static Evaluation of the board.
         */
        Player opponent;
        if (player == Player.V) opponent = Player.H; else opponent = Player.V;
        boolean playerWins = false;
        boolean opponentWins = false;

        int playerSafeMoves = Game.getSafeMoves(board, player);
        int opponentSafeMoves = Game.getSafeMoves(board, opponent);
        int playerRealMoves = Game.getRealMoves(board, player);
        int opponentRealMoves = Game.getRealMoves(board, opponent);
        int playerMobility = Game.getMobility(board, player);
        int opponentMobility = Game.getMobility(board, opponent);

        int realMovesVertical;
        int safeMovesVertical;
        int realMovesHorizontal;
        int safeMovesHorizontal;
        int verticalMobility;
        int horizontalMobility;

        if (player == Player.V) {
            realMovesVertical = playerRealMoves;
            safeMovesVertical = playerSafeMoves;
            realMovesHorizontal = opponentRealMoves;
            safeMovesHorizontal = opponentSafeMoves;
            verticalMobility = playerMobility;
            horizontalMobility = opponentMobility;

        } else {
            realMovesVertical = opponentRealMoves;
            safeMovesVertical = opponentSafeMoves;
            realMovesHorizontal = playerRealMoves;
            safeMovesHorizontal = playerSafeMoves;
            verticalMobility = opponentMobility;
            horizontalMobility = playerMobility;
        }

        // If the opponent has no turns left: we win
        if (opponentMobility == 0) {
            playerWins = true;
        } else if (playerMobility == 0) {
            // If the the opponent has at least one turn and we have none: we lose
            opponentWins = true;
        } else  if(playerSafeMoves >= opponentRealMoves)
        { // If our safe turns >= opponents real turns: we win
            playerWins = true;
        } else if(playerRealMoves <= opponentSafeMoves && playerRealMoves <= opponentRealMoves + 1) {
            // If our real turns <= opponents safe turns and our real turns + 1 <= opponents real turns: we lose
            opponentWins = true;
        } else {
            // Game has not ended return the normal static evaluation
/*            return new Tuple<Boolean, int[]>(false,
                    new int[]{realMovesVertical - realMovesHorizontal, safeMovesVertical - safeMovesHorizontal});*/
/*            return new Tuple<Boolean, int[]>(false,
                    new int[]{verticalMobility + realMovesVertical
                            - horizontalMobility - realMovesHorizontal,
                            safeMovesVertical - safeMovesHorizontal});*/
            return new Tuple<Boolean, int[]>(false,
                    new int[]{safeMovesVertical + realMovesVertical - safeMovesHorizontal - realMovesHorizontal,
                            verticalMobility - horizontalMobility});
        }

        // if there is a winner return the right thing
        if ((playerWins && player == Player.V) || (opponentWins && player == Player.H)) {
            return new Tuple<Boolean, int[]>(true, new int[]{1000, 0});
        } else return new Tuple<Boolean, int[]>(true, new int[]{-1000, 0});
    }
}