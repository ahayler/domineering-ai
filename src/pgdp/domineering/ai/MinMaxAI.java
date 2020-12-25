package pgdp.domineering.ai;

import pgdp.domineering.*;
import pgdp.domineering.ai.AI;
import pgdp.domineering.evaluation_function.EvaluationFunction;
import pgdp.domineering.evaluation_function.RealMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.SimpleMobilityEvaluationFunction;

public class MinMaxAI extends AI {
    private int depth;
    private EvaluationFunction evaluationFunction;

    public MinMaxAI(int depth, EvaluationFunction evaluationFunction) {
        this.depth = depth;
        this.evaluationFunction = evaluationFunction;
    }

    @Override
    public Coordinate playMove(char[][] board, Player player, Mode mode) {
        return getMove(board, player, depth, evaluationFunction);
    }

    public static Coordinate getMove(char[][] board, Player player, int depth, EvaluationFunction evaluationFunction) {
        return minMaxHead(board, depth, player, evaluationFunction);
    }

    private static Coordinate minMaxHead(char[][] board, int depth, Player player,
                                         EvaluationFunction evaluationFunction) {
        // the head is very similar to mixMax itself but it returns the coordinate.


        // first get all possible moves
        Coordinate[] movesArray = Game.getAllPossibleMoves(board, player);

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
                        depth - 1, Player.H, evaluationFunction);

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
            }
            return bestMove;

        } else {
            // MIN
            int minRealMovesDiff = 2000;
            int minSafeMoveDiff = 2000;
            Coordinate bestMove = null;

            for (int i = 0; i < movesArray.length; i++) {
                int[] evaluation = minMax(Game.makeMoveAndCopyBoard(board, movesArray[i], player),
                        depth - 1, Player.V, evaluationFunction);

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
            }
            return bestMove;

        }

    }

    private static int[] minMax(char[][] board, int depth, Player player, EvaluationFunction evaluationFunction) {
        /* The vertical player maximizes and horizontal minimizes */


        // first check if this is a losing or winning: if so end the search here
        Player opponent;
        if (player == Player.V) opponent = Player.H;
        else opponent = Player.V;
        // Note it is important to call the evaluation with the opponent as he has made the last move
        Tuple<Boolean, int[]> boardEvaluation = checkIfGameEndedAndComputeStaticEvaluation(board, opponent,
                evaluationFunction);

        if (boardEvaluation.x || depth == 0) {
            // game has ended or max search depth has been reached
            return boardEvaluation.y;
        }

        // now its time to get recursive

        // The vertical player is the maximizing player
        if (player == Player.V)
            return max(board, depth, evaluationFunction);
        else return min(board, depth, evaluationFunction);
    }

    private static int[] max(char[][] board, int depth, EvaluationFunction evaluationFunction) {
        // maximize the value of the Static Evaluation

        // first get all possible moves
        Coordinate[] movesArray = Game.getAllPossibleMoves(board, Player.V);

        int maxRealMovesDiff = -2000;
        int maxSafeMoveDiff = -2000;

        for (int i = 0; i < movesArray.length; i++) {
            int[] evaluation = minMax(Game.makeMoveAndCopyBoard(board, movesArray[i], Player.V),
                    depth - 1, Player.H, evaluationFunction);

            if (evaluation[0] == 1000) {
                // if a winning move is found end search
                return evaluation;
            } else if (evaluation[0] > maxRealMovesDiff ||
                    (evaluation[0] == maxRealMovesDiff && evaluation[1] > maxSafeMoveDiff)) {
                // is new max
                maxRealMovesDiff = evaluation[0];
                maxSafeMoveDiff = evaluation[1];
            }
        }
        return new int[]{maxRealMovesDiff, maxSafeMoveDiff};
    }

    private static int[] min(char[][] board, int depth, EvaluationFunction evaluationFunction) {
        // minimize the value of the Static Evaluation

        // first get all possible moves
        Coordinate[] movesArray = Game.getAllPossibleMoves(board, Player.H);

        int minRealMovesDiff = 2000;
        int minSafeMoveDiff = 2000;

        for (int i = 0; i < movesArray.length; i++) {
            int[] evaluation = minMax(Game.makeMoveAndCopyBoard(board, movesArray[i], Player.H),
                    depth - 1, Player.V, evaluationFunction);

            if (evaluation[0] == -1000) {
                // if a winning move is found end search
                return evaluation;
            } else if (evaluation[0] < minRealMovesDiff ||
                    (evaluation[0] == minRealMovesDiff && evaluation[1] < minSafeMoveDiff)) {
                // is new max
                minRealMovesDiff = evaluation[0];
                minSafeMoveDiff = evaluation[1];
            }
        }
        return new int[]{minRealMovesDiff, minSafeMoveDiff};
    }

    private static Tuple<Boolean, int[]> checkIfGameEndedAndComputeStaticEvaluation
            (char[][] board, Player player, EvaluationFunction evaluationFunction) {
        /*
        Checks if the game has ended. The player is needed to
        see who JUST made a move.

        If the game has ended the method returns the winner by stating -1000 or +1000.
        If not it returns the static Evaluation of the board.
         */
        Player opponent;
        if (player == Player.V) opponent = Player.H;
        else opponent = Player.V;
        boolean playerWins = false;
        boolean opponentWins = false;
        boolean gameDecided = false;

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

        /*
        This section checks if a certain move is winning for either side,
        which of course ends the search and attaches a +1000 or -1000 to a certain move so it will be avoided.
         */
        // If the opponent has no turns left: we win
        if (opponentMobility == 0) {
            playerWins = true;
        } else if (playerMobility == 0) {
            // If the the opponent has at least one turn and we have none: we lose
            opponentWins = true;
        } else if (playerSafeMoves >= opponentRealMoves) { // If our safe turns >= opponents real turns: we win
            playerWins = true;
        } else if (playerRealMoves <= opponentSafeMoves && playerRealMoves <= opponentRealMoves + 1) {
            // If our real turns <= opponents safe turns and our real turns + 1 <= opponents real turns: we lose
            opponentWins = true;
        }


        // calculate the normal Evaluation Function
        int[] evaluation = evaluationFunction.evaluate(
                verticalMobility, horizontalMobility, realMovesVertical,
                realMovesHorizontal, safeMovesVertical, safeMovesHorizontal);

        /* if there is a winner modify the evaluation accordingly
        (the +/- is there so that the AI doesn't play like shit just because it has lost/won)
        */
        if ((playerWins && player == Player.V) || (opponentWins && player == Player.H)) {
            evaluation[0] += 1000;
            gameDecided = true;
        } if ((playerWins && player == Player.H) || (opponentWins && player == Player.V)) {
            evaluation[0] += -1000;
            gameDecided = true;
        }

        return new Tuple<>(gameDecided, evaluation);
    }
}
