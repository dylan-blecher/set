package src.card.attributes;

import src.card.Card;

public enum Shape implements Attribute {
    DIAMOND,
    SQUIGGLE,
    OVAL;

    @Override
    public boolean isAttributeSet(Card card1, Card card2, Card card3) {
        return allSame(card1, card2, card3) ||
               allDifferent(card1, card2, card3);
    }

    private boolean allSame(Card card1, Card card2, Card card3) {
        return card1.getShape() == card2.getShape() &&
               card1.getShape() == card3.getShape();
    }

    private boolean allDifferent(Card card1, Card card2, Card card3) {
        return card1.getShape() != card2.getShape() &&
               card1.getShape() != card3.getShape() &&
               card2.getShape() != card3.getShape();
    }
}
