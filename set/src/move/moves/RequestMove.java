package src.move.moves;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.move.Move;
import src.move.MoveRunner;
import src.move.MoveType;
import src.player.Player;
import src.player.Players;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static src.player.PlayerInteractor.getMoveRequestResponse;

public abstract class RequestMove extends Move implements MoveRunner {
    private List<Integer> IDsOfPlayersInAgreement;
    private boolean rejected = false;

    public RequestMove(int playerID) {
        super(playerID);
        IDsOfPlayersInAgreement = new LinkedList<>();
        IDsOfPlayersInAgreement.add(playerID);
    }

    @Override
    public void enactMove(Board board, Deck deck, Players players) {
        Player actingPlayer = players.getPlayer(getPlayerID());
        // ask all other players if they agree to your requested move. All players must be in agreement for move to happen.
        for (Player activePlayer: players.getActivePlayers().values()) {
            if (activePlayer == actingPlayer) continue;
            RespondToRequest response = getMoveRequestResponse(activePlayer, this);
            response.enactMove(board, deck, players);
        }
    }

    public void addAgreement(int ID) {
        IDsOfPlayersInAgreement.add(ID);
    }

    public void rejectMove() {
        rejected = true;
    }

    public boolean allInAgreement(Set<Integer> activePlayerIDs) {
        for (int activePlayerID: activePlayerIDs)
            if (!IDsOfPlayersInAgreement.contains(activePlayerID)) return false;
        return true;
    }

    public abstract MoveType getMoveType();
    public abstract void enactRequestedMove(Board board, Deck deck);
}
