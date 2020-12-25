package pgdp.domineering.ai;

import pgdp.domineering.Coordinate;
import pgdp.domineering.Mode;
import pgdp.domineering.Player;
import pgdp.domineering.evaluation_function.RealAndSafeMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.RealMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.SimpleMobilityEvaluationFunction;

public class PenguAI extends AI {
    public static final boolean PARTICIPATING = false;

    @Override
    public synchronized Coordinate playMove(char[][] board, Player player, Mode mode) {
        if (mode == Mode.HARD)
            return MinMaxAI.getMove(board, player, 2, new RealAndSafeMovesEvaluationFunction());

        return MinMaxAI.getMove(board, player, 1, new RealMovesEvaluationFunction());

        /*return AdvancedCostFunctionAI.getMove(board, player);*/

        /*return SimpleCostFunctionAI.getMove(board, player);*/
    }
}