package src.action;

import src.proto.ActionProtos;

public class PlayerAction extends Action {
    public PlayerAction(ActionType type, int playerID) {
        this(ActionProtos.Action.newBuilder().setType(type.proto).setPlayerID(playerID).build());
    }

    public PlayerAction(ActionProtos.Action action) {
        super(action);
    }

    public int getPlayerID() {
        return proto.getPlayerID();
    }
}