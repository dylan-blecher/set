package src.card;

import src.card.attributes.Colour;
import src.card.attributes.Shape;
import src.card.attributes.Fill;
import src.card.attributes.Number;

public class Card {
    private static final String[] attributes = {"COLOUR", "FILL", "NUMBER", "SHAPE"};

    private final int cardID;
    private final Colour colour;
    private final Shape shape;
    private final Fill fill;
    private final Number number;

    public Card(int cardID, Colour colour, Shape shape, Fill fill, Number number) {
        // TODO: add error checking for input?
        this.cardID = cardID;
        this.colour = colour;
        this.shape = shape;
        this.fill = fill;
        this.number = number;
    }

    public int getCardID() {
        return cardID;
    }

    public Colour getColour() {
        return colour;
    }
    public Shape getShape() {
        return shape;
    }
    public Fill getFill() {
        return fill;
    }
    public Number getNumber() {
        return number;
    }

    public static String[] getAttributes() {
        return attributes;
    }
}