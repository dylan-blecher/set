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
        var boardCards = new LinkedList<Card>();

        for (int count = 0; count < BASE_BOARD_SIZE; count++)
            boardCards.add(deck.removeCard());

        return new Board(boardCards);
    }

    // Moves the first card from the deck to the end of the board list
    public static void addToBoardFromDeck(int numToAdd, Board board, Deck deck) {
        for (int count = 0; count < numToAdd; count++)
            board.addCard(deck.removeCard(0));
    }
}
