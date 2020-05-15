package src.game;

import src.card.Card;
import src.card.attributes.Colour;
import src.card.attributes.Fill;
import src.card.attributes.Shape;
import src.cardCollection.board.Board;
import src.cardCollection.set.Set;

import java.util.LinkedList;
import java.util.List;

// TODO: This is a bit gross because Set is a thing in Java.util so I'm overriding :/

public class Referee {
    // class should not be instantiatable
    private Referee() throws UnsupportedOperationException {
        // creates runtime error if reflection is used to bypass private
        throw new UnsupportedOperationException("Kindly stop using reflection to get around this being private xo");
    }

    // TODO: not sure if this belongs in the referee.. will be reused by move...
    public static boolean setExists(Board board) {
        return getSet(board) != null;
    }

    // TODO: not sure if this belongs in the referee.. will be reused by AI...
    public static Set getSet(Board board) {
        // TODO: brute force, optimise later! Might choose one set over another based on how many more it leaves on the board... or that's up to AI
        for (Card card1: board.getCards()) {
            for (Card card2: board.getCards()) {
                for (Card card3: board.getCards()) {
                    if (isSet(card1, card2, card3)) {
                        List<Card> potentialSet = new LinkedList<Card>();
                        potentialSet.add(card1);
                        potentialSet.add(card2);
                        potentialSet.add(card3);
                        return new Set(potentialSet);
                    }
                }
            }
        }
        return null;
    }

    private static boolean isSet(Card card1, Card card2, Card card3) {
        return Colour.isAttributeSet(card1, card2, card3) &&
               Fill.isAttributeSet(card1, card2, card3) &&
               Number.isAttributeSet(card1, card2, card3) &&
               Shape.isAttributeSet(card1, card2, card3);
    }
}