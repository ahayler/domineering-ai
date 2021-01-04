package pgdp.domineering;

import pgdp.domineering.ai.MinMaxAI_V2;
import pgdp.domineering.ai.MinMaxAI_V1;
import pgdp.domineering.evaluation_function.ExtendedRealAndSafeMovesEvaluationFunction;

public class SimulateGame {
    public static void main(String[] args) {
        testSimpleGameStructure();
    }

    public static void testSimpleGameStructure() {
        Game game = new Game(new MinMaxAI_V1(3, new ExtendedRealAndSafeMovesEvaluationFunction(), true,
                false, 13, true),
                new MinMaxAI_V2(5, new ExtendedRealAndSafeMovesEvaluationFunction(), true,
                        false, 13, true, true), Mode.EASY);
        game.runGame();
        System.out.println(game.getWinner());
    }
}
