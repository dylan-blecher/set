package src.action;

import src.proto.AllProtos;

import java.util.List;

/**
 * @author dylanblecher
 * Proto-backed according to Google's Protobuf.
 */
public class PlayerActionSelectSet extends PlayerAction {
    public PlayerActionSelectSet(ActionType type, int playerID, List<Integer> cardPositions) {
        super(AllProtos.Action.newBuilder().setType(type.proto).setPlayerID(playerID).addAllCardPositions(cardPositions).build());
    }

    public PlayerActionSelectSet(AllProtos.Action action) {
        super(action);
    }

    public List<Integer> getCardPositions() {
        return proto.getCardPositionsList();
    }
}