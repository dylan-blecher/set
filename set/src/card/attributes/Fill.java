package src.card.attributes;

import src.proto.AllProtos;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dylanblecher
 * Fill attribute of a card
 * Proto-backed according to Google's Protobuf.
 */
public enum Fill {
    OPEN(AllProtos.CardFill.OPEN),
    STRIPED(AllProtos.CardFill.STRIPED),
    SOLID(AllProtos.CardFill.SOLID);

    public final AllProtos.CardFill proto;

    private static Map<AllProtos.CardFill, Fill> map = new HashMap<>();

    Fill(AllProtos.CardFill cardProto) {
        proto = cardProto;
    }

    static {
        for (Fill fill : Fill.values())
            map.put(fill.proto, fill);
    }

    public static Fill fromProto(AllProtos.CardFill fillProto) {
        return map.get(fillProto);
    }
}
