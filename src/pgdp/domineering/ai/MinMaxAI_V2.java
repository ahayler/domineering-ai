package pgdp.domineering.ai;

import pgdp.domineering.*;
import pgdp.domineering.AI;
import pgdp.domineering.evaluation_function.EvaluationFunction;
import pgdp.domineering.tiles.Tile;
import pgdp.domineering.tiles.TileManager;

import java.util.List;
import java.util.function.BinaryOperator;

public class MinMaxAI_V2 extends AI {
    private final int depth;
    private final EvaluationFunction evaluationFunction;
    private final boolean useSafeMovePruning;
    private final boolean useSMCMovePruning;

    private final boolean increaseDepth;
    private final int increaseDepthTurn;
    private int turnNumber;

    private final boolean useAlphaBeta;


    public MinMaxAI_V2(int depth, EvaluationFunction evaluationFunction, boolean useSafeMovePruning,
                       boolean increaseDepth, int increaseDepthTurn, boolean useAlphaBeta, boolean useSMCMovePruning) {
        this.depth = depth;
        this.evaluationFunction = evaluationFunction;
        this.useSafeMovePruning = useSafeMovePruning;
        this.useSMCMovePruning = useSMCMovePruning;

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
        // generate the tile board
        Tile[][] tileBoard = TileManager.boardToTileBoard(board);

        return minMaxHead(tileBoard, depth, player);
    }

