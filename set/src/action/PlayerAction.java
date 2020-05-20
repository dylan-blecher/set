package src.action;

public class PlayerAction extends Action {
    private final int playerID;

    public PlayerAction(ActionType actionType, int playerID) {
        super(actionType);
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }
}
