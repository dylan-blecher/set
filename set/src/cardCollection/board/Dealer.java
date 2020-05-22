package src.cardCollection.board;

import src.card.Card;
import src.cardCollection.deck.Deck;

import static src.cardCollection.board.Board.BASE_BOARD_SIZE;
import static src.cardCollection.board.Board.MAX_BOARD_SIZE;

public class Dealer {
    // class should not be instantiatable, hence private
    // creates compilation error
    private Dealer() throws UnsupportedOperationException {
        // creates runtime error if reflection is used to bypass private
        throw new UnsupportedOperationException("Kindly stop using reflection to get around this being private xo");
    }

    public static Board setupBoard(Deck deck) {
        deck.shuffle();
        Board board = new Board(MAX_BOARD_SIZE);
        addToBoardFromDeck(BASE_BOARD_SIZE, board, deck);
        return board;
    }

    // Moves the first card from the deck to the end of the board list
    public static void addToBoardFromDeck(int numToAdd, Board board, Deck deck) {
        for (int count = 0; count < numToAdd; count++) {
            Card card = deck.removeCard();
            board.addCard(card);
        }
    }
}
