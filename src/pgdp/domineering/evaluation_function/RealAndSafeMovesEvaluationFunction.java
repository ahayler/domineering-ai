package pgdp.domineering.evaluation_function;

public class RealAndSafeMovesEvaluationFunction extends EvaluationFunction {
    // WARNING: Pretty bad evaluation function

    @Override
    public int[] evaluate(int verticalMobility, int horizontalMobility, int verticalRealMoves, int horizontalRealMoves,
                          int verticalSafeMoves, int horizontalSafeMoves,
                          int verticalSafeMovePossibilities, int horizontalSafeMovePossibilities,
                          int freeMovesVertical, int freeMovesHorizontal) {
        return new int[]{verticalRealMoves + verticalSafeMoves - horizontalRealMoves - horizontalSafeMoves,
                verticalMobility - horizontalMobility};
    }

    @Override
    public boolean mobilityNeeded() {
        return true;
    }
}
