package pgdp.domineering.evaluation_function;

public class ExtendedEvaluationFunctionV0 extends ExtendedEvaluationFunction {
    @Override
    public int[] evaluate(int verticalMobility, int horizontalMobility, int verticalRealMoves, int horizontalRealMoves,
                          int verticalSafeMoves, int horizontalSafeMoves, int verticalSafeMovePossibilities,
                          int horizontalSafeMovePossibilities, int freeMovesVertical, int freeMovesHorizontal,
                          int verticalVulnerableTiles, int horizontalVulnerableTiles) {

        int verticalDeadMoves = verticalRealMoves - verticalSafeMoves
                - verticalSafeMovePossibilities - freeMovesVertical;
        int horizontalDeadMoves = horizontalRealMoves - horizontalSafeMoves
                - horizontalSafeMovePossibilities - freeMovesHorizontal;

        return new int[]{verticalRealMoves + verticalSafeMoves
                - horizontalRealMoves - horizontalSafeMoves,
                (verticalSafeMovePossibilities * 100 - verticalVulnerableTiles - verticalDeadMoves * 10)
                        - (horizontalSafeMovePossibilities * 100 - horizontalVulnerableTiles - horizontalDeadMoves * 10)};
    }
}
