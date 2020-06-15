package src.card.attributes;

import src.proto.AllProtos;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dylanblecher
 * Number attribute of a card
 * Proto-backed according to Google's Protobuf.
 */
public enum Number {
    ONE(AllProtos.CardNumber.ONE),
    TWO(AllProtos.CardNumber.TWO),
    THREE(AllProtos.CardNumber.THREE);

    public final AllProtos.CardNumber proto;

    private static Map<AllProtos.CardNumber, Number> map = new HashMap<>();

    Number(AllProtos.CardNumber cardProto) {
        proto = cardProto;
    }

    static {
        for (Number number : Number.values())
            map.put(number.proto, number);
    }

    public static Number fromProto(AllProtos.CardNumber numberProto) {
        return map.get(numberProto);
    }
}