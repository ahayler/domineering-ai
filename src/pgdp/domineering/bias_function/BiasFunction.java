package pgdp.domineering.bias_function;

import pgdp.domineering.Coordinate;
import pgdp.domineering.Player;
import pgdp.domineering.tiles.Tile;

public abstract class BiasFunction {
    abstract public int[] evaluate(Tile[][] tileBoard, Coordinate move, Player player);
}
