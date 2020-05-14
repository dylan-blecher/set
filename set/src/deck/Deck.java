package src.deck;

import src.card.Card;
import java.util.List;

public class Deck {
    private final List<Card> cards;
    private int size;

    public Deck(List<Card> cards) {
        this.cards = cards;
        this.size = cards.size();
    }

    public int getSize() {
        return size;
    }
}
