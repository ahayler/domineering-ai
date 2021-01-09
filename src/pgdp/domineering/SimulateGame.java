package pgdp.domineering;

import pgdp.domineering.ai.*;
import pgdp.domineering.bias_function.BiasFunction_V0;
import pgdp.domineering.evaluation_function.ExtendedRealAndSafeMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.ReworkedEvaluationFunction;

public class SimulateGame {
    public static void main(String[] args) {
        testSimpleGameStructure();
    }

    public static void testSimpleGameStructure() {
        MinMaxAI_V4 horizontalAI =  new MinMaxAI_V4(5, new ReworkedEvaluationFunction(), true,
                false, 13, true, true, new BiasFunction_V0());

        Game game = new Game(new HumanPlayerWithAITakeOver(horizontalAI), new MinMaxAI_V3(4, new ReworkedEvaluationFunction(), true,
                false, 13, true, false),
                Mode.EASY);
        game.runGame();
        System.out.println(game.getWinner());
    }
}
