package pgdp.domineering.tests;

import org.junit.Test;
import pgdp.domineering.BoardConverter;
import pgdp.domineering.Coordinate;
import pgdp.domineering.Player;
import pgdp.domineering.ai.MinMaxAI;
import pgdp.domineering.evaluation_function.RealMovesEvaluationFunction;

public class TestMinMaxAI {
    /*
    RMEV = RealMoveEvaluationFunction;
    D1, D2, ... = Depth 1, Depth 2, ...
    V/H = vertical/horizontal Move played
    */
    @Test
    public void testBadMove_RMEV_D1_H() {
    /*
      0 1 2 3 4 5 6 7 8 9 a b c
     ┏━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┓
    0┃ ┃V┃ ┃ ┃V┃ ┃ ┃
     ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
    1┃ ┃V┃H┃H┃V┃V┃ ┃
     ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
    2┃ ┃V┃ ┃V┃ ┃V┃ ┃
     ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
    3┃ ┃V┃ ┃V┃ ┃V┃ ┃
     ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
    4┃H┃H┃ ┃ ┃ ┃V┃ ┃
     ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
    5┃H┃H┃H┃H┃H┃H┃ ┃
     ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
    6┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃
     ┗━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┛
    EVEEVEE;EVHHVVE;EVEVEVE;EVEVEVE;HHEEEVE;HHHHHHE;EEEEEEE;
    1 (2)
      0 1 2 3 4 5 6 7 8 9 a b c
     ┏━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┓
    0┃ ┃V┃ ┃ ┃V┃ ┃ ┃
     ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
    1┃ ┃V┃H┃H┃V┃V┃ ┃
     ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
    2┃ ┃V┃ ┃V┃ ┃V┃ ┃
     ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
    3┃ ┃V┃ ┃V┃ ┃V┃ ┃
     ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
    4┃H┃H┃ ┃ ┃ ┃V┃ ┃
     ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
    5┃H┃H┃H┃H┃H┃H┃ ┃
     ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
    6┃H┃H┃ ┃ ┃ ┃ ┃ ┃
     ┗━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┛
    EVEEVEE;EVHHVVE;EVEVEVE;EVEVEVE;HHEEEVE;HHHHHHE;HHEEEEE;
    2 (3)

        Clearly the turn is shit, lets find out why it was played.
    */

        char[][] board = BoardConverter.stringToBoard("EVEEVEE;EVHHVVE;EVEVEVE;EVEVEVE;HHEEEVE;HHHHHHE;EEEEEEE;");

        MinMaxAI minMaxAI = new MinMaxAI(1, new RealMovesEvaluationFunction());
        Coordinate move = minMaxAI.playMove(board, Player.H, null);
    }
}
