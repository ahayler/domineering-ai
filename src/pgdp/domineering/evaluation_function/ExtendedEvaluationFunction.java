package pgdp.domineering.evaluation_function;

abstract public class ExtendedEvaluationFunction {

    public abstract int[] evaluate(int verticalMobility, int horizontalMobility, int verticalRealMoves,
                                   int horizontalRealMoves, int verticalSafeMoves, int horizontalSafeMoves,
                                   int verticalSafeMovePossibilities, int horizontalSafeMovePossibilities,
                                   int freeMovesVertical, int freeMovesHorizontal,
                                   int verticalVulnerableTiles, int horizontalVulnerableTiles);

}
