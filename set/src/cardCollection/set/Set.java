package src.cardCollection.set;

import src.card.Card;
import src.card.attributes.Colour;
import src.card.attributes.Fill;
import src.card.attributes.Number;
import src.card.attributes.Shape;
import src.cardCollection.CardCollection;

import java.util.HashSet;

public class Set extends CardCollection {
    public static final int SET_SIZE = 3;

    public Set(Card[] cards) {
        super(cards);
        if (!isSet(cards)) {
            throw new UnsupportedOperationException("Invalid set.");
        }
    }

    // To check if 3 cards make a Set, use a mathematical set to find out how many of each unique versions of
    // each attribute is present. i.e. create a dictionary which maps each enum (e.g. Colour) to a number.
    // If the number is 1 or 3, it's a set. Otherwise, it's not.
    public static boolean isSet(Card[] potentialSet) {
        if (potentialSet.length != SET_SIZE) return false;

        for (Card card: potentialSet)
            if (card == null) return false;

        return colourSet(potentialSet) &&
               fillSet(potentialSet)   &&
               numberSet(potentialSet) &&
               shapeSet(potentialSet);
    }

    //    TODO: is there a way to simplify the below 4 functions into 1 function with a function pointer as arg?
    private static boolean colourSet(Card[] potentialSet) {
        HashSet<Colour> enum_set = new HashSet<>();

        for (Card card: potentialSet)
            enum_set.add(card.getColour());

        return enum_set.size() == 1 || enum_set.size() == 3;
    }

    private static boolean fillSet(Card[] potentialSet) {
        HashSet<Fill> enum_set = new HashSet<>();

        for (Card card: potentialSet)
            enum_set.add(card.getFill());

        return enum_set.size() == 1 || enum_set.size() == 3;
    }

    private static boolean numberSet(Card[] potentialSet) {
        HashSet<Number> enum_set = new HashSet<>();

        for (Card card: potentialSet)
            enum_set.add(card.getNumber());

        return enum_set.size() == 1 || enum_set.size() == 3;
    }

    private static boolean shapeSet(Card[] potentialSet) {
        HashSet<Shape> enum_set = new HashSet<>();

        for (Card card: potentialSet)
            enum_set.add(card.getShape());

        return enum_set.size() == 1 || enum_set.size() == 3;
    }

    public void display() {
        for (Card card: getCards()) {
            System.out.println("---------------");
            System.out.println(card.getColour());
            System.out.println(card.getFill());
            System.out.println(card.getNumber());
            System.out.println(card.getShape());
            System.out.println("--------------");
        }
    }
}