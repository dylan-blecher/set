package src.card;

import src.card.Attributes.Colour;
import src.card.Attributes.Shape;
import src.card.Attributes.Fill;
import src.card.Attributes.Number;

public class Card {
    private final Colour colour;
    private final Shape shape;
    private final Fill fill;
    private final Number number;

    public Card(Colour colour, Shape shape, Fill fill, Number number) {
        // add error checking for input?
        this.colour = colour;
        this.shape = shape;
        this.fill = fill;
        this.number = number;
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
}
