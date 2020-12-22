package pgdp.domineering;

public class RandomAI extends AI {
    @Override
    public synchronized Coordinate playMove(char[][] board, Player player, Mode mode) {
        return getMove(board, player);
    }

    public static Coordinate getMove(char[][] board, Player player) {
        // make random moves
        while (true) {
            Coordinate move = new Coordinate(MiniJava.randomInt(0, board.length),
                    MiniJava.randomInt(0, board[0].length));
            if (Game.movePossible(board, move, player))
                return move;
        }
    }
}
