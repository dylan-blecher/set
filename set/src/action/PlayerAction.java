package src.action;

import src.proto.AllProtos;

/**
 * @author dylanblecher
 * The type of action that can be made by a player
 * (as opposed to SHOW_SET and DRAW_3 which can only be requested through REQUEST_SHOW_SET for example.)
 * PlayerActions include all other actions.
 * Proto-backed according to Google's Protobuf
 */
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