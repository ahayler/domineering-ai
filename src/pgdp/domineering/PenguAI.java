package pgdp.domineering;

public class PenguAI extends AI {
    public static final boolean PARTICIPATING = false;
    public static final String NAME = null;

    @Override
    public synchronized Coordinate playMove(char[][] board, Player player, Mode mode) {
        return new Coordinate(0, 0);
    }
}