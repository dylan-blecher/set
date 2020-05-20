package src.action;

public class PlayerActionSelectSet extends PlayerAction {
    int[] cardPositions;

    public PlayerActionSelectSet(ActionType actionType, int playerID, int[] cardPositions) {
        super(actionType, playerID);
        this.cardPositions = cardPositions;
    }

    public int[] getCardPositions() {
        return cardPositions;
    }
}