    public Coordinate minMaxHead(Tile[][] tileBoard, int depth, Player player) {

        // the head is very similar to mixMax itself but it returns the coordinate.
        int[] alpha = new int[]{-2000, -2000};
        int[] beta = new int[]{2000, 2000};

        // first get all possible moves
        Coordinate[] movesArray = getMoves(tileBoard, player);

        if (depth < 1) {
            return null;
        }

        if (player == Player.V) {
            // MAX
            int maxRealMovesDiff = -2000;
            int maxSafeMoveDiff = -2000;
            Coordinate bestMove = null;
            for (int i = 0; i < movesArray.length; i++) {
                Tile[][] updatedBoard = TileManager.makeMoveAndCopyBoard(tileBoard, movesArray[i], player);
                int[] evaluation = minMax(updatedBoard,
                        depth - 1, Player.H, isMoveSafeMove(tileBoard, movesArray[i], player), alpha, beta);

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
                Tile[][] updatedBoard = TileManager.makeMoveAndCopyBoard(tileBoard, movesArray[i], player);
                int[] evaluation = minMax(updatedBoard,  depth - 1,
                        Player.V, isMoveSafeMove(tileBoard, movesArray[i], player), alpha, beta);

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

    public int[] minMax(Tile[][] tileBoard, int depth, Player player,
                        boolean lastMoveWasASafeMove, int[] alpha, int[] beta) {
        /* The vertical player maximizes and horizontal minimizes */


        // first check if this is a losing or winning: if so end the search here
        Player opponent;
        if (player == Player.V) opponent = Player.H;
        else opponent = Player.V;


        // Note: It is important to call the evaluation with the opponent as he has made the last move
        Tuple<Boolean, int[]> boardEvaluation = checkIfGameEndedAndComputeStaticEvaluation(tileBoard, opponent,
                lastMoveWasASafeMove);

        if (boardEvaluation.x || depth == 0) {
            // game has ended or max search depth has been reached
            return boardEvaluation.y;
        }

        // now its time to get recursive

        // The vertical player is the maximizing player
        if (player == Player.V)
            return max(tileBoard, depth, alpha, beta);
        else return min(tileBoard, depth, alpha, beta);
    }

    public int[] max(Tile[][] tileBoard, int depth, int[] alpha, int[] beta) {
        // maximize the value of the Static Evaluation

        // first get all possible moves
        Coordinate[] movesArray = getMoves(tileBoard, Player.V);

        int maxRealMovesDiff = -2000;
        int maxSafeMoveDiff = -2000;

        for (int i = 0; i < movesArray.length; i++) {
            Tile[][] updatedBoard = TileManager.makeMoveAndCopyBoard(tileBoard, movesArray[i], Player.V);
            int[] evaluation = minMax(updatedBoard,
                    depth - 1, Player.H, isMoveSafeMove(tileBoard, movesArray[i], Player.V), alpha, beta);

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

    public int[] min(Tile[][] tileBoard, int depth, int[] alpha, int[] beta) {
        // minimize the value of the Static Evaluation

        // first get all possible moves
        Coordinate[] movesArray = getMoves(tileBoard, Player.H);

        int minRealMovesDiff = 2000;
        int minSafeMoveDiff = 2000;

        for (int i = 0; i < movesArray.length; i++) {
            Tile[][] updatedBoard = TileManager.makeMoveAndCopyBoard(tileBoard, movesArray[i], Player.H);
            int[] evaluation = minMax(updatedBoard, depth - 1, Player.V,
                    isMoveSafeMove(tileBoard, movesArray[i], Player.H), alpha, beta);

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
            (Tile[][] tileBoard, Player player, boolean lastMoveWasASafeMove) {
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

        int[] metrics = TileManager.getRealMovesSafeMovesAndPossibilitiesVerticalAndHorizontal(tileBoard);

        int realMovesVertical = metrics[0];
        int realMovesHorizontal = metrics[1];
        int safeMovesVertical = metrics[2];
        int safeMovesHorizontal = metrics[3];
        int verticalSafeMovePossibilities = metrics[4];
        int horizontalSafeMovePossibilities = metrics[5];

        int verticalMobility = 0;
        int horizontalMobility = 0;

        int playerRealMoves, playerSafeMoves, opponentRealMoves, opponentSafeMoves;
        if (player == Player.V) {
            playerRealMoves = realMovesVertical;
            playerSafeMoves = safeMovesVertical;
            opponentRealMoves = realMovesHorizontal;
            opponentSafeMoves = safeMovesHorizontal;
        } else {
            playerRealMoves = realMovesHorizontal;
            playerSafeMoves = safeMovesHorizontal;
            opponentRealMoves = realMovesVertical;
            opponentSafeMoves = safeMovesVertical;
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
        } else if (playerRealMoves <= opponentSafeMoves && playerRealMoves <= opponentRealMoves + 1) {
            // If our real turns <= opponents safe turns and our real turns + 1 <= opponents real turns: we lose
            opponentWins = true;
        }


        // calculate the normal Evaluation Function
        int[] evaluation = evaluationFunction.evaluate(
                verticalMobility, horizontalMobility, realMovesVertical,
                realMovesHorizontal, safeMovesVertical, safeMovesHorizontal,
                verticalSafeMovePossibilities, horizontalSafeMovePossibilities);

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

    public boolean isMoveSafeMove(Tile[][] tileBoard, Coordinate move, Player player) {
        if (useSafeMovePruning)
            return TileManager.isSafeMove(tileBoard, move, player);
        else return false;
    }

    public Coordinate[] getMoves(Tile[][] tileBoard, Player player) {
        if(useSMCMovePruning)
            return getAllMovesWithSMCPruning(tileBoard, player);
        else return TileManager.getAllPossibleMoves(tileBoard, player);
    }

    public Coordinate[] getAllMovesWithSMCPruning(Tile[][] tileBoard, Player player) {
        Tuple<List<Coordinate>, Boolean> results = TileManager.getAllSMCMoves(tileBoard, player);
        List<Coordinate> allMoves = TileManager.getBangerMoves(tileBoard, player);
        allMoves.addAll(TileManager.getAllBlockingMoves(tileBoard, player));
        allMoves.addAll(results.x);


        if (!results.y || results.x.size() == 0) {
            return TileManager.getAllPossibleMoves(tileBoard, player);
        } else return allMoves.toArray(new Coordinate[0]);
    }
}
