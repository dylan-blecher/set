package src.action;

import src.proto.AllProtos;

import java.util.HashMap;
import java.util.Map;

// TODO: create end game option where it just prints out the current winner summary
public enum ActionType {
    SELECT_SET(AllProtos.ActionType.SELECT_SET),
    REQUEST_SHOW_SET(AllProtos.ActionType.REQUEST_SHOW_SET),
    LEAVE_GAME(AllProtos.ActionType.LEAVE_GAME),
    REQUEST_DRAW_THREE(AllProtos.ActionType.REQUEST_DRAW_THREE),
    SHOW_SET(AllProtos.ActionType.SHOW_SET, ActionTypePriority.HIGH_PRIORITY),
    DRAW_THREE(AllProtos.ActionType.DRAW_THREE, ActionTypePriority.HIGH_PRIORITY);

    private static Map<AllProtos.ActionType, ActionType> map = new HashMap<>();

    public final AllProtos.ActionType proto;

    private final ActionTypePriority priority;

    ActionType(AllProtos.ActionType actionType) {
        this(actionType, ActionTypePriority.LOW_PRIORITY);
    }

    ActionType(AllProtos.ActionType actionType, ActionTypePriority priority) {
        this.proto = actionType;
        this.priority = priority;
    }

    static {
        for (ActionType actionType : ActionType.values())
            map.put(actionType.proto, actionType);
    }

    public static ActionType valueOf(int moveType) {
        return map.get(AllProtos.ActionType.forNumber(moveType));
    }

    public static ActionType fromProto(AllProtos.ActionType actionType) {
        return map.get(actionType);
    }

    public ActionTypePriority getPriority() {
        return priority;
    }
}