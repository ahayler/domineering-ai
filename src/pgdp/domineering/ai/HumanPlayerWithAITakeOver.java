package pgdp.domineering.ai;

import pgdp.domineering.*;

public class HumanPlayerWithAITakeOver extends AI {
    private boolean tookOver = false;
    AI takeOverAI;

    public HumanPlayerWithAITakeOver(AI takeOverAI) {
        this.takeOverAI = takeOverAI;
    }


    @Override
    public Coordinate playMove(char[][] board, Player player, Mode mode) {
        if(!tookOver) {
            int answer = MiniJava.readInt("If you want the AI to take over enter 0: ");
            if(answer == 0) tookOver = true;
        }
        if(!tookOver) {
            int x = MiniJava.readInt("x-Koordinate: ");
            int y = MiniJava.readInt("y-Koordinate: ");
            return new Coordinate(x, y);
        } else return takeOverAI.playMove(board, player, mode);

    }
}
