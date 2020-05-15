package src.card.attributes;

import src.card.Card;

public interface Attribute {
    boolean isAttributeSet(Card card1, Card card2, Card card3);
}