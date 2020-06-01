package src.card.attributes;

import src.proto.AllProtos;

import java.util.HashMap;
import java.util.Map;

public enum Shape {
    DIAMOND(AllProtos.CardShape.DIAMOND),
    SQUIGGLE(AllProtos.CardShape.SQUIGGLE),
    OVAL(AllProtos.CardShape.OVAL);

    public final AllProtos.CardShape proto;

    private static Map<AllProtos.CardShape, Shape> map = new HashMap<>();

    Shape(AllProtos.CardShape cardShape) {
        proto = cardShape;
    }

    static {
        for (Shape shape : Shape.values())
            map.put(shape.proto, shape);
    }

    public static Shape fromProto(AllProtos.CardShape shapeProto) {
        return map.get(shapeProto);
    }
}
