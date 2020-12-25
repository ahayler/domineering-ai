package pgdp.domineering.tests;

import org.junit.Test;
import pgdp.domineering.BoardConverter;

import java.util.Arrays;

public class TestBoardConverter {
    @Test
    public void testBoardToString() {
        char[][] board = new char[][]{
                new char[] {'E', 'V', 'V'},
                new char[] {'E', 'H', 'E'},
                new char[] {'E', 'H', 'E'}};

        assert "EEE;VHH;VEE;".equals(BoardConverter.boardToString(board));
    }

    @Test
    public void testStringToBoard() {
        char[][] realBoard = new char[][]{
                new char[] {'E', 'V', 'V'},
                new char[] {'E', 'H', 'E'},
                new char[] {'E', 'H', 'E'}};

    char[][] recreatedBoard = BoardConverter.stringToBoard("EEE;VHH;VEE;");
    assert Arrays.deepEquals(realBoard, recreatedBoard);
    }
}
