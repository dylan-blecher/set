package src.move.moves;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.move.Move;
import src.move.MoveRunner;
import src.move.MoveType;
import src.player.Players;

public class RespondToRequest extends Move implements MoveRunner {
    MoveType response;
    RequestMove requestMove;

    public RespondToRequest(int playerID, MoveType response, RequestMove requestMove) {
        super(playerID);
        this.response = response;
        this.requestMove = requestMove;
    }

    @Override
    public void enactMove(Board board, Deck deck, Players players) {
        // TODO: validate input is one of the 2 responses below
        switch (response) {
            case GRANT_REQUEST:
                requestMove.addAgreement(getPlayerID());
            case REFUSE_REQUEST:
                requestMove.rejectMove();
        }

        if (requestMove.allInAgreement(players.getActivePlayers().keySet())) {
            requestMove.enactRequestedMove(board, deck);
        }
    }

    @Override
    public MoveType getMoveType() {
        return null;
    }
}
