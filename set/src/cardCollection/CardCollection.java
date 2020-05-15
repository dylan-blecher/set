package src.cardCollection;

import src.card.Card;

import java.util.List;

public abstract class CardCollection {
    private List<Card> cards;

    public CardCollection(List<Card> cards) {
        this.cards = cards;
    }

    // add card to the tail of the cards list
    public void addCard(Card card) {
        cards.add(card);
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
        return this.cards.remove(index);
    }

    public int size() {
        return cards.size();
    }

    public List<Card> getCards() {
        return cards;
    }
}
