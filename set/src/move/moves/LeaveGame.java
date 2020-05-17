package src.move.moves;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.move.Move;
import src.move.MoveRunner;
import src.move.MoveType;
import src.player.Players;

public class LeaveGame extends Move implements MoveRunner {
    public LeaveGame(int playerID) {
        super(playerID);
    }

    @Override
    public void enactMove(Board board, Deck deck, Players players) {
        int playerID = getPlayerID();
        String name = players.getActivePlayers().get(playerID).getName();
        players.dropPlayerFromGame(playerID);
        int nPlayers = players.getNActivePlayers();
        System.out.printf("%s (player %d) has left. %d player%s remain.\n", name, playerID + 1, nPlayers, nPlayers == 1 ? "" :"s");
    }

    @Override
    public MoveType getMoveType() {
        return MoveType.LEAVE_GAME;
    }
}
