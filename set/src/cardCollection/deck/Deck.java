package src.cardCollection.deck;

import src.card.Card;
import src.cardCollection.CardCollection;

import java.util.Collections;
import java.util.List;

public class Deck extends CardCollection {
    public static final int INITIAL_DECK_SIZE = 81;
    public Deck(List<Card> cards) {
        super(cards);
        if (cards.size() != INITIAL_DECK_SIZE)
            throw new UnsupportedOperationException("You are trying to create a set of incorrect size.");
    }

    public void shuffle() {
        Collections.shuffle(getCards());
    }
}