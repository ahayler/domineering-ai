package pgdp.domineering.evaluation_function;

abstract public class EvaluationFunction {
    abstract public int[] evaluate(int verticalMobility, int horizontalMobility, int verticalRealMoves,
                                   int horizontalRealMoves, int verticalSafeMoves, int horizontalSafeMoves);
}
