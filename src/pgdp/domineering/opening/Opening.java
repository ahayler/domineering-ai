package pgdp.domineering.opening;

import pgdp.domineering.Coordinate;
import pgdp.domineering.Player;
import pgdp.domineering.tiles.Tile;

public abstract class Opening {
    abstract public Coordinate findOpeningMove(Tile[][] tileBoard, Player player);
}
