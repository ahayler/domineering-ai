package pgdp.domineering;

import pgdp.domineering.ai.MinMaxAI_V2;
import pgdp.domineering.ai.MinMaxAI_V1;
import pgdp.domineering.evaluation_function.ExtendedRealAndSafeMovesEvaluationFunction;

public class SimulateGame {
    public static void main(String[] args) {
        testSimpleGameStructure();
    }

    public static void testSimpleGameStructure() {
        Game game = new Game(new MinMaxAI_V2(4, new ExtendedRealAndSafeMovesEvaluationFunction(), true,
                false, 13, true, false),
                new MinMaxAI_V2(2, new ExtendedRealAndSafeMovesEvaluationFunction(), true,
                        false, 13, true, false), Mode.EASY);
        game.runGame();
        System.out.println(game.getWinner());
    }
}
