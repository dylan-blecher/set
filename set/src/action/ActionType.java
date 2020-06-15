package src.action;

import src.proto.AllProtos;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dylanblecher
 * Defining all types of Actions in the game.
 * Proto-backed according to Google's Protobuf.
 * Uses a map to convert from proto ActionTypes to native ActionTypes
 */
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

    ActionType(AllProtos.ActionType actionTypeProto, ActionTypePriority priority) {
        this.proto = actionTypeProto;
        this.priority = priority;
    }

    static {
        for (ActionType actionType : ActionType.values())
            map.put(actionType.proto, actionType);
    }

    public static ActionType valueOf(int moveType) {
        return map.get(AllProtos.ActionType.forNumber(moveType));
    }

    /**
     * Converts from a proto ActionType to a native ActionType
     */
    public static ActionType fromProto(AllProtos.ActionType actionType) {
        return map.get(actionType);
    }

    public ActionTypePriority getPriority() {
        return priority;
    }
}