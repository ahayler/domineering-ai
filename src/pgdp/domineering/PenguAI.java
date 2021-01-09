package pgdp.domineering;

import pgdp.domineering.ai.*;
import pgdp.domineering.bias_function.BiasFunction_V0;
import pgdp.domineering.evaluation_function.ExtendedRealAndSafeMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.RealAndSafeMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.ReworkedEvaluationFunction;
import pgdp.domineering.opening.CornerStoneOpening;

public class PenguAI extends AI {


    public static final boolean PARTICIPATING = true;

    private AI EasyAI = new MinMaxAI_V2(2, new RealAndSafeMovesEvaluationFunction(), true,
            false, 0, true, false);
    private AI MediumAI = new MinMaxAI_V2(2, new ExtendedRealAndSafeMovesEvaluationFunction(), true,
            false, 10, true, false);
    private AI HardAI = new MinMaxAI_V5(5, new ReworkedEvaluationFunction(), true,
            false, 0, true, true,new CornerStoneOpening());

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