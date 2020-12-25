package pgdp.domineering.ai;

import pgdp.domineering.Coordinate;
import pgdp.domineering.MiniJava;
import pgdp.domineering.Mode;
import pgdp.domineering.Player;
import pgdp.domineering.ai.AI;

public class HumanPlayer extends AI {
    @Override
    public Coordinate playMove(char[][] board, Player player, Mode mode) {
        int x = MiniJava.readInt("x-Koordinate: ");
        int y = MiniJava.readInt("y-Koordinate: ");
        return new Coordinate(x, y);
    }
}
