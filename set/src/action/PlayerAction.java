package src.action;

public class PlayerAction extends Action {
    private final int playerID;

    public PlayerAction(ActionType type, int playerID) {
        super(type);
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }
}
