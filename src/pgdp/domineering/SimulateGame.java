package pgdp.domineering;

import pgdp.domineering.ai.MinMaxAI;
import pgdp.domineering.evaluation_function.RealAndSafeMovesEvaluationFunction;

public class SimulateGame {
    public static void main(String[] args) {
        testSimpleGameStructure();
    }

    public static void testSimpleGameStructure() {
        Game game = new Game(new MinMaxAI(2, new RealAndSafeMovesEvaluationFunction(), false),
                new MinMaxAI(2, new RealAndSafeMovesEvaluationFunction(), false), Mode.EASY);
        game.runGame();
        System.out.println(game.getWinner());
    }
}
