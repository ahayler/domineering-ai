package pgdp.domineering;

public class PenguAI extends AI {
    public static final boolean PARTICIPATING = false;

    @Override
    public synchronized Coordinate playMove(char[][] board, Player player, Mode mode) {
        if (mode == Mode.HARD)
            return MinMaxAI.getMove(board, player, 3);

        return MinMaxAI.getMove(board, player, 1);

        /*return AdvancedCostFunctionAI.getMove(board, player);*/

        /*return SimpleCostFunctionAI.getMove(board, player);*/
    }
}