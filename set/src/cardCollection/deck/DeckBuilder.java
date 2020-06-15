package src.cardCollection.deck;

import src.card.Card;
import src.card.attributes.Colour;
import src.card.attributes.Fill;
import src.card.attributes.Number;
import src.card.attributes.Shape;

import static src.cardCollection.deck.Deck.INITIAL_DECK_SIZE;

/**
 * @author dylanblecher
 * Constructs deck.
 */
public class DeckBuilder {
    // class should not be instantiatable, hence private
    // creates compilation error
    private DeckBuilder() throws UnsupportedOperationException {
        // creates runtime error if reflection is used to bypass private
        throw new UnsupportedOperationException("Kindly stop using reflection to get around this being private xo");
    }

    public static Deck buildDeck() {
        Deck deck = new Deck(INITIAL_DECK_SIZE);

        for (Colour colour: Colour.values())
            for (Shape shape: Shape.values())
                for (Fill fill: Fill.values())
                    for (Number number: Number.values())
                        deck.addCard(new Card(colour, shape, fill, number));

        return deck;
    }
}