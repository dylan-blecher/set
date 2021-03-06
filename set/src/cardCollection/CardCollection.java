package src.cardCollection;

import src.card.Card;
import src.proto.AllProtos;

import java.util.*;

/**
 * @author dylanblecher
 * Abstract concept of a group of cards. This includes a Set, Board and Deck.
 *
 */
public abstract class CardCollection {
    /**
     * To maintain position in the board, the group of cards is implemented as an array of Cards such that
     * removing one card doesn't shuffle the others around like a list would. Shuffling would make it hard
     * to play the game.
     * In order to use an array like this, we must store the empty spots in the cards array in ascending order,
     * so that we can insert a card into the earliest empty spot efficiently (saving an O(n) search every time).
     */
    private final Card[] cards;
    private PriorityQueue<Integer> emptyIndices;

    /**
     * construct with a complete list
     */
    public CardCollection(Card[] cards) {
        this.cards = cards;
        initialiseEmptySlots();
    }

    /**
     * construct with a number of cards which will then be added using addCard()
     */
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

    /**
     * Add card to the first empty slot in the collection.
     * If there are no empty slots, nothing will happen.
     */
    public void addCard(Card card) {
        Integer earliestFreeIndex = emptyIndices.poll();
        if (validIndex(earliestFreeIndex))
            cards[earliestFreeIndex] = card;
    }

    /**
     *
     * @param index position to remove the card from
     * @return the card removed or null if the index is invalid or there is no card at the index
     */
    public Card removeCard(int index) {
        if (!validIndex(index) || cards[index] == null) return null;
        Card card = cards[index];
        emptySlot(index);
        return card;
    }

    private boolean validIndex(Integer num) {
        return num != null && num >= 0 && num < cards.length;
    }

    /**
     * empty a spot in the card collection and update our emptyIndices now that we have one more
     */
    private void emptySlot(int index) {
        cards[index] = null;
        emptyIndices.add(index);
    }

    /**
     * @return the number of cards in the card collection
     * (excluding the empty slots obviously)
     */
    public int nCards() {
        return cards.length - nEmptySpots();
    }

    /**
     * @return a copy of the cards for iterating through
     */
    public Card[] getCards() {
        return cards.clone();
    }

    /**
     * @return a protobuf of the cards within the card collection according to Google's protobuf
     */
    public List<AllProtos.Card> getCardsProto() {
        // ugly alternative in one line:
        // return Arrays.stream(cards).map(Card::getProto).collect(Collectors.toList());

        List<AllProtos.Card> cardsProto = new LinkedList<>();
        for (Card card: cards) {
            if (card == null) {
                // can't add null to a repeated proto item so just create an empty card (i.e. don't set colour, fill...)
                // By not setting these, the default value will be the first value in the enum: UNKNOWN_...
                cardsProto.add(buildEmptyCard());
            } else {
                cardsProto.add(card.proto);
            }
        }
        return cardsProto;
    }

    private AllProtos.Card buildEmptyCard() {
        return AllProtos.Card.newBuilder().build();
    }

    /**
     * @return a protobuf of the card collection according to Google's protobuf
     */
    public AllProtos.CardCollection getProto() {
        return AllProtos.CardCollection
                .newBuilder()
                .addAllCards(getCardsProto())
                .build();
    }

    public Card getCard(int position) {
        return cards[position];
    }

    /**
     * Shuffle cards randomly.
     * Used before game starts for randomising board setup.
     */
    public void shuffle() {
        List<Card> cardsList = Arrays.asList(cards);
        Collections.shuffle(cardsList);
        cardsList.toArray(cards);
    }

    public int nEmptySpots() {
        return emptyIndices.size();
    }
}