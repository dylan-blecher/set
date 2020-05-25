package src.action;

import src.proto.ActionProtos;

import java.util.HashMap;
import java.util.Map;

// TODO: create end game option where it just prints out the current winner summary
public enum ActionType {
    SELECT_SET(ActionProtos.ActionType.SELECT_SET),
    REQUEST_SHOW_SET(ActionProtos.ActionType.REQUEST_SHOW_SET),
    LEAVE_GAME(ActionProtos.ActionType.LEAVE_GAME),
    REQUEST_DRAW_THREE(ActionProtos.ActionType.REQUEST_DRAW_THREE),
    SHOW_SET(ActionProtos.ActionType.SHOW_SET, ActionTypePriority.HIGH_PRIORITY),
    DRAW_THREE(ActionProtos.ActionType.DRAW_THREE, ActionTypePriority.HIGH_PRIORITY);

    private static Map<ActionProtos.ActionType, ActionType> map = new HashMap<>();

    public final ActionProtos.ActionType proto;

    private final ActionTypePriority priority;

    ActionType(ActionProtos.ActionType actionType) {
        this(actionType, ActionTypePriority.LOW_PRIORITY);
    }

    ActionType(ActionProtos.ActionType actionType, ActionTypePriority priority) {
        this.proto = actionType;
        this.priority = priority;
    }

    static {
        for (ActionType actionType : ActionType.values())
            map.put(actionType.proto, actionType);
    }

    public static ActionType valueOf(int moveType) {
        return map.get(ActionProtos.ActionType.forNumber(moveType));
    }

    public static ActionType fromProto(ActionProtos.ActionType actionType) {
        return map.get(actionType);
    }

    public ActionTypePriority getPriority() {
        return priority;
    }
}