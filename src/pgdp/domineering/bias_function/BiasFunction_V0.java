package pgdp.domineering.bias_function;

import pgdp.domineering.Coordinate;
import pgdp.domineering.Player;
import pgdp.domineering.tiles.Tile;
import pgdp.domineering.tiles.TileManager;

public class BiasFunction_V0 extends BiasFunction {
    @Override
    public int[] evaluate(Tile[][] tileBoard, Coordinate move, Player player) {
        int biasFirst = 0;
        int biasSecond = 0;

        // first build an Anti-Safe Move Bias (MaxPlayer get's Minus, MinPlayer get's Plus)
        int[] evalBoardBefore = TileManager.getRealSafeFreePossibilitiesVerticalAndHorizontal(tileBoard);
        if(player == Player.V) {
            if(evalBoardBefore[0] != evalBoardBefore[2] && TileManager.isSafeMove(tileBoard, move, player))
                biasFirst--;
        } else {
            if(evalBoardBefore[1] != evalBoardBefore[3] && TileManager.isSafeMove(tileBoard, move, player))
                biasFirst++;
        }
        return new int[]{biasFirst, biasSecond};
    }
}
