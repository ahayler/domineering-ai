package pgdp.domineering;

import pgdp.domineering.ai.HumanPlayer;
import pgdp.domineering.ai.MinMaxAI;
import pgdp.domineering.ai.OldMinMaxAI;
import pgdp.domineering.evaluation_function.ExtendedRealAndSafeMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.RealAndSafeMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.RealMovesEvaluationFunction;

public class SimulateGame {
    public static void main(String[] args) {
        testSimpleGameStructure();
    }

    public static void testSimpleGameStructure() {
        Game game = new Game(new MinMaxAI(2, new ExtendedRealAndSafeMovesEvaluationFunction(), true,
                true, 10, true),
                new MinMaxAI(2, new ExtendedRealAndSafeMovesEvaluationFunction(), true,
                        true, 10, true), Mode.EASY);
        game.runGame();
        System.out.println(game.getWinner());
    }
}
