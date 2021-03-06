package pgdp.domineering.evaluation_function;

abstract public class EvaluationFunction {

    public abstract int[] evaluate(int verticalMobility, int horizontalMobility, int verticalRealMoves,
                                   int horizontalRealMoves, int verticalSafeMoves, int horizontalSafeMoves,
                                   int verticalSafeMovePossibilities, int horizontalSafeMovePossibilities,
                                   int freeMovesVertical, int freeMovesHorizontal);

    public abstract boolean mobilityNeeded();
}
