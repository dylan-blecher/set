package src.move.moves;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.move.MoveRunner;
import src.move.MoveType;

import static src.cardCollection.board.Dealer.addToBoardFromDeck;
import static src.cardCollection.set.Set.SET_SIZE;

public class RequestDrawThree extends RequestMove implements MoveRunner {
    public RequestDrawThree(int playerID) {
        super(playerID);
    }

//    TODO: would it be better to actually instantiate a dealer person and pass them around instaed of just calling their static functions?

    @Override
    public MoveType getMoveType() {
        return MoveType.REQUEST_DRAW_THREE;
    }

    @Override
    public void enactRequestedMove(Board board, Deck deck) {
        addToBoardFromDeck(SET_SIZE, board, deck);
    }
}
