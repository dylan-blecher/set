package src.action;

import src.proto.ActionProtos;

import java.util.List;

public class PlayerActionSelectSet extends PlayerAction {
    public PlayerActionSelectSet(ActionType type, int playerID, List<Integer> cardPositions) {
        super(ActionProtos.Action.newBuilder().setType(type.proto).setPlayerID(playerID).addAllCardPositions(cardPositions).build());
    }

    public List<Integer> getCardPositions() {
        return proto.getCardPositionsList();
    }
}