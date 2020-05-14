package src.deck;

import java.util.LinkedList;
import src.card.Card;
import src.card.Attributes.Colour;
import src.card.Attributes.Shape;
import src.card.Attributes.Fill;
import src.card.Attributes.Number;

public class DeckBuilder {
    // class should not be instantiatable, hence private
    // creates compilation error
    private DeckBuilder() throws UnsupportedOperationException {
        // creates runtime error if reflection is used to bypass private
        throw new UnsupportedOperationException("Kindly stop using reflection to get around this being a private class xo");
    }

    public static Deck buildDeck() {
        var cards = new LinkedList<Card>();

        for (Colour colour: Colour.values())
            for (Shape shape: Shape.values())
                for (Fill fill: Fill.values())
                    for (Number number: Number.values())
                        cards.add(new Card(colour, shape, fill, number));

        return new Deck(cards);
    }
}