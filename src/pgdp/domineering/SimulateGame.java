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
        Game game = new Game(new MinMaxAI(2, new RealAndSafeMovesEvaluationFunction(), false,
                false, 10, false),
                new MinMaxAI(2, new RealMovesEvaluationFunction(), false,
                        false, 0, false), Mode.EASY);
        game.runGame();
        System.out.println(game.getWinner());
    }
}
