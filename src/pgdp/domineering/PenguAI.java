package pgdp.domineering;

import pgdp.domineering.ai.MinMaxAI;
import pgdp.domineering.evaluation_function.RealAndSafeMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.RealMovesEvaluationFunction;

public class PenguAI extends AI {
    public static final boolean PARTICIPATING = false;

    @Override
    public synchronized Coordinate playMove(char[][] board, Player player, Mode mode) {
        if (mode == Mode.HARD || mode == Mode.MEDIUM)
            return MinMaxAI.getMove(board, player, 2, new RealAndSafeMovesEvaluationFunction());

        return MinMaxAI.getMove(board, player, 1, new RealMovesEvaluationFunction());

        /*return AdvancedCostFunctionAI.getMove(board, player);*/

        /*return SimpleCostFunctionAI.getMove(board, player);*/
    }
}