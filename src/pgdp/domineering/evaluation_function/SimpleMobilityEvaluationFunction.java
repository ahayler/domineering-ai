package pgdp.domineering.evaluation_function;

public class SimpleMobilityEvaluationFunction extends EvaluationFunction {
    @Override
    public int[] evaluate(int verticalMobility, int horizontalMobility, int verticalRealMoves,
                          int horizontalRealMoves, int verticalSafeMoves, int horizontalSafeMoves,
                          int verticalSafeMovePossibilities, int horizontalSafeMovePossibilities) {
        return new int[]{verticalMobility - horizontalMobility, 0};
    }

    @Override
    public boolean mobilityNeeded() {
        return true;
    }
}
