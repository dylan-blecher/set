package src.action;

import java.util.HashMap;
import java.util.Map;

// TODO: create end game option where it just prints out the current winner summary
public enum ActionType {
    SELECT_SET(0),
    REQUEST_SHOW_SET(1),
    LEAVE_GAME(2),
    REQUEST_DRAW_THREE(3),
    SHOW_SET(4, ActionTypePriority.HIGH_PRIORITY),
    DRAW_THREE(5, ActionTypePriority.HIGH_PRIORITY);

    private static Map<Integer, ActionType> map = new HashMap<>();

    private final int value;

    private final ActionTypePriority priority;

    ActionType(int value) {
        this(value, ActionTypePriority.LOW_PRIORITY);
    }

    ActionType(int value, ActionTypePriority priority) {
        this.value = value;
        this.priority = priority;
    }

    static {
        for (ActionType actionType : ActionType.values())
            map.put(actionType.getValue(), actionType);
    }

    public static ActionType valueOf(int moveType) {
        return map.get(moveType);
    }

    public int getValue() {
        return value;
    }

    public ActionTypePriority getPriority() {
        return priority;
    }
}