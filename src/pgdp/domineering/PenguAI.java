package pgdp.domineering;

import pgdp.domineering.ai.MinMaxAI;
import pgdp.domineering.evaluation_function.ExtendedRealAndSafeMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.RealAndSafeMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.RealMovesEvaluationFunction;

public class PenguAI extends AI {

    public static final boolean PARTICIPATING = true;


    private AI EasyAI = new MinMaxAI(2, new RealAndSafeMovesEvaluationFunction(), true,
            false, 0, true);
    private AI MediumAI = new MinMaxAI(2, new ExtendedRealAndSafeMovesEvaluationFunction(), true,
            true, 10, true);
    private AI HardAI = new MinMaxAI(3, new ExtendedRealAndSafeMovesEvaluationFunction(), true,
            true, 15, true);

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