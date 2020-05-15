package src.cardCollection.set;

import src.card.Card;
import src.cardCollection.CardCollection;

import java.util.List;

public class Set extends CardCollection {
    public static final int SET_SIZE = 3;

    public Set(List<Card> cards) {
        super(cards);
        if (cards.size() != SET_SIZE)
            throw new UnsupportedOperationException("You are trying to create a set of incorrect size.");
    }

    public void display() {
        for (Card card: getCards()) {
            System.out.println("---------------");
            System.out.println(card.getColour());
            System.out.println(card.getFill());
            System.out.println(card.getNumber());
            System.out.println(card.getShape());
            System.out.println("--------------");
        }
    }
}