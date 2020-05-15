package src.card.attributes;

import src.card.Card;

public enum Fill implements Attribute{
    OPEN,
    STRIPED,
    SOLID;

    @Override
    public boolean isAttributeSet(Card card1, Card card2, Card card3) {
        return allSame(card1, card2, card3) ||
               allDifferent(card1, card2, card3);
    }

    private boolean allSame(Card card1, Card card2, Card card3) {
        return card1.getFill() == card2.getFill() &&
               card1.getFill() == card3.getFill();
    }

    private boolean allDifferent(Card card1, Card card2, Card card3) {
        return card1.getFill() != card2.getFill() &&
               card1.getFill() != card3.getFill() &&
               card2.getFill() != card3.getFill();
    }
}
