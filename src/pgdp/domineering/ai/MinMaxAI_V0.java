package pgdp.domineering.ai;

import pgdp.domineering.*;
import pgdp.domineering.evaluation_function.EvaluationFunction;

public class MinMaxAI_V0 extends AI {
    /*
    NOTE: Legacy: Should work, but no further maintenance!
     */
    private final int depth;
    private final EvaluationFunction evaluationFunction;
    private final boolean useSafeMovePruning;

    private final boolean increaseDepth;
    private final int increaseDepthTurn;
    private int turnNumber;

    private final boolean useAlphaBeta;


    public MinMaxAI_V0(int depth, EvaluationFunction evaluationFunction, boolean useSafeMovePruning,
                       boolean increaseDepth, int increaseDepthTurn, boolean useAlphaBeta) {
        this.depth = depth;
        this.evaluationFunction = evaluationFunction;
        this.useSafeMovePruning = useSafeMovePruning;

        this.increaseDepth = increaseDepth;
        this.increaseDepthTurn = increaseDepthTurn;

        this.useAlphaBeta = useAlphaBeta;

        this.turnNumber = 0;
    }

    @Override
    public Coordinate playMove(char[][] board, Player player, Mode mode) {
        if (increaseDepth && turnNumber >= increaseDepthTurn)
            return getMove(board, player, depth + 1);
        turnNumber++;
        return getMove(board, player, depth);
    }

    public Coordinate getMove(char[][] board, Player player, int depth) {
        return minMaxHead(board, depth, player);
    }

    public Coordinate minMaxHead(char[][] board, int depth, Player player) {
        // the head is very similar to mixMax itself but it returns the coordinate.
        int[] alpha = new int[]{-2000, -2000};
        int[] beta = new int[]{2000, 2000};

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
                        depth - 1, Player.H, isMoveSafeMove(board, movesArray[i], player), alpha, beta);

                // max(evaluation, best move)
                if (evaluation[0] >= 1000) {
                    // if a winning move is found end search
                    return movesArray[i];
                } else if (evaluation[0] > maxRealMovesDiff ||
                        (evaluation[0] == maxRealMovesDiff && evaluation[1] > maxSafeMoveDiff)) {
                    // is new max
                    maxRealMovesDiff = evaluation[0];
                    maxSafeMoveDiff = evaluation[1];
                    bestMove = movesArray[i];
                }

                if (useAlphaBeta) {
                    // alpha = max(alpha, eval)
                    if (evaluation[0] > alpha[0] || (evaluation[0] == alpha[0] && evaluation[1] > alpha[1])) {
                        alpha = evaluation;
                    }

                    // if beta <= alpha: break ==> can't happen here
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
                        depth - 1, Player.V, isMoveSafeMove(board, movesArray[i], player), alpha, beta);

