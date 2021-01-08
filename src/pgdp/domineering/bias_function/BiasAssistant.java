package pgdp.domineering.bias_function;

import pgdp.domineering.Coordinate;
import pgdp.domineering.Tuple;
import pgdp.domineering.tiles.Tile;

public class BiasAssistant {
    public static int evaluateRootColumn(Tile[][] tileBoard, int i) {
        int eval = 0;

        for(int j = 0; j < tileBoard[0].length - 1; j++) {
            eval += evaluateCombo(tileBoard, i, j, i, j + 1);
        }

        return eval;
    }

    public static int evaluateRootRow(Tile[][] tileBoard, int j) {
        int eval = 0;

        for(int i = 0; i < tileBoard.length - 1; i++) {
            eval += evaluateCombo(tileBoard, i, j, i + 1, j);
        }

        return eval;
    }

    public static int evaluateCombo(Tile[][] tileBoard, int x0, int y0, int x1, int y1) {
        if(tileBoard[x0][y0].getTileChar() == 'E' && tileBoard[x1][y1].getTileChar() == 'E')
            return 0;
        else if(tileBoard[x0][y0].getTileChar() == 'V'  && tileBoard[x1][y1].getTileChar() == 'E'
                || tileBoard[x0][y0].getTileChar() == 'E' &&  tileBoard[x1][y1].getTileChar() == 'V'
                || tileBoard[x0][y0].getTileChar() == 'V'  && tileBoard[x1][y1].getTileChar() == 'V')
            return 1;
        else if (tileBoard[x0][y0].getTileChar() == 'H' && tileBoard[x1][y1].getTileChar() == 'E' ||
                tileBoard[x0][y0].getTileChar() == 'E' && tileBoard[x1][y1].getTileChar() == 'H' ||
                tileBoard[x0][y0].getTileChar() == 'H' && tileBoard[x1][y1].getTileChar() == 'H')
            return -1;
        else return 0;
    }
}
