package src.action;

import src.proto.AllProtos;

public class PlayerAction extends Action {
    public PlayerAction(ActionType type, int playerID) {
        super(AllProtos.Action.newBuilder().setType(type.proto).setPlayerID(playerID).build());
    }

    public PlayerAction(AllProtos.Action action) {
        super(action);
    }

    public int getPlayerID() {
        return proto.getPlayerID();
    }
}