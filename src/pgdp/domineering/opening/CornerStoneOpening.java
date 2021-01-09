package pgdp.domineering.opening;

import pgdp.domineering.Coordinate;
import pgdp.domineering.Player;
import pgdp.domineering.tiles.Tile;
import pgdp.domineering.tiles.TileManager;

public class CornerStoneOpening extends Opening {
    /*
    Opening is really simple: It tries to occupy as many corner nodes as possible. For illustration see below

    Vertical (Numbering in playing order):
          0 1 2 3 4 5 6 7 8 9 a b c
         ┏━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┓
        0┃ ┃3┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃4┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        1┃ ┃3┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃4┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        2┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        3┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        4┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        5┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        6┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        7┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        8┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        9┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        a┃ ┃2┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃1┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        b┃ ┃2┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃1┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        c┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┗━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┛

         Horizontal (Numbering in playing order):

          0 1 2 3 4 5 6 7 8 9 a b c
         ┏━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┓
        0┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        1┃4┃4┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃2┃2┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        2┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        3┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        4┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        5┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        6┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        7┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        8┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        9┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        a┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        b┃3┃3┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃1┃1┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        c┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
         ┗━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┛
    */
    @Override
    public Coordinate findOpeningMove(Tile[][] tileBoard, Player player) {

        if (player == Player.V) {
            // check move number 1
            if (TileManager.movePossible(tileBoard, new Coordinate(11, 10), player) &&
                    TileManager.movePossible(tileBoard, new Coordinate(12, 10), player))
                return new Coordinate(11, 10);
                // check move number 2
            else if (TileManager.movePossible(tileBoard, new Coordinate(1, 10), player) &&
                    TileManager.movePossible(tileBoard, new Coordinate(0, 10), player))
                return new Coordinate(1, 10);
                // check move number 3
            else if (TileManager.movePossible(tileBoard, new Coordinate(1, 0), player) &&
                    TileManager.movePossible(tileBoard, new Coordinate(0, 0), player))
                return new Coordinate(1, 0);
                // check move number 4
            else if (TileManager.movePossible(tileBoard, new Coordinate(11, 0), player) &&
                    TileManager.movePossible(tileBoard, new Coordinate(12, 0), player))
                return new Coordinate(11, 0);
            else return null;
        } else {
            // check move number 1
            if (TileManager.movePossible(tileBoard, new Coordinate(10, 11), player) &&
                    TileManager.movePossible(tileBoard, new Coordinate(10, 12), player))
                return new Coordinate(10, 11);
                // check move number 2
            else if (TileManager.movePossible(tileBoard, new Coordinate(10, 1), player) &&
                    TileManager.movePossible(tileBoard, new Coordinate(10, 0), player))
                return new Coordinate(10, 1);
                // check move number 3
            else if (TileManager.movePossible(tileBoard, new Coordinate(0, 11), player) &&
                    TileManager.movePossible(tileBoard, new Coordinate(0, 12), player))
                return new Coordinate(0, 11);
                // check move number 4
            else if (TileManager.movePossible(tileBoard, new Coordinate(0, 1), player) &&
                    TileManager.movePossible(tileBoard, new Coordinate(0, 0), player))
                return new Coordinate(0, 1);
            else return null;
        }
    }
}
