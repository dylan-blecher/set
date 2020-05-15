package src.cardCollection.board;

import src.card.Card;
import src.cardCollection.CardCollection;

import java.util.List;

public class Board extends CardCollection {
    public static final int BASE_BOARD_SIZE = 12;

    public Board(List<Card> cards) {
        super(cards);
        if (cards.size() != BASE_BOARD_SIZE)
            throw new UnsupportedOperationException("You are trying to create a board of incorrect size.");
    }

    public void display() {
        int cardNum = 0;
        for (Card card: getCards()) {
            System.out.printf("------%d------\n", cardNum);
            System.out.println(card.getColour());
            System.out.println(card.getFill());
            System.out.println(card.getNumber());
            System.out.println(card.getShape());
            System.out.println("--------------");
            cardNum++;
        }
    }
}