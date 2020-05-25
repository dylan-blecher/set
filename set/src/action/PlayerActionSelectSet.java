package src.action;

public class PlayerActionSelectSet extends PlayerAction {
    int[] cardPositions;

    public PlayerActionSelectSet(ActionType type, int playerID, int[] cardPositions) {
        super(type, playerID);
        this.cardPositions = cardPositions;
    }

    public int[] getCardPositions() {
        return cardPositions;
    }
}