package src.move.moves;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.move.Move;
import src.move.PlayerMove;
import src.move.MoveType;
import src.player.Player;

import static src.cardCollection.board.Dealer.addToBoardFromDeck;
import static src.cardCollection.set.Set.SET_SIZE;

public class DrawThree extends PlayerMove implements Move {
    public DrawThree(Player player) {
        super(player);
    }

    @Override
    public void validateMove(Board board, Deck deck) {
        if (deck.size() < SET_SIZE) throw new UnsupportedOperationException("Not enough cards left to draw SET_SIZE.");
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
