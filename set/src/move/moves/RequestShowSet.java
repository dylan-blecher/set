package src.move.moves;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.cardCollection.set.Set;
import src.move.MoveRunner;
import src.move.MoveType;

import static src.game.Referee.getSet;

public class RequestShowSet extends RequestMove implements MoveRunner {
    public RequestShowSet(int playerID) {
        super(playerID);
    }

    @Override
    public MoveType getMoveType() {
        return MoveType.REQUEST_SHOW_SET;
    }

    @Override
    public void enactRequestedMove(Board board, Deck deck) {
        Set set = getSet(board);
        if (set == null)
            System.out.println("There are no sets on the board.");
        else
            set.display();
    }
}
