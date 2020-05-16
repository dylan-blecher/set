package src.move;

import java.util.HashMap;
import java.util.Map;

// TODO: create end game option where it just prints out the current winner summary
public enum MoveType {
    DRAW_3(3),
    SELECT_SET(0),
    SHOW_SET(1),
    END_GAME(2);

    private static Map<Integer, MoveType> map = new HashMap<>();

    private int value;

    MoveType(int value) {
        this.value = value;
    }

    static {
        for (MoveType moveType : MoveType.values()) {
            map.put(moveType.getValue(), moveType);
        }
    }

    public int getValue() {
        return value;
    }

    public static MoveType valueOf(int moveType) {
        return map.get(moveType);
    }
}