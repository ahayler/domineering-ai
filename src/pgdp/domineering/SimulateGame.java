package pgdp.domineering;

import pgdp.domineering.ai.HumanPlayerWithAITakeOver;
import pgdp.domineering.ai.MinMaxAI_V2;
import pgdp.domineering.ai.MinMaxAI_V1;
import pgdp.domineering.ai.MinMaxAI_V3;
import pgdp.domineering.evaluation_function.ExtendedRealAndSafeMovesEvaluationFunction;
import pgdp.domineering.evaluation_function.ReworkedEvaluationFunction;

public class SimulateGame {
    public static void main(String[] args) {
        testSimpleGameStructure();
    }

    public static void testSimpleGameStructure() {
        MinMaxAI_V3 horizontalAI =  new MinMaxAI_V3(4, new ReworkedEvaluationFunction(), true,
                false, 13, true, true);

        Game game = new Game(new MinMaxAI_V2(4, new ExtendedRealAndSafeMovesEvaluationFunction(), true,
                false, 13, true, false),
                new HumanPlayerWithAITakeOver(horizontalAI), Mode.EASY);
        game.runGame();
        System.out.println(game.getWinner());
    }
}
