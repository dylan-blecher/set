package src.deck;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static src.deck.DeckBuilder.buildDeck;

class DeckBuilderTest {
    @Test
    public void testBuildDeck() {
        assertEquals(81, buildDeck().getSize());
    }
}