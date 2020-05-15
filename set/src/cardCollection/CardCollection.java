package src.cardCollection;

import src.card.Card;

import java.util.List;

public abstract class CardCollection {
    private List<Card> cards;
    private int size;

    public CardCollection(List<Card> cards) {
        this.cards = cards;
        this.size = cards.size();
    }

    // remove card at head and return it
    public Card removeCard() {
        return removeCard(0);
    }

    // remove and return given card
    public Card removeCard(Card card) {
        this.cards.remove(card);
        return card;
    }

    // remove card at given index and return card removed
    public Card removeCard(int index) {
        Card removedCard = this.cards.remove(index);
        return removedCard;
    }

    public int size() {
        return size;
    }

    public List<Card> getCards() {
        return cards;
    }
}
