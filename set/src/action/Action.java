package src.action;

import src.proto.AllProtos;

/**
 * @author dylanblecher
 * An Action is a move in the game.
 * Instantiations of this parent class are non-player actions: SHOW_SET & DRAW_3
 */
public class Action {
    // Action is proto-backed using Google's Protobuf.
    // This means that all interaction with the class is reading from/writing to a proto.
    public final AllProtos.Action proto;

    public Action(ActionType type) {
        this(AllProtos.Action
                .newBuilder()
                .setType(type.proto)
                .build());
    }

    public Action(AllProtos.Action actionProto) {
        proto = actionProto;
    }

    public ActionType getType() { return ActionType.fromProto(proto.getType()); }
}


