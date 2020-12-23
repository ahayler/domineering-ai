package pgdp.domineering;

public class Test {
    public static void main(String[] args) {
        testSimpleGameStructure();
    }

    public static void testSimpleGameStructure() {
        Game game = new Game(new AdvancedCostFunctionAI(), new SimpleCostFunctionAI(), Mode.EASY);
        game.runGame();
        System.out.println(game.getWinner());
    }
}
