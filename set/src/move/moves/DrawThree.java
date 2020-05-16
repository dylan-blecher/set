package src.move.moves;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.move.Move;
import src.move.MoveType;

import static src.cardCollection.board.Dealer.addToBoardFromDeck;
import static src.cardCollection.set.Set.SET_SIZE;
// TODO: Move the 3 types of moves into a moves directory?
public class DrawThree implements Move {
    @Override
    public void validateMove(Board board, Deck deck) {
        if (deck.size() < 3) throw new UnsupportedOperationException("Not enough cards left to draw three.");
    }

    @Override
//    TODO: would it be better to actually instantiate a dealer person and pass them around instaed of just calling their static functions?
    public void enactMove(Board board, Deck deck) {
        addToBoardFromDeck(SET_SIZE, board, deck);
    }

    @Override
    public MoveType getMoveType() {
        return MoveType.DRAW_3;
    }
}
