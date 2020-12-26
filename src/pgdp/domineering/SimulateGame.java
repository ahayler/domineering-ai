package pgdp.domineering;

import pgdp.domineering.ai.MinMaxAI;
import pgdp.domineering.evaluation_function.ExtendedRealAndSafeMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.RealAndSafeMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.RealMovesEvaluationFunction;

public class SimulateGame {
    public static void main(String[] args) {
        testSimpleGameStructure();
    }

    public static void testSimpleGameStructure() {
        Game game = new Game(new MinMaxAI(3, new RealAndSafeMovesEvaluationFunction(), false,
                false, 10, false),
                new MinMaxAI(3, new RealMovesEvaluationFunction(), true,
                        false, 0, true), Mode.EASY);
        game.runGame();
        System.out.println(game.getWinner());
    }
}
