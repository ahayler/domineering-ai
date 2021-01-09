package pgdp.domineering.bias_function;

import pgdp.domineering.Coordinate;
import pgdp.domineering.Player;
import pgdp.domineering.tiles.Tile;
import pgdp.domineering.tiles.TileManager;

public class BiasFunction_V0 extends BiasFunction {
    @Override
    public int[] evaluate(Tile[][] tileBoard, Coordinate move, Player player) {
        return new int[]{0, 0};

        /*TEST*/
        /*int biasFirst = 0;
        int biasSecond = 0;
        int indexBase;
        int baseFactor;
        if (player == Player.V) {
            indexBase = 0;
            baseFactor = 1;
        } else {
            indexBase = 1;
            baseFactor = -1;
        }*/


/*        // evaluate board before and after
        int[] results_before =
                TileManager.getRealSafeFreePossibilitiesVerticalAndHorizontal(tileBoard);
        int[] results_after =
                TileManager.getRealSafeFreePossibilitiesVerticalAndHorizontal(
                        TileManager.makeMoveAndCopyBoard(tileBoard, move, player));

        // if the Player doesn't create a safe move attach a penality
        if (results_before[indexBase + 2] <= results_after[indexBase + 2])
            biasSecond -= baseFactor * 50;*/

/*            // Root node bias
            if (TileManager.getAllPossibleMoves(tileBoard, player).length > 50) {
                Tile[][] updatedBoard = TileManager.makeMoveAndCopyBoard(tileBoard, move, player);
                biasSecond += evaluateRootNodes(updatedBoard);
            }*/
    }

    public static int evaluateRootNodes(Tile[][] tileBoard) {
        int count = 0;
        count += BiasAssistant.evaluateRootRow(tileBoard, 1);
        count += BiasAssistant.evaluateRootRow(tileBoard, 11);
        count += BiasAssistant.evaluateRootColumn(tileBoard, 1);
        count += BiasAssistant.evaluateRootColumn(tileBoard, 11);

        return count;
    }
}
