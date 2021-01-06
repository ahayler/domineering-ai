package pgdp.domineering.evaluation_function;

public class ExtendedRealAndSafeMovesEvaluationFunction extends EvaluationFunction {
    @Override
    public int[] evaluate(int verticalMobility, int horizontalMobility, int verticalRealMoves, int horizontalRealMoves,
                          int verticalSafeMoves, int horizontalSafeMoves, int verticalSafeMovePossibilities,
                          int horizontalSafeMovePossibilities, int freeMovesVertical, int freeMovesHorizontal) {
        return new int[]{verticalRealMoves + verticalSafeMoves - horizontalRealMoves - horizontalSafeMoves,
                verticalSafeMovePossibilities - horizontalSafeMovePossibilities};
    }

    @Override
    public boolean mobilityNeeded() {
        return false;
    }
}
