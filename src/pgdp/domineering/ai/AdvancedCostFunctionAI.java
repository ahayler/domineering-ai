package pgdp.domineering.ai;

import pgdp.domineering.Coordinate;
import pgdp.domineering.Game;
import pgdp.domineering.Mode;
import pgdp.domineering.Player;
import pgdp.domineering.ai.AI;

public class AdvancedCostFunctionAI extends AI {
    @Override
    public Coordinate playMove(char[][] board, Player player, Mode mode) {
        return getMove(board, player);
    }

    public static Coordinate getMove(char[][] board, Player player) {
        // first get all possible moves
        Coordinate [] movesArray = Game.getAllPossibleMoves(board, player);

        // now get the min and best move
        int max0 = -2000;
        int max1 = -2000;
        Coordinate bestMove = null;

        for(int i = 0; i < movesArray.length; i++) {
            int[] orderingValueArray = getOrderingValueArray(board, movesArray[i], player);
            if (orderingValueArray[0] > max0 || (orderingValueArray[0] == max0 && orderingValueArray[1] > max1)) {
                max0 = orderingValueArray[0];
                max1 = orderingValueArray[1];
                bestMove = movesArray[i];
            }
        }
        return bestMove;
    }

    private static int[] getOrderingValueArray(char[][] board, Coordinate move, Player player) {
        // get board after move
        char[][] oldBoard = board;
        board = Game.makeMoveAndCopyBoard(board, move, player);
        Player opponent;
        if (player == Player.V) opponent = Player.H; else opponent = Player.V;

        // If the opponent has no turns left: we win
        if ( Game.getMobility(board, opponent) == 0)
            return new int[]{1000, 0};

        // If the the opponent has at least one turn and we have none: we lose
        if (Game.getMobility(board, player) == 0)
            return new int[]{-1000, 0};

        // If our safe turns >= opponents real turns: we win
        int playerSafeMoves = Game.getSafeMoves(board, player);
        int opponentRealMoves = Game.getRealMoves(board, opponent);

        if(playerSafeMoves >= opponentRealMoves) {
            return new int[]{1000, 0};
        }

        // If our real turns <= opponents safe turns and our real turns + 1 <= opponents real turns: we lose
        int playerRealMoves = Game.getRealMoves(board, player);
        int opponentSafeMoves = Game.getSafeMoves(board, opponent);

        if(playerRealMoves <= opponentSafeMoves && playerRealMoves <= opponentRealMoves + 1)
            return new int[]{-1000, 0};

        // if none of the special cases occur return the normal ordering value
        return new int[]{playerRealMoves - opponentRealMoves, playerSafeMoves - opponentSafeMoves};
        /*return playerRealMoves - opponentRealMoves;*/
    }
}
