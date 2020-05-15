package src.card.attributes;

import src.card.Card;

public enum Number implements Attribute {
    ONE,
    TWO,
    THREE;

    @Override
    public boolean isAttributeSet(Card card1, Card card2, Card card3) {
        return allSame(card1, card2, card3) ||
               allDifferent(card1, card2, card3);
    }

    private boolean allSame(Card card1, Card card2, Card card3) {
        return card1.getNumber() == card2.getNumber() &&
               card1.getNumber() == card3.getNumber();
    }

    private boolean allDifferent(Card card1, Card card2, Card card3) {
        return card1.getNumber() != card2.getNumber() &&
               card1.getNumber() != card3.getNumber() &&
               card2.getNumber() != card3.getNumber();
    }
}