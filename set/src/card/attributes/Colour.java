package src.card.attributes;

import src.proto.AllProtos;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dylanblecher
 * Colour attribute of a card
 * Proto-backed according to Google's Protobuf.
 */
public enum Colour {
    RED(AllProtos.CardColour.RED),
    GREEN(AllProtos.CardColour.GREEN),
    PURPLE(AllProtos.CardColour.PURPLE);

    public final AllProtos.CardColour proto;

    private static Map<AllProtos.CardColour, Colour> map = new HashMap<>();

    Colour(AllProtos.CardColour cardProto) {
        proto = cardProto;
    }

    static {
        for (Colour colour : Colour.values())
            map.put(colour.proto, colour);
    }

    public static Colour fromProto(AllProtos.CardColour colourProto) {
        return map.get(colourProto);
    }
}
