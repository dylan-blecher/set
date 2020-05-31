package src.action;

import src.proto.AllProtos;

public class Action {
    public final AllProtos.Action proto;

    public Action(ActionType type) {
        this(AllProtos.Action
                .newBuilder()
                .setType(type.proto)
                .build());
    }

    public Action(AllProtos.Action action) {
        proto = action;
    }

    public ActionType getType() { return ActionType.fromProto(proto.getType()); }
}


