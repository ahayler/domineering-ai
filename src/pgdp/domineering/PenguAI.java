package pgdp.domineering;

import pgdp.domineering.ai.MinMaxAI;
import pgdp.domineering.evaluation_function.RealAndSafeMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.RealMovesEvaluationFunction;

public class PenguAI extends AI {
    public static final boolean PARTICIPATING = false;

    private AI EasyAI = new MinMaxAI(1, new RealAndSafeMovesEvaluationFunction(), false);
    private AI MediumAI = new MinMaxAI(2, new RealAndSafeMovesEvaluationFunction(), false);
    private AI HardAI = new MinMaxAI(2, new RealAndSafeMovesEvaluationFunction(), false);

    @Override
    public synchronized Coordinate playMove(char[][] board, Player player, Mode mode) {
        if (mode == Mode.EASY)
            return EasyAI.playMove(board, player, mode);
        else if (mode == Mode.MEDIUM)
            return MediumAI.playMove(board, player, mode);
        else
            return HardAI.playMove(board, player, mode);
    }
}