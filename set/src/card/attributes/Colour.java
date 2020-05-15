package src.card.attributes;

import src.card.Card;

public enum Colour implements Attribute {
    RED,
    GREEN,
    PURPLE;

    @Override
    public boolean isAttributeSet(Card card1, Card card2, Card card3) {
        return allSame(card1, card2, card3) ||
               allDifferent(card1, card2, card3);
    }

    private boolean allSame(Card card1, Card card2, Card card3) {
        return card1.getColour() == card2.getColour() &&
               card1.getColour() == card3.getColour();
    }

    private boolean allDifferent(Card card1, Card card2, Card card3) {
        return card1.getColour() != card2.getColour() &&
               card1.getColour() != card3.getColour() &&
               card2.getColour() != card3.getColour();
    }
}
