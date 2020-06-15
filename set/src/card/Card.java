package src.card;

import src.card.attributes.Colour;
import src.card.attributes.Fill;
import src.card.attributes.Number;
import src.card.attributes.Shape;
import src.proto.AllProtos;

/**
 * @author dylanblecher
 * Card in the game, which has a Colour, Fill, Number and Shape
 * Proto-backed according to Google's Protobuf.
 */
public class Card {
    public final AllProtos.Card proto;

    public Card(AllProtos.Card cardProto) {
        proto = cardProto;
    }

    public Card(Colour colour, Shape shape, Fill fill, Number number) {
        this(AllProtos.Card
                .newBuilder()
                .setColour(colour.proto)
                .setShape(shape.proto)
                .setFill(fill.proto)
                .setNumber(number.proto)
                .build()
        );
    }


    public Colour getColour() {
        return Colour.fromProto(proto.getColour());
    }
    public Shape getShape() {
        return Shape.fromProto(proto.getShape());
    }
    public Fill getFill() {
        return Fill.fromProto(proto.getFill());
    }
    public Number getNumber() {
        return Number.fromProto(proto.getNumber());
    }
}