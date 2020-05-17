package src.move;

import java.util.HashMap;
import java.util.Map;

// TODO: create end game option where it just prints out the current winner summary
public enum MoveType {
    SELECT_SET(0),
    REQUEST_SHOW_SET(1),
    LEAVE_GAME(2),
    REQUEST_DRAW_THREE(3),
    GRANT_REQUEST(4),
    REFUSE_REQUEST(5);

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