package src.cardCollection.board;

import src.card.Card;
import src.cardCollection.deck.Deck;

import java.util.LinkedList;

import static src.cardCollection.board.Board.BASE_BOARD_SIZE;

public class Dealer {
    // class should not be instantiatable, hence private
    // creates compilation error
    private Dealer() throws UnsupportedOperationException {
        // creates runtime error if reflection is used to bypass private
        throw new UnsupportedOperationException("Kindly stop using reflection to get around this being private xo");
    }

    public static Board setupBoard(Deck deck) {
        deck.shuffle();
        var cards = new LinkedList<Card>();

        for (int count = 0; count < BASE_BOARD_SIZE; count++)
            cards.add(deck.removeCard());

        return new Board(cards);
    }
}
