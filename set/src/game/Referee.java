package src.game;

import src.card.Card;
import src.card.attributes.Colour;
import src.card.attributes.Fill;
import src.card.attributes.Number;
import src.card.attributes.Shape;
import src.cardCollection.board.Board;
import src.cardCollection.set.Set;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static src.cardCollection.set.Set.SET_SIZE;

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
        //       there's got to be a smart way by considering which 3 cards are new... DOn't need to compare them with all surely?
        for (Card card1: board.getCards()) {
            for (Card card2 : board.getCards()) {
                // don't compare a card with itself (i.e. skip if card has matching ID)
                if (card1 == card2) continue;
                for (Card card3 : board.getCards()) {
                    if (card3 == card2 || card3 == card1) continue;

                    List<Card> potentialSet = new LinkedList<>();
                    potentialSet.add(card1);
                    potentialSet.add(card2);
                    potentialSet.add(card3);

                    if (isSet(potentialSet)) return new Set(potentialSet);
                }
            }
        }

        return null;
    }

    // To check if 3 cards make a Set, use a mathematical set to find out how many of each unique versions of
    // each attribute is present. i.e. create a dictionary which maps each enum (e.g. Colour) to a number.
    // If the number is 1 or 3, it's a set. Otherwise, it's not.
    public static boolean isSet(List<Card> potentialSet) {
        if (potentialSet.size() != SET_SIZE) return false;
        return colourSet(potentialSet) &&
               fillSet(potentialSet)   &&
               numberSet(potentialSet) &&
               shapeSet(potentialSet);
    }

//    TODO: is there a way to simplify the below 4 functions into 1 function with a function pointer as arg?
    private static boolean colourSet(List<Card> potentialSet) {
        HashSet<Colour> enum_set = new HashSet<>();

        for (Card card: potentialSet)
            enum_set.add(card.getColour());

        return enum_set.size() == 1 || enum_set.size() == 3;
    }

    private static boolean fillSet(List<Card> potentialSet) {
        HashSet<Fill> enum_set = new HashSet<>();

        for (Card card: potentialSet)
            enum_set.add(card.getFill());

        return enum_set.size() == 1 || enum_set.size() == 3;
    }

    private static boolean numberSet(List<Card> potentialSet) {
        HashSet<Number> enum_set = new HashSet<>();

        for (Card card: potentialSet)
            enum_set.add(card.getNumber());

        return enum_set.size() == 1 || enum_set.size() == 3;
    }

    private static boolean shapeSet(List<Card> potentialSet) {
        HashSet<Shape> enum_set = new HashSet<>();

        for (Card card: potentialSet)
            enum_set.add(card.getShape());

        return enum_set.size() == 1 || enum_set.size() == 3;
    }
}