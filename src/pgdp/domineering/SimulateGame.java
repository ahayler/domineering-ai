package pgdp.domineering;

import pgdp.domineering.ai.MinMaxAI;
import pgdp.domineering.evaluation_function.RealAndSafeMovesEvaluationFunction;

public class SimulateGame {
    public static void main(String[] args) {
        testSimpleGameStructure();
    }

    public static void testSimpleGameStructure() {
        Game game = new Game(new MinMaxAI(3, new RealAndSafeMovesEvaluationFunction(), true,
                true, 10, true),
                new MinMaxAI(3, new RealAndSafeMovesEvaluationFunction(), true,
                        false, 0, true), Mode.EASY);
        game.runGame();
        System.out.println(game.getWinner());
    }
}
