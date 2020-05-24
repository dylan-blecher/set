package src.cardCollection.deck;

import org.junit.Test;

import static org.junit.Assert.*;
import static src.cardCollection.deck.DeckBuilder.buildDeck;

public class DeckBuilderTest {
    @Test
    public void testBuildDeck() {
        assertEquals(81, buildDeck().nCards());
    }
}