package pgdp.domineering;

import pgdp.domineering.ai.MinMaxAI_V2;
import pgdp.domineering.ai.MinMaxAI_V1;
import pgdp.domineering.evaluation_function.ExtendedRealAndSafeMovesEvaluationFunction;

public class SimulateGame {
    public static void main(String[] args) {
        testSimpleGameStructure();
    }

    public static void testSimpleGameStructure() {
        Game game = new Game(new MinMaxAI_V2(3, new ExtendedRealAndSafeMovesEvaluationFunction(), true,
                true, 13, true),
                new MinMaxAI_V2(3, new ExtendedRealAndSafeMovesEvaluationFunction(), true,
                        true, 13, true), Mode.EASY);
        game.runGame();
        System.out.println(game.getWinner());
    }
}
