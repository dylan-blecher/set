package src.cardCollection.board;

import src.card.Card;
import src.cardCollection.CardCollection;

/**
 * @author dylanblecher
 * The communal board where players look for sets.
 */
public class Board extends CardCollection {
    public static final int BASE_BOARD_SIZE = 12;
    // you are guaranteed to have a set on the board with 20 cards, according to Wikipedia
    // https://en.wikipedia.org/wiki/Set_(card_game)#Basic_combinatorics_of_Set
    public static final int MAX_BOARD_SIZE = 20;

    public Board(int nCards) {
        super(nCards);
        if (nCards != MAX_BOARD_SIZE)
            throw new UnsupportedOperationException("You are trying to create a board of incorrect size.");
    }

    /**
     * Used to display the board for a terminal game.
     * Not used in the online version.
     */
    public void display() {
        int cardNum = 0;
        for (Card card: getCards()) {
            if (cardNum % 3 == 0) System.out.println();

            System.out.printf("Card %d: ", cardNum);
            if (card == null) {
                System.out.print("null     ");
            } else {
                System.out.print(card.getColour() + " " + card.getFill() + " " + card.getNumber() + " " + card.getShape() + "    ");
            }

            cardNum++;
        }

        System.out.println("\n\n");
    }
}