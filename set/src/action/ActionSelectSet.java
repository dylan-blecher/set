package src.action;

public class ActionSelectSet extends Action {
    int[] cardPositions;

    public ActionSelectSet(ActionType actionType, int playerID, int[] cardPositions) {
        super(actionType, playerID);
        this.cardPositions = cardPositions;
    }

    public int[] getCardPositions() {
        return cardPositions;
    }
}