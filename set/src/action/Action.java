package src.action;

import src.proto.ActionProtos;

public class Action {
    public final ActionProtos.Action proto;

    public Action(ActionProtos.Action action) {
        proto = action;
    }

    public Action(ActionType type) {
        this(ActionProtos.Action.newBuilder().setType(type.proto).build());
    }

    public ActionType getType() { return ActionType.fromProto(proto.getType()); }
}


