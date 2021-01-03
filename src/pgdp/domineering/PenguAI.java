package pgdp.domineering;

import pgdp.domineering.ai.MinMaxAI_V2;
import pgdp.domineering.ai.MinMaxAI_V1;
import pgdp.domineering.evaluation_function.ExtendedRealAndSafeMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.RealAndSafeMovesEvaluationFunction;

public class PenguAI extends AI {

    public static final boolean PARTICIPATING = true;


    private AI EasyAI = new MinMaxAI_V2(2, new RealAndSafeMovesEvaluationFunction(), true,
            false, 0, true);
    private AI MediumAI = new MinMaxAI_V2(2, new ExtendedRealAndSafeMovesEvaluationFunction(), true,
            true, 10, true);
    private AI HardAI = new MinMaxAI_V2(3, new ExtendedRealAndSafeMovesEvaluationFunction(), true,
            true, 11, true);

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