package src.cardCollection.deck;

import src.card.Card;
import src.cardCollection.CardCollection;

public class Deck extends CardCollection {
    public static final int INITIAL_DECK_SIZE = 81;
    private int nonNullIndex = 0;
    public Deck(Card[] cards) {
        super(cards);
        if (cards.length != INITIAL_DECK_SIZE)
            throw new UnsupportedOperationException("You are trying to create a deck of incorrect size.");
    }

    public Deck(int nCards) {
        super(nCards);
        if (nCards != INITIAL_DECK_SIZE)
            throw new UnsupportedOperationException("You are trying to create a deck of incorrect size.");
    }

    // remove card from next non-null slot
    public Card removeCard() {
        return removeCard(nonNullIndex++);
    }
}