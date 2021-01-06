package pgdp.domineering.evaluation_function;

public class ReworkedEvaluationFunction extends EvaluationFunction {
    /*
    The main part is still Real + Safe Moves (difference between players); but the second order value is now
    Safe Move Possibilities - DeadMoves (difference between players)
    */
    @Override
    public int[] evaluate(int verticalMobility, int horizontalMobility, int verticalRealMoves, int horizontalRealMoves,
                          int verticalSafeMoves, int horizontalSafeMoves, int verticalSafeMovePossibilities,
                          int horizontalSafeMovePossibilities, int freeMovesVertical, int freeMovesHorizontal) {

        int verticalDeadMoves = verticalRealMoves - verticalSafeMoves
                - verticalSafeMovePossibilities - freeMovesVertical;
        int horizontalDeadMoves = horizontalRealMoves - horizontalSafeMoves
                - horizontalSafeMovePossibilities - freeMovesHorizontal;

        return new int[]{verticalRealMoves + verticalSafeMoves
                - horizontalRealMoves - horizontalSafeMoves,
                (verticalSafeMovePossibilities * 100 - verticalDeadMoves)
                        - (horizontalSafeMovePossibilities * 100 - horizontalDeadMoves)};
    }

    @Override
    public boolean mobilityNeeded() {
        return false;
    }
}