                // min(eval, best move)
                if (evaluation[0] <= -1000) {
                    // if a winning move is found end search
                    return movesArray[i];
                } else if (evaluation[0] < minRealMovesDiff ||
                        (evaluation[0] == minRealMovesDiff && evaluation[1] < minSafeMoveDiff)) {
                    // is new max
                    minRealMovesDiff = evaluation[0];
                    minSafeMoveDiff = evaluation[1];
                    bestMove = movesArray[i];

                    if (useAlphaBeta) {
                        // alpha = max(alpha, eval)
                        if (evaluation[0] < beta[0] || (evaluation[0] == beta[0] && evaluation[1] < alpha[1])) {
                            beta = evaluation;
                        }

                        // if beta <= alpha: break ==> can't happen here
                    }
                }
            }
            return bestMove;

        }

    }

    public int[] minMax(char[][] board, int depth, Player player, boolean lastMoveWasASafeMove, int[] alpha, int[] beta) {
        /* The vertical player maximizes and horizontal minimizes */


        // first check if this is a losing or winning: if so end the search here
        Player opponent;
        if (player == Player.V) opponent = Player.H;
        else opponent = Player.V;


        // Note: It is important to call the evaluation with the opponent as he has made the last move
        Tuple<Boolean, int[]> boardEvaluation = checkIfGameEndedAndComputeStaticEvaluation(board, opponent,
                lastMoveWasASafeMove);

        if (boardEvaluation.x || depth == 0) {
            // game has ended or max search depth has been reached
            return boardEvaluation.y;
        }

        // now its time to get recursive

        // The vertical player is the maximizing player
        if (player == Player.V)
            return max(board, depth, alpha, beta);
        else return min(board, depth, alpha, beta);
    }

    public int[] max(char[][] board, int depth, int[] alpha, int[] beta) {
        // maximize the value of the Static Evaluation

        // first get all possible moves
        Coordinate[] movesArray = Game.getAllPossibleMoves(board, Player.V);

        int maxRealMovesDiff = -2000;
        int maxSafeMoveDiff = -2000;

        for (int i = 0; i < movesArray.length; i++) {
            int[] evaluation = minMax(Game.makeMoveAndCopyBoard(board, movesArray[i], Player.V),
                    depth - 1, Player.H, isMoveSafeMove(board, movesArray[i], Player.V), alpha, beta);

            if (evaluation[0] == 1000) {
                // if a winning move is found end search
                return evaluation;
            } else if (evaluation[0] > maxRealMovesDiff ||
                    (evaluation[0] == maxRealMovesDiff && evaluation[1] > maxSafeMoveDiff)) {
                // is new max
                maxRealMovesDiff = evaluation[0];
                maxSafeMoveDiff = evaluation[1];

                if (useAlphaBeta) {
                    // alpha = max(alpha, eval)
                    if (evaluation[0] > alpha[0] || (evaluation[0] == alpha[0] && evaluation[1] > alpha[1])) {
                        alpha = evaluation;
                    }

                    // if beta <= alpha: break
                    if (beta[0] < alpha[0] || (beta[0] == alpha[0] && beta[1] <= alpha[1]))
                        break;
                }
            }
        }
        return new int[]{maxRealMovesDiff, maxSafeMoveDiff};
    }

    public int[] min(char[][] board, int depth, int[] alpha, int[] beta) {
        // minimize the value of the Static Evaluation

        // first get all possible moves
        Coordinate[] movesArray = Game.getAllPossibleMoves(board, Player.H);

        int minRealMovesDiff = 2000;
        int minSafeMoveDiff = 2000;

        for (int i = 0; i < movesArray.length; i++) {
            int[] evaluation = minMax(Game.makeMoveAndCopyBoard(board, movesArray[i], Player.H),
                    depth - 1, Player.V, isMoveSafeMove(board, movesArray[i], Player.H), alpha, beta);

            if (evaluation[0] == -1000) {
                // if a winning move is found end search
                return evaluation;
            } else if (evaluation[0] < minRealMovesDiff ||
                    (evaluation[0] == minRealMovesDiff && evaluation[1] < minSafeMoveDiff)) {
                // is new max
                minRealMovesDiff = evaluation[0];
                minSafeMoveDiff = evaluation[1];
            }

            if (useAlphaBeta) {
                // alpha = max(alpha, eval)
                if (evaluation[0] < beta[0] || (evaluation[0] == beta[0] && evaluation[1] < alpha[1])) {
                    beta = evaluation;
                }

                // if beta <= alpha
                if (beta[0] < alpha[0] || (beta[0] == alpha[0] && beta[1] <= alpha[1]))
                    break;
            }
        }
        return new int[]{minRealMovesDiff, minSafeMoveDiff};
    }

    public Tuple<Boolean, int[]> checkIfGameEndedAndComputeStaticEvaluation
            (char[][] board, Player player, boolean lastMoveWasASafeMove) {
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


        int playerMobility;
        int opponentMobility;
        if (evaluationFunction.mobilityNeeded()) {
            playerMobility = Game.getMobility(board, player);
            opponentMobility = Game.getMobility(board, opponent);
        } else {
            playerMobility = 0;
            opponentMobility = 0;
        }

        int realMovesVertical;
        int safeMovesVertical;
        int realMovesHorizontal;
        int safeMovesHorizontal;
        int verticalMobility;
        int horizontalMobility;

        int verticalSafeMovePossibilities = Game.getSafeMovePossibilities(board, Player.V);
        int horizontalSafeMovePossibilities = Game.getSafeMovePossibilities(board, Player.H);

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
        if (opponentRealMoves == 0) {
            playerWins = true;
        } else if (playerRealMoves == 0) {
            // If the the opponent has at least one turn and we have none: we lose
            opponentWins = true;
        } else if (playerSafeMoves >= opponentRealMoves) { // If our safe turns >= opponents real turns: we win
            playerWins = true;
        } else if (playerRealMoves <= opponentSafeMoves && playerRealMoves + 1 <= opponentRealMoves) {
            // If our real turns <= opponents safe turns and our real turns + 1 <= opponents real turns: we lose
            opponentWins = true;
        }


        // calculate the normal Evaluation Function
        int[] evaluation = evaluationFunction.evaluate(
                verticalMobility, horizontalMobility, realMovesVertical,
                realMovesHorizontal, safeMovesVertical, safeMovesHorizontal,
                verticalSafeMovePossibilities, horizontalSafeMovePossibilities, 0, 0);

        /* if there is a winner modify the evaluation accordingly
        (the +/- is there so that the AI doesn't play like shit just because it has lost/won)
        */
        if ((playerWins && player == Player.V) || (opponentWins && player == Player.H)) {
            evaluation[0] += 1000;
            gameDecided = true;
        } else if ((playerWins && player == Player.H) || (opponentWins && player == Player.V)) {
            evaluation[0] += -1000;
            gameDecided = true;
        } else if (lastMoveWasASafeMove && playerRealMoves > playerSafeMoves){
            /*
            This means that the player could have played a real move instead.
            At this point we prune the search and add a penalty of 500.
             */
            gameDecided = true;
            if (player == Player.V) evaluation[0] -= 500; else evaluation[0] += 500;
        }

        return new Tuple<>(gameDecided, evaluation);
    }

    public boolean isMoveSafeMove(char[][] board, Coordinate move, Player player) {
        if (useSafeMovePruning)
            return Game.isSafeMove(board, move, player);
        else return false;
    }
}
