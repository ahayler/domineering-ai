package pgdp.domineering;

public class PenguAI extends AI {
    public static final boolean PARTICIPATING = false;

    @Override
    public synchronized Coordinate playMove(char[][] board, Player player, Mode mode) {
        return SimpleCostFunctionAI.getMove(board, player);
    }
}