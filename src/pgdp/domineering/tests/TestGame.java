package pgdp.domineering.tests;

import org.junit.Test;
import pgdp.domineering.BoardConverter;
import pgdp.domineering.Game;
import pgdp.domineering.Player;

public class TestGame {

    @Test
    public void testGetMobilityV() {
            /*
          0 1 2 3 4 5 6 7 8 9 a b c
         ┏━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┓
        0┃ ┃V┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        1┃ ┃V┃H┃H┃H┃H┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        2┃ ┃ ┃ ┃ ┃V┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        3┃H┃H┃H┃H┃V┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        4┃ ┃V┃ ┃V┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        5┃ ┃V┃ ┃V┃ ┃ ┃
         ┗━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┛
    EVEEEE;EVHHHH;EEEEVE;HHHHVE;EVEVEE;EVEVEE;
     */
        char[][] board = BoardConverter.stringToBoard("EVEEEE;EVHHHH;EEEEVE;HHHHVE;EVEVEE;EVEVEE;");

        assert Game.getMobility(board, Player.V) == 8;
    }

    @Test
    public void testGetMobilityH() {
        /*
         0 1 2 3 4 5 6 7 8 9 a b c
         ┏━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┓
        0┃ ┃V┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        1┃ ┃V┃H┃H┃H┃H┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        2┃ ┃ ┃ ┃ ┃V┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        3┃H┃H┃ ┃ ┃V┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        4┃ ┃V┃ ┃V┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        5┃ ┃V┃ ┃V┃ ┃ ┃
         ┗━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┛
    EVEEEE;EVHHHH;EEEEVE;HHEEVE;EVEVEE;EVEVEE;
         */
        char[][] board = BoardConverter.stringToBoard("EVEEEE;EVHHHH;EEEEVE;HHEEVE;EVEVEE;EVEVEE;");

        assert Game.getMobility(board, Player.H) == 9;
    }

    @Test
    public void testGetRealMovesV () {
        /*
          0 1 2 3 4 5 6 7 8 9 a b c
         ┏━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┓
        0┃V┃V┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        1┃V┃V┃H┃H┃H┃H┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        2┃ ┃ ┃ ┃ ┃ ┃H┃H┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        3┃H┃H┃ ┃V┃ ┃V┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        4┃ ┃V┃ ┃V┃ ┃V┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        5┃ ┃V┃H┃H┃ ┃V┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        6┃H┃H┃ ┃ ┃ ┃V┃ ┃
         ┗━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┛
     VVEEEEE;VVHHHHE;EEEEEHH;HHEVEVE;EVEVEVE;EVHHEVE;HHEEEVE;
     */

        char[][] board = BoardConverter.stringToBoard("VVEEEEE;VVHHHHE;EEEEEHH;HHEVEVE;EVEVEVE;EVHHEVE;HHEEEVE;");

        assert Game.getRealMoves(board, Player.V) == 7;

    }

    @Test
    public void testRealMovesH() {
        /*
         0 1 2 3 4 5 6 7 8 9 a b c
         ┏━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┓
        0┃ ┃V┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        1┃ ┃V┃H┃H┃H┃H┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        2┃ ┃ ┃ ┃ ┃ ┃H┃H┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        3┃H┃H┃ ┃V┃ ┃V┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        4┃ ┃V┃ ┃V┃ ┃V┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        5┃ ┃V┃H┃H┃ ┃V┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        6┃ ┃ ┃ ┃ ┃ ┃V┃ ┃
         ┗━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┛
    EVEEEEE;EVHHHHE;EEEEEHH;HHEVEVE;EVEVEVE;EVHHEVE;EEEEEVE;
        */

        char[][] board = BoardConverter.stringToBoard("EVEEEEE;EVHHHHE;EEEEEHH;HHEVEVE;EVEVEVE;EVHHEVE;EEEEEVE;");

        assert Game.getRealMoves(board, Player.H) == 6;
    }

    @Test
    public void testGetSafeMovesV () {
        /*
          0 1 2 3 4 5 6 7 8 9 a b c
         ┏━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┓
        0┃V┃V┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        1┃V┃V┃H┃H┃H┃H┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        2┃H┃H┃V┃H┃H┃H┃H┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        3┃H┃H┃V┃V┃V┃V┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        4┃V┃V┃ ┃V┃V┃V┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        5┃V┃V┃H┃H┃ ┃V┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        6┃H┃H┃ ┃ ┃ ┃V┃ ┃
         ┗━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┛
    VVEEEEE;VVHHHHE;HHVHHHH;HHVVVVE;VVEVVVE;VVHHEVE;HHEEEVE;
         */

        char[][] board = BoardConverter.stringToBoard("VVEEEEE;VVHHHHE;HHVHHHH;HHVVVVE;VVEVVVE;VVHHEVE;HHEEEVE;");

        assert Game.getSafeMoves(board, Player.V) == 2;
    }

    @Test
    public void testGetSafeMovesH () {
    /*
          0 1 2 3 4 5 6 7 8 9 a b c
         ┏━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┳━┓
        0┃V┃V┃ ┃ ┃ ┃ ┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        1┃V┃V┃H┃H┃H┃H┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        2┃ ┃ ┃ ┃ ┃ ┃H┃H┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        3┃H┃H┃ ┃V┃ ┃V┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        4┃V┃V┃ ┃V┃ ┃V┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        5┃V┃V┃H┃H┃ ┃V┃ ┃
         ┣━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━╋━┫
        6┃H┃H┃ ┃ ┃ ┃V┃ ┃
         ┗━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┻━┛
    VVEEEEE;VVHHHHE;EEEEEHH;HHEVEVE;VVEVEVE;VVHHEVE;HHEEEVE;
    */

        char[][] board = BoardConverter.stringToBoard("VVEEEEE;VVHHHHE;EEEEEHH;HHEVEVE;VVEVEVE;VVHHEVE;HHEEEVE;");

        assert Game.getSafeMoves(board, Player.H) == 4;
    }

    public static void testGetAllPossibleMoves () {
        /*
        If a move would be invalid the game would error so we only check if the number of moves is correct.
        The samples are from the mobility tests.
        */
        char[][] boardV = BoardConverter.stringToBoard("EVEEEE;EVHHHH;EEEEVE;HHHHVE;EVEVEE;EVEVEE;");
        char[][] boardH = BoardConverter.stringToBoard("EVEEEE;EVHHHH;EEEEVE;HHEEVE;EVEVEE;EVEVEE;");

        assert Game.getAllPossibleMoves(boardV, Player.V).length == 8;
        assert Game.getAllPossibleMoves(boardH, Player.H).length == 9;
    }
}
