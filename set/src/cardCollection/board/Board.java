package src.cardCollection.board;

import src.card.Card;
import src.cardCollection.CardCollection;

public class Board extends CardCollection {
    public static final int BASE_BOARD_SIZE = 12;
    public static final int MAX_BOARD_SIZE = 21;

    public Board(Card[] cards) {
        super(cards);
        if (cards.length != BASE_BOARD_SIZE)
            throw new UnsupportedOperationException("You are trying to create a board of incorrect size.");
    }

    public Board(int nCards) {
        super(nCards);
        if (nCards != MAX_BOARD_SIZE)
            throw new UnsupportedOperationException("You are trying to create a board of incorrect size.");
    }

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