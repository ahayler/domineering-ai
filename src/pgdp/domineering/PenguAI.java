package pgdp.domineering;

public class PenguAI extends AI {
    public static final boolean PARTICIPATING = false;

    @Override
    public synchronized Coordinate playMove(char[][] board, Player player, Mode mode) {
        return playRandomMove(board, player);
    }

    private Coordinate playRandomMove(char[][] board, Player player) {
        // make random moves
        while (true) {
            Coordinate move = new Coordinate(MiniJava.randomInt(0, board.length),
                    MiniJava.randomInt(0, board[0].length));
            if (Game.movePossible(board, move, player))
                return move;
        }
    }
}