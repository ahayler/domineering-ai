package pgdp.domineering;

import pgdp.domineering.ai.AdvancedCostFunctionAI;
import pgdp.domineering.ai.MinMaxAI;
import pgdp.domineering.ai.PenguAI;
import pgdp.domineering.ai.SimpleCostFunctionAI;
import pgdp.domineering.evaluation_function.RealAndSafeMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.RealMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.SimpleMobilityEvaluationFunction;

public class SimulateGame {
    public static void main(String[] args) {
        testSimpleGameStructure();
    }

    public static void testSimpleGameStructure() {
        Game game = new Game(new MinMaxAI(1, new RealAndSafeMovesEvaluationFunction()),
                new MinMaxAI(3, new RealAndSafeMovesEvaluationFunction()), Mode.EASY);
        game.runGame();
        System.out.println(game.getWinner());
    }
}
