package src.board;

import src.card.Card;

import java.util.List;

public class Board {
    public static final int BASE_BOARD_SIZE = 12;
    private int nCards = BASE_BOARD_SIZE;
    private final List<Card> cards;

    public Board(List<Card> cards) {
        this.cards = cards;
    }

    public int getNCards() {
        return nCards;
    }

    public void display() {
        for (Card card: cards) {
            System.out.println(card.getColour());
            System.out.println(card.getFill());
            System.out.println(card.getNumber());
            System.out.println(card.getShape());
            System.out.println("--------------");
        }
    }
}
