package pgdp.domineering.ai;

import pgdp.domineering.Coordinate;
import pgdp.domineering.Mode;
import pgdp.domineering.Player;

public abstract class AI {

    public abstract Coordinate playMove(char[][] board, Player player, Mode mode);
}
