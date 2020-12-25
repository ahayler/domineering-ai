package pgdp.domineering.evaluation_function;

public class RealMovesEvaluationFunction extends EvaluationFunction {
    @Override
    public int[] evaluate(int verticalMobility, int horizontalMobility, int verticalRealMoves, int horizontalRealMoves,
                          int verticalSafeMoves, int horizontalSafeMoves) {
        return new int[]{verticalRealMoves - horizontalMobility, verticalSafeMoves - horizontalSafeMoves};
    }
}
