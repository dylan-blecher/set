package src.cardCollection;

import src.card.Card;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public abstract class CardCollection {
    private final Card[] cards;
    // stores the empty spots in the cards array in ascending order,
    // so that we can insert a card into the earliest empty spot.
//    TODO: check this is ascending, not descending!
    private PriorityQueue<Integer> emptyIndices;

    // construct with a complete list
    public CardCollection(Card[] cards) {
        this.cards = cards;
        initialiseEmptySlots();
    }

    // construct with a number of cards which will then be added using addCard()
    public CardCollection(int nCards) {
        this.cards = new Card[nCards];
        this.emptyIndices = new PriorityQueue<>();
        initialiseEmptySlots();
    }

    private void initialiseEmptySlots() {
        int freeIndex = 0;
        for (Card card: cards)
            if (card == null)
                emptyIndices.add(freeIndex++);
    }

    // add card to the first empty slot in the collection
    // if there are no empty slots, nothing will happen.
    public void addCard(Card card) {
        Integer earliestFreeIndex = emptyIndices.poll();
        if (validIndex(earliestFreeIndex))
            cards[earliestFreeIndex] = card;
    }

    private boolean validIndex(Integer num) {
        return num != null && num >= 0 && num < cards.length;
    }

    // remove card at given index and return card removed
    // returns null if the index is invalid or there is no card at the index
    public Card removeCard(int index) {
        if (!validIndex(index) || cards[index] == null) return null;
        Card card = cards[index];
        emptySlot(index);
        return card;
    }

    // empty a spot in the collection and update our emptyIndices now that we have one more
    private void emptySlot(int index) {
        cards[index] = null;
        emptyIndices.add(index);
    }

    // remove and return given card
    // returns null if card is not in the collection
//    TODO: not sure i ever use this...
    public Card removeCard(Card card) {
        for (int index = 0; index < cards.length; index++)
            if (cards[index] == card)
                return removeCard(index);
        return null;
    }

    public int size() {
        return cards.length;
    }

    public Card[] getCards() {
        return cards.clone();
    }

    public Card getCard(int position) {
        return cards[position];
    }

    public void shuffle() {
        List<Card> cardsList = Arrays.asList(cards);
        Collections.shuffle(cardsList);
        cardsList.toArray(cards);
    }

    public int nEmptySpots() {
        return emptyIndices.size();
    }
}