package src.deck;

import src.card.Card;

import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards;
    private int size;

    public Deck(List<Card> cards) {
        this.cards = cards;
        this.size = cards.size();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    // remove and return given card
    public Card removeCard(Card card) {
        this.cards.remove(card);
        return card;
    }

    // remove card at head and return it
    public Card removeCard() {
        return removeCard(0);
    }

    // remove card at given index and return card removed
    public Card removeCard(int index) {
        Card removedCard = this.cards.remove(index);
        return removedCard;
    }

    public int getSize() {
        return size;
    }
}
