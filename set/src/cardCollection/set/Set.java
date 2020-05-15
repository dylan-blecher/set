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

